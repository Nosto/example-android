package com.nosto.graphql.recommendations;

import android.annotation.SuppressLint;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.api.internal.Optional;
import com.apollographql.apollo.exception.ApolloException;
import com.nosto.graphql.request.MyClientFactory;

import javax.annotation.Nonnull;

import graphql.SessionMutation;
import graphql.type.InputCustomerInfoEntity;
import graphql.type.InputSessionParams;

public class HomePageRecommendations extends AbstractRecommendations {

    private static final String TAG = HomePageRecommendations.class.getSimpleName();
    private String customerId;

    public HomePageRecommendations(String customerId) {
        this.customerId = customerId;
    }

    public void load(final Callback<SessionMutation.Data> callable) {
        SessionMutation mutation = SessionMutation.builder()
                .id(customerId)
                .params(InputSessionParams.builder()
                        .customer(InputCustomerInfoEntity.builder()
                                .customerReference(customerId)
                                .build())
                        .build())
                .build();

        MyClientFactory.newInstance().mutate(mutation).enqueue(
                new ApolloCall.Callback<Optional<SessionMutation.Data>>() {

                    @SuppressLint("CheckResult")
                    @Override
                    public void onResponse(@Nonnull Response<Optional<SessionMutation.Data>> response) {
                        response.data().apply(callable::call);
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.e(TAG, e.getMessage());
                    }
                });
    }
}