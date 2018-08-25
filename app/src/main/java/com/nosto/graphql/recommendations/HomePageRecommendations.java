package com.nosto.graphql.recommendations;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.nosto.graphql.MyClientFactory;

import java.util.Optional;

import javax.annotation.Nonnull;

import graphql.SessionMutation;
import graphql.type.InputCustomerInfoEntity;
import graphql.type.InputSessionParams;

public class HomePageRecommendations extends AbstractRecommendations {

    private static final String TAG = HomePageRecommendations.class.getSimpleName();

    public static void load(final Callback<SessionMutation.Data> callable) {
        SessionMutation mutation = SessionMutation.builder()
                .id("a2aa02ff-324e-4a97-935e-0bee89dde3ef")
                .params(InputSessionParams.builder()
                        .customer(InputCustomerInfoEntity.builder()
                                .firstName("Moo")
                                .lastName("Moo")
                                .build())
                        .build())
                .build();

        MyClientFactory.newInstance().mutate(mutation).enqueue(
                new ApolloCall.Callback<SessionMutation.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<SessionMutation.Data> response) {
                        Optional.ofNullable(response.data()).ifPresent(data -> {
                            Log.d(TAG, data.toString());
                            callable.call(data);
                        });
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.e(TAG, e.getMessage());
                    }
                });
    }
}
