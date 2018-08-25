package com.nosto.graphql.recommendations;

class AbstractRecommendations {

    @FunctionalInterface
    public interface Callback<P> {
        void call(P data);
    }
}
