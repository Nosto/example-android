package com.nosto.graphql.request.interceptors;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Simple interceptor class that adds a Basic authentication header to every request.
 * More information how Nosto API authentication can be found here:
 * https://developer.nosto.com/#authentication
 *
 * @author mridang
 */
public class BasicAuthInterceptor implements Interceptor {

    private final String credentials;

    public BasicAuthInterceptor(String username, String password) {
        this.credentials = Credentials.basic(username, password);
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder()
                .method(original.method(), original.body())
                .addHeader("Authorization", credentials);
        return chain.proceed(builder.build());
    }
}
