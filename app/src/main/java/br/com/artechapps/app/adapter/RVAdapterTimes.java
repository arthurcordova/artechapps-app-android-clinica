package br.com.artechapps.app.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.artechapps.app.R;
import br.com.artechapps.app.activity.NewScheduleTimeActivity;
import br.com.artechapps.app.model.Doctor;
import br.com.artechapps.app.model.Product;

/**
 * Created by acstapassoli on 07/12/2015.
 */
public class RVAdapterTimes extends RecyclerView.Adapter<RVAdapterTimes.ViewHolder> {

    private ArrayList<String> mItemsData;
    private Product mProduct;

    public RVAdapterTimes(ArrayList<String> itemsData, Product product) {
        mItemsData = itemsData;
        mProduct = product;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_times, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final String time = mItemsData.get(position);
//        viewHolder.tvCode.setText(String.valueOf(model.getCode()));
        viewHolder.tvTime.setText(time);

        viewHolder.lContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProduct.setTime(time);

                Intent it = new Intent(v.getContext(), NewScheduleTimeActivity.class);
                it.putExtra("model", mProduct);
                v.getContext().startActivity(it);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (mItemsData != null) ? mItemsData.size() : 0;
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime;
        LinearLayout lContent;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvTime = (TextView) itemLayoutView.findViewById(R.id.tv_time);
            lContent = (LinearLayout) itemLayoutView.findViewById(R.id.content);

        }
    }
}
