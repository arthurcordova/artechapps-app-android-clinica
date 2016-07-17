package br.com.artechapps.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.artechapps.app.R;
import br.com.artechapps.app.model.Product;

/**
 * Created by acstapassoli on 07/12/2015.
 */
public class RVAdapterProduct extends RecyclerView.Adapter<RVAdapterProduct.ViewHolder> {

    private ArrayList<Product> mItemsData;

    public RVAdapterProduct(ArrayList<Product> itemsData) {
        mItemsData = itemsData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_product, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Product model = mItemsData.get(position);
        viewHolder.tvCode.setText(model.getDescription());

    }

    @Override
    public int getItemCount() {
        return (mItemsData != null) ? mItemsData.size() : 0;
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCode;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            tvCode = (TextView) itemLayoutView.findViewById(R.id.code);
        }
    }
}
