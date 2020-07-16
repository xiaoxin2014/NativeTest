package com.example.nativetest.net;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.nativetest.utils.log.SLog;
import com.google.gson.JsonObject;

import java.io.IOException;

import androidx.annotation.Nullable;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.RealResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;

public class ScInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request requestOld = chain.request();

        Request request = requestOld.newBuilder()
//                .header("Content-Type", "application/json")
                .header("Authorization", getAuthorization())
                .header("DV", getDV())
                .header("LG", getLG())
                .header("VI", getVI())
                .method(requestOld.method(), requestOld.body())
                .build();

        SLog.e("retrofitResponse", String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers(), request.headers("Cookie")));
        Response response = chain.proceed(request);
        ResponseBody body = response.peekBody(1024 * 1024);
        String responseString = body.string();
        SLog.e("retrofitResponse", request.url() + "---------" + responseString);
//        String str ;
//        if(responseString.length()>=2){
//            str = responseString.substring(1,responseString.length()-1);
//            String contentType = body.contentType().toString();
//            int length = str.length();
//            ResponseBody body1 = new RealResponseBody(contentType,length,body.source());
//            Response response1 = response.newBuilder().body(body1).build();
//            SLog.e("retrofitResponse 2", "---------" + JSON.toJSONString(response1));
//            return response1;
//        }
        return response;
    }

    public static String Authorization = "Basic ampBcHBBcGlDbGllbnQ6Q2lyY2xlMjAyMEBXb3JsZA==";

    private static String getAuthorization() {
        return Authorization;
    }

    public static String getDV() {
        return "niko";
    }

    private static String getLG() {
        return "CN";
    }

    private static String getVI() {
        return "A101";
    }
}
