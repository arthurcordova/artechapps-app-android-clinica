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
import br.com.artechapps.app.activity.DetailBudgetActivity;
import br.com.artechapps.app.activity.MainMenuActivity;
import br.com.artechapps.app.model.Budget;
import br.com.artechapps.app.model.Product;

/**
 * Created by acstapassoli on 07/12/2015.
 */
public class RVAdapterBudget extends RecyclerView.Adapter<RVAdapterBudget.ViewHolder> {

    private ArrayList<Budget> mItemsData;
    private MainMenuActivity mActivity;

    public RVAdapterBudget(ArrayList<Budget> itemsData, MainMenuActivity activity) {
        mItemsData = itemsData;
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_budget, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView, mActivity);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Budget model = mItemsData.get(position);
        viewHolder.tvCode.setText(String.valueOf(model.getCode()));
        viewHolder.tvTitle.setText("Orçamento: " + String.valueOf(model.getCode()));
        viewHolder.tvTotalValue.setText("Valor: R$ " + Product.formatValue(model.getValueTotal()));
        viewHolder.tvDate.setText(model.getDate());
        viewHolder.lContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(v.getContext(), DetailBudgetActivity.class);
                it.putExtra("model", model);
                v.getContext().startActivity(it);
            }
        });

    }

    public void setFilter(ArrayList<Budget> messages) {
        mItemsData = new ArrayList<>();
        mItemsData.addAll(messages);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (mItemsData != null) ? mItemsData.size() : 0;
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCode;
        TextView tvTitle;
        TextView tvTotalValue;
        TextView tvDate;
        LinearLayout lContent;

        MainMenuActivity mActivity;

        public ViewHolder(View itemLayoutView, final MainMenuActivity activity) {
            super(itemLayoutView);
            mActivity = activity;
            tvCode = (TextView) itemLayoutView.findViewById(R.id.code);
            tvTitle = (TextView) itemLayoutView.findViewById(R.id.title);
            tvTotalValue = (TextView) itemLayoutView.findViewById(R.id.tv_total_value);
            tvDate = (TextView) itemLayoutView.findViewById(R.id.tv_date);
            lContent = (LinearLayout) itemLayoutView.findViewById(R.id.content);

        }
    }
}
