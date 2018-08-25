package com.nosto.graphql;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nosto.graphql.recommendations.HomePageRecommendations;

import java.util.ArrayList;
import java.util.List;

import graphql.SessionMutation;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private List<SectionDataModel> allSampleData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HomePageRecommendations.load(data -> MainActivity.this.runOnUiThread(() -> {
            createDummyData(data);

            RecyclerView my_recycler_view = findViewById(R.id.my_recycler_view);
            my_recycler_view.setHasFixedSize(true);
            RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(getApplicationContext(), allSampleData);
            my_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            my_recycler_view.setAdapter(adapter);
        }));

        setContentView(R.layout.activity_main);
    }

    public void createDummyData(SessionMutation.Data data) {
        SectionDataModel dm = new SectionDataModel("Popular Right Now");
        data.updateSession().recos()
                .xx()
                .primary()
                .stream()
                .map(product -> new SingleItemModel(product.name(), product.productId(), product.price(), "XXX", product.imageUrl()))
                .forEach(dm::addProduct);
        allSampleData.add(dm);

        dm = new SectionDataModel("You might also like");
        data.updateSession()
                .recos()
                .yx()
                .primary()
                .stream()
                .map(product -> new SingleItemModel(product.name(), product.productId(), product.price(), "ddd", product.imageUrl()))
                .forEach(dm::addProduct);
        allSampleData.add(dm);
    }
}