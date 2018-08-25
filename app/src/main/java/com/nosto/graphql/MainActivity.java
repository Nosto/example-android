package com.nosto.graphql;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.apollographql.apollo.api.internal.Function;
import com.apollographql.apollo.api.internal.Optional;
import com.nosto.graphql.adapters.RecyclerViewDataAdapter;
import com.nosto.graphql.adapters.SectionDataModel;
import com.nosto.graphql.adapters.SingleItemModel;
import com.nosto.graphql.recommendations.HomePageRecommendations;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import graphql.SessionMutation;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

/**
 * Main activity class that is loaded when the application starts. This activity is defined in the
 * manifest file.
 *
 * @author mridang
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class MainActivity extends AppCompatActivity {

    private List<SectionDataModel> allSampleData = new ArrayList<>();

    /**
     * Method that begins loading the data when the application starts. This method initializes
     * the activity with the layout definition and registers a callback to populate the
     * recommendations.
     *
     * @param state the saved instance state
     */
    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        // Notice that the device-id is used as the customer reference. This is just for for demo
        // purposes and once the user has been logged in, the actual customer's reference should
        // be used.
        String reference = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        new HomePageRecommendations(reference)
                .load(data -> runOnUiThread(() -> {
                    populate(data);

                    RecyclerView my_recycler_view = findViewById(R.id.my_recycler_view);
                    my_recycler_view.setHasFixedSize(true);
                    RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(getApplicationContext(), allSampleData);
                    my_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    my_recycler_view.setAdapter(adapter);
                }));

        setContentView(R.layout.activity_main);
    }

    /**
     * Consumes the result and adds to the section list holder. Since any node in the GraphQL result
     * can be null, Apollo has been configured to return Optionals from each of the accessors.
     * This mode is not enabled by default and must be configured in your build.gradle file using:
     *
     * <code>
     *  apollo {
     *    nullableValueType = "apolloOptional"
     *  }
     * </code>
     *
     * @param data the resultant recommender data
     */
    @SuppressLint("CheckResult")
    public void populate(SessionMutation.Data data) {
        data.updateSession()
                .flatMap(new Function<SessionMutation.UpdateSession, Optional<SessionMutation.Recos>>() {
                    @Nonnull
                    @Override
                    public Optional<SessionMutation.Recos> apply(@Nonnull SessionMutation.UpdateSession updateSession) {
                        return updateSession.recos();
                    }
                })
                .apply(recos -> {
                    recos.yx().apply(yx -> yx.primary().apply(results -> {
                        allSampleData.add(results.stream()
                                .map(product -> new SingleItemModel.Builder()
                                        .setName(product.name().orNull())
                                        .setPrice(product.price().orNull())
                                        .setCurrency("ddd")
                                        .setImage(product.imageUrl().orNull())
                                        .build())
                                .collect(collectingAndThen(toList(), products -> new SectionDataModel.Builder()
                                        .setTitle(getString(R.string.popular_right_now))
                                        .setProducts(products)
                                        .build())));
                    }));

                    recos.xx().apply(xx -> xx.primary().apply(results -> {
                        allSampleData.add(results.stream()
                                .map(product -> new SingleItemModel.Builder()
                                        .setName(product.name().orNull())
                                        .setPrice(product.price().orNull())
                                        .setCurrency("ddd")
                                        .setImage(product.imageUrl().orNull())
                                        .build())
                                .collect(collectingAndThen(toList(), products -> new SectionDataModel.Builder()
                                        .setTitle(getString(R.string.you_might_like))
                                        .setProducts(products)
                                        .build())));
                    }));
                });
    }
}