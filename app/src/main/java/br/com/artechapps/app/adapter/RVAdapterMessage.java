package br.com.artechapps.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.artechapps.app.R;
import br.com.artechapps.app.activity.MainMenuActivity;
import br.com.artechapps.app.model.Message;

/**
 * Created by acstapassoli on 07/12/2015.
 */
public class RVAdapterMessage extends RecyclerView.Adapter<RVAdapterMessage.ViewHolder> {

    private ArrayList<Message> mItemsData;
    private MainMenuActivity mActivity;

    public RVAdapterMessage(ArrayList<Message> itemsData, MainMenuActivity activity) {
        mItemsData = itemsData;
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_product, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView, mActivity);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Message model = mItemsData.get(position);
        viewHolder.tvCode.setText(String.valueOf(model.getId()));

        viewHolder.tvDescription.setText(model.getTitle());

    }

    @Override
    public int getItemCount() {
        return (mItemsData != null) ? mItemsData.size() : 0;
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCode;
        TextView tvDescription;
        TextView tvValue;
        TextView tvAdd;

        MainMenuActivity mActivity;

        public ViewHolder(View itemLayoutView, final MainMenuActivity activity) {
            super(itemLayoutView);
            mActivity = activity;
            tvCode = (TextView) itemLayoutView.findViewById(R.id.code);
            tvDescription = (TextView) itemLayoutView.findViewById(R.id.description);
            tvValue = (TextView) itemLayoutView.findViewById(R.id.value);
            tvAdd = (TextView)itemLayoutView.findViewById(R.id.add);


        }
    }
}
