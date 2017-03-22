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
import br.com.artechapps.app.activity.DetailMessageActivity;
import br.com.artechapps.app.activity.MainMenuActivity;
import br.com.artechapps.app.model.Execution;
import br.com.artechapps.app.model.Message;

/**
 * Created by acstapassoli on 07/12/2015.
 */
public class RVAdapterExecution extends RecyclerView.Adapter<RVAdapterExecution.ViewHolder> {

    private ArrayList<Execution> mItemsData;
    private MainMenuActivity mActivity;

    public RVAdapterExecution(ArrayList<Execution> itemsData, MainMenuActivity activity) {
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
        final Execution model = mItemsData.get(position);
//        viewHolder.tvCode.setText(String.valueOf(model.getId()));

        viewHolder.tvTitle.setText(model.getProduto());
        viewHolder.tvDescription.setText(model.getExecutor());
        viewHolder.tvSentDate.setText(model.getData());

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
        LinearLayout lContent;


        MainMenuActivity mActivity;

        public ViewHolder(View itemLayoutView, final MainMenuActivity activity) {
            super(itemLayoutView);
            mActivity = activity;
            tvCode = (TextView) itemLayoutView.findViewById(R.id.code);
            tvTitle = (TextView) itemLayoutView.findViewById(R.id.title);
            tvDescription = (TextView) itemLayoutView.findViewById(R.id.description);
            tvSentDate = (TextView) itemLayoutView.findViewById(R.id.sent_date);
            lContent = (LinearLayout) itemLayoutView.findViewById(R.id.content);
        }
    }
}
