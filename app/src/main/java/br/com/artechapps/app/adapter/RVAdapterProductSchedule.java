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
import br.com.artechapps.app.activity.NewScheduleActivity;
import br.com.artechapps.app.activity.NewScheduleDoctorActivity;
import br.com.artechapps.app.activity.NewScheduleFinalActivity;
import br.com.artechapps.app.activity.NewScheduleTimeActivity;
import br.com.artechapps.app.model.Product;

/**
 * Created by acstapassoli on 07/12/2015.
 */
public class RVAdapterProductSchedule extends RecyclerView.Adapter<RVAdapterProductSchedule.ViewHolder> {

    private ArrayList<Product> mItemsData;
    private NewScheduleActivity mActivity;

    public RVAdapterProductSchedule(ArrayList<Product> itemsData, NewScheduleActivity activity) {
        mItemsData = itemsData;
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_product_schedule, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView, mActivity);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Product model = mItemsData.get(position);
        viewHolder.tvCode.setText(String.valueOf(model.getId()));

        viewHolder.tvDescription.setText(model.getDescription());
        viewHolder.tvAdd.setText("R$ " + model.formatValue(model.getValue()));
        viewHolder.lContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it;
                if (model.getType().equals("M")){
                    it = new Intent(v.getContext(), NewScheduleDoctorActivity.class);
                    it.putExtra("model", model);
                } else {
                    it = new Intent(v.getContext(), NewScheduleTimeActivity.class);
                    it.putExtra("model", model);
                }
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

        TextView tvCode;
        TextView tvDescription;
        TextView tvValue;
        TextView tvAdd;
        LinearLayout lContent;

        NewScheduleActivity mActivity;

        public ViewHolder(View itemLayoutView, final NewScheduleActivity activity) {
            super(itemLayoutView);
            mActivity = activity;
            tvCode = (TextView) itemLayoutView.findViewById(R.id.code);
            tvDescription = (TextView) itemLayoutView.findViewById(R.id.description);
            tvValue = (TextView) itemLayoutView.findViewById(R.id.value);
            tvAdd = (TextView)itemLayoutView.findViewById(R.id.add);
            lContent = (LinearLayout) itemLayoutView.findViewById(R.id.content);

//            tvAdd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Shop s = new Shop();
//                    Product p = new Product();
//                    p.setId(Long.parseLong(tvCode.getText().toString()));
//                    s.setProduct(p);
//
//                    PersistenceShop persistenceShop = null;
//                    try {
//                        persistenceShop = new PersistenceShop(activity);
//                        persistenceShop.save(s);
//                        Toast.makeText(v.getContext(), "Produto adicionado ao carrinho.", Toast.LENGTH_SHORT).show();
//
//                    } finally {
//                        persistenceShop.close();
//                    }
//                }
//            });
        }
    }
}
