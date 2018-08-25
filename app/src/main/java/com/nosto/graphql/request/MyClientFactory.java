package com.nosto.graphql.request;

import com.apollographql.apollo.ApolloClient;
import com.nosto.graphql.request.interceptors.BasicAuthInterceptor;
import com.nosto.graphql.request.interceptors.DebugModeInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

public class MyClientFactory {

    private static final String TOKEN = "5VsztSfLhrGDXOsbs1ZqMHWgBOwICfmhIWCocVRICaSDeUMF9kQ3mz4oSxl76jUE";

    private MyClientFactory() {
    }

    public static ApolloClient newInstance() {
        OkHttpClient transport = new Builder()
                .addInterceptor(new BasicAuthInterceptor("", TOKEN))
                .addInterceptor(new DebugModeInterceptor())
                .build();
        return ApolloClient.builder()
                .serverUrl("https://api.nosto.com/v1/graphql")
                .okHttpClient(transport)
                .build();
    }
}
