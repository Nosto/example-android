package com.nosto.graphql.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nosto.graphql.R;
import com.nosto.graphql.SingleItemModel;

import java.util.List;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private List<SingleItemModel> itemsList;
    private Context mContext;

    public SectionListDataAdapter(Context context, List<SingleItemModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {
        SingleItemModel singleItem = itemsList.get(i);
        holder.tvTitle.setText(singleItem.getName());
        holder.tvPrice.setText(singleItem.getPrice());
        Uri uri = Uri.parse(singleItem.getImage());
        holder.itemImage.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;
        protected TextView tvPrice;
        protected SimpleDraweeView itemImage;

        SingleItemRowHolder(View view) {
            super(view);

            this.tvTitle = view.findViewById(R.id.tvTitle);
            this.tvPrice = view.findViewById(R.id.recommendation_price_text);
            this.itemImage = view.findViewById(R.id.itemImage);

            view.setOnClickListener(v -> Toast.makeText(v.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show());
        }
    }
}