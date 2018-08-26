package com.nosto.graphql.request.interceptors;

import android.support.annotation.NonNull;

import com.nosto.graphql.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DebugModeInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        if (BuildConfig.DEBUG) {
            Request.Builder builder = original.newBuilder()
                    .method(original.method(), original.body())
                    .addHeader("X-Nosto-Ignore", "True");
            return chain.proceed(builder.build());
        } else {
            return chain.proceed(original);
        }
    }
}
