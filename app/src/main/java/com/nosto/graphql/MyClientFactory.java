package com.nosto.graphql;

import com.apollographql.apollo.ApolloClient;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

public class MyClientFactory {

    private MyClientFactory() {}

    public static final ApolloClient newInstance() {
        OkHttpClient transport = new Builder()
                .addInterceptor(new NostoInterceptor())
                .build();
        return ApolloClient.builder()
                .serverUrl("https://api.nosto.com/v1/graphql")
                .okHttpClient(transport)
                .build();
    }
}
