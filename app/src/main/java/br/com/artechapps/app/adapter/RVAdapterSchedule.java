package br.com.artechapps.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.artechapps.app.R;
import br.com.artechapps.app.activity.MainMenuActivity;
import br.com.artechapps.app.model.Schedule;

/**
 * Created by acstapassoli on 07/12/2015.
 */
public class RVAdapterSchedule extends RecyclerView.Adapter<RVAdapterSchedule.ViewHolder> {

    private ArrayList<Schedule> mItemsData;
    private MainMenuActivity mActivity;

    public RVAdapterSchedule(ArrayList<Schedule> itemsData, MainMenuActivity activity) {
        mItemsData = itemsData;
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_message, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView, mActivity);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Schedule model = mItemsData.get(position);
        viewHolder.tvCode.setText(String.valueOf(model.getCode()));

        viewHolder.tvTitle.setText(model.getStatus());
//        viewHolder.tvDescription.setText(model.getMessage());
//        viewHolder.tvSentDate.setText(model.getSentDate());

    }

    @Override
    public int getItemCount() {
        return (mItemsData != null) ? mItemsData.size() : 0;
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCode;
        TextView tvTitle;
        TextView tvDescription;
        TextView tvSentDate;


        MainMenuActivity mActivity;

        public ViewHolder(View itemLayoutView, final MainMenuActivity activity) {
            super(itemLayoutView);
            mActivity = activity;
            tvCode = (TextView) itemLayoutView.findViewById(R.id.code);
            tvTitle = (TextView) itemLayoutView.findViewById(R.id.title);
            tvDescription = (TextView) itemLayoutView.findViewById(R.id.description);
            tvSentDate = (TextView) itemLayoutView.findViewById(R.id.sent_date);


        }
    }
}
