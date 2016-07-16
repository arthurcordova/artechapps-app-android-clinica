package br.com.artechapps.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.artechapps.app.R;

/**
 * Created by acstapassoli on 07/12/2015.
 */
public class RVAdapterProduct extends RecyclerView.Adapter<RVAdapterProduct.ViewHolder> {

    private List mItemsData;

    public RVAdapterProduct(List itemsData) {
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
        String hours = (String) mItemsData.get(position);
        viewHolder.tvHour.setText(hours);

    }

    @Override
    public int getItemCount() {
        return (mItemsData != null) ? mItemsData.size() : 0;
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvHour;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            tvHour = (TextView) itemLayoutView.findViewById(R.id.code);
        }
    }
}
