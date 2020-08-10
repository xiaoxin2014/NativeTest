package com.example.nativetest.net.token;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.example.nativetest.BuildConfig;
import com.example.nativetest.common.NetConstant;
import com.example.nativetest.net.EncodeUtils;
import com.example.nativetest.net.LiveDataCallAdapterFactory;
import com.example.nativetest.net.ScInterceptor;

import org.json.JSONObject;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import androidx.annotation.NonNull;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class TokenRetrofitClient {
    private Context mContext;
    private Retrofit mRetrofit;

    public TokenRetrofitClient(Context context, String baseUrl) {
        mContext = context;

//        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
//                .addInterceptor(new AddHeaderInterceptor(mContext))
//                .addInterceptor(new ReceivedCookiesInterceptor(mContext))
//                .connectTimeout(NetConstant.API_CONNECT_TIME_OUT, TimeUnit.SECONDS)
//                .readTimeout(NetConstant.API_READ_TIME_OUT, TimeUnit.SECONDS)
//                .writeTimeout(NetConstant.API_WRITE_TIME_OUT, TimeUnit.SECONDS);

        /*
         * 当 baseUrl 没有以 "/" 结尾时加入 "/"
         * 防止当 baseUrl 为非纯域名的，如：域名+ path 时，如果不以 "/" 结尾，Retrofit 会抛出异常
         */
        if (!TextUtils.isEmpty(baseUrl)
                && baseUrl.lastIndexOf("/") != baseUrl.length() - 1) {
            baseUrl = baseUrl + "/";
        }
        mRetrofit = new Retrofit.Builder()
                .client(getUnsafeOkHttpClient())
                .baseUrl(baseUrl) //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .addCallAdapterFactory(new LiveDataCallAdapterFactory()) //设置请求响应适配 LiveData
                .build();
    }

    private OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                    .addInterceptor(new AddHeaderInterceptor(mContext))
                    .addInterceptor(new ReceivedCookiesInterceptor(mContext))
                    .addInterceptor(new MyInterceptor())
                    .addInterceptor(new ScInterceptor())
                    .connectTimeout(NetConstant.API_CONNECT_TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(NetConstant.API_READ_TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(NetConstant.API_WRITE_TIME_OUT, TimeUnit.SECONDS);
            okHttpBuilder.sslSocketFactory(sslSocketFactory);
            okHttpBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return okHttpBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 接受cookie拦截器
     */
    public class ReceivedCookiesInterceptor implements Interceptor {
        private Context mContext;

        public ReceivedCookiesInterceptor(Context context) {
            mContext = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());

            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                HashSet<String> cookiesSet = new HashSet<>(originalResponse.headers("Set-Cookie"));

                SharedPreferences.Editor config = mContext.getSharedPreferences(NetConstant.API_SP_NAME_NET, MODE_PRIVATE)
                        .edit();
                config.putStringSet(NetConstant.API_SP_KEY_NET_COOKIE_SET, cookiesSet);
                config.apply();
            }


            return originalResponse;
        }
    }

    /**
     * 添加header包含cookie拦截器
     */
    public class AddHeaderInterceptor implements Interceptor {
        private Context mContext;

        public AddHeaderInterceptor(Context context) {
            mContext = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            SharedPreferences preferences = mContext.getSharedPreferences(NetConstant.API_SP_NAME_NET,
                    Context.MODE_PRIVATE);

            //添加cookie
            HashSet<String> cookieSet = (HashSet<String>) preferences.getStringSet(NetConstant.API_SP_KEY_NET_COOKIE_SET, null);
            if (cookieSet != null) {
                for (String cookie : cookieSet) {
                    builder.addHeader("Cookie", cookie);
                }
            }

            //添加用户登录认证
            String auth = preferences.getString(NetConstant.API_SP_KEY_NET_HEADER_AUTH, null);
            if (auth != null) {
                builder.addHeader("Authorization", auth);
            }

            return chain.proceed(builder.build());
        }
    }

    public class MyInterceptor implements Interceptor {

        //该构造方法只会调用一次
        public MyInterceptor() {
        }

        @NonNull
        @Override
        public Response intercept(@NonNull Chain chain) {
            Request request = chain.request();
            Request.Builder builder = request.newBuilder();

            Response response = null;
            String responseInfo = "";
            try {
                //添加公共请求参数
                response = chain.proceed(builder.build());
                responseInfo = response.peekBody(1024 * 1024).string();
                //打印响应结果
                httpLog(request, responseInfo);
            } catch (Exception e) {

            }


            return response;
        }

        private String getBase64(Map map) {
            return Base64.encodeToString(new JSONObject(map).toString().getBytes(), Base64.NO_WRAP);
        }

        private void httpLog(Request request, String responseInfo) {
            if (BuildConfig.DEBUG) {
                Log.d("xiaoxin", "----------Start-----------");
                //打印请求Ulr
                Log.d("xiaoxinRequest", String.format("Sending request %s",
                        request.url()));

                //打印请求参数
                String method = request.method();
                if ("POST".equals(method)) {
                    StringBuilder sb = new StringBuilder();
                    if (request.body() instanceof FormBody) {
                        FormBody formBody = (FormBody) request.body();
                        for (int i = 0; i < formBody.size(); i++) {
                            sb.append(formBody.encodedName(i)).append("=").append(EncodeUtils.urlDecode(formBody.encodedValue(i)).replace("<", "<").replace(">", ">").replace("%24", "$")).append(",");
                        }

                        if (sb.length() > 1) {
                            sb.delete(sb.length() - 1, sb.length());
                        }
                        Log.d("xiaoxinRequestBody", "{" + sb.toString() + "}");
                    }
                }
                Log.d("xiaoxinResponse", responseInfo);
//            Log.d("retrofitResponseCode", "响应码:" + response.code());
                Log.d("xiaoxin", "----------end-----------");
            }
        }
    }

    public <T> T createService(Class<T> service) {
        return mRetrofit.create(service);
    }
}


