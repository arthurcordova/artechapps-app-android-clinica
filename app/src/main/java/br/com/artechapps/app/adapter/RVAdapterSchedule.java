package br.com.artechapps.app.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.artechapps.app.R;
import br.com.artechapps.app.activity.MainMenuActivity;
import br.com.artechapps.app.activity.NewScheduleFinalActivity;
import br.com.artechapps.app.model.Product;
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
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final Schedule model = mItemsData.get(position);
        viewHolder.tvCode.setText(String.valueOf(model.getCode()));

        viewHolder.tvTitle.setText("Procedimento: " + model.getProduct().getDescription());
        viewHolder.tvDescription.setText("Data: " + model.getDate()+  " Horário: " + model.getTime());
        viewHolder.tvSentDate.setText(model.getStatus());
        viewHolder.lineContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = new Product();
                product.setDescription(model.getProduct().getDescription());
                product.setDateTimeFormatted(viewHolder.tvDescription.getText().toString());
                Intent it = new Intent(mActivity, NewScheduleFinalActivity.class);
                it.putExtra("model", product);
                mActivity.startActivity(it);
            }
        });

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
        View lineContent;

        MainMenuActivity mActivity;

        public ViewHolder(View itemLayoutView, final MainMenuActivity activity) {
            super(itemLayoutView);
            mActivity = activity;
            lineContent = itemLayoutView.findViewById(R.id.content);
            tvCode = (TextView) itemLayoutView.findViewById(R.id.code);
            tvTitle = (TextView) itemLayoutView.findViewById(R.id.title);
            tvDescription = (TextView) itemLayoutView.findViewById(R.id.description);
            tvSentDate = (TextView) itemLayoutView.findViewById(R.id.sent_date);

        }
    }
}
