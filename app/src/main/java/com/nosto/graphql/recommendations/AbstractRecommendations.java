package com.nosto.graphql.recommendations;

/**
 * Abstract base class for a recommendation loading callback. It is recommended that every fragment's
 * recommendation implement this.
 *
 * @author mridang
 */
class AbstractRecommendations {

    @FunctionalInterface
    public interface Callback<P> {
        void call(P data);
    }
}
