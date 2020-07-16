package com.example.nativetest.net.token;

import com.example.nativetest.utils.log.SLog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.RealResponseBody;

public class ScTokenInterceptor implements Interceptor {
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
