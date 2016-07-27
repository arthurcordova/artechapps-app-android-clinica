package br.com.artechapps.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.artechapps.app.R;
import br.com.artechapps.app.activity.CartActivity;
import br.com.artechapps.app.database.PersistenceShop;
import br.com.artechapps.app.model.Product;
import br.com.artechapps.app.model.Shop;

/**
 * Created by acstapassoli on 07/12/2015.
 */
public class RVAdapterCart extends RecyclerView.Adapter<RVAdapterCart.ViewHolder> {

    private ArrayList<Shop> mItemsData;
    private CartActivity mActivity;

    public RVAdapterCart(ArrayList<Shop> itemsData, CartActivity activity) {
        mItemsData = itemsData;
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_cart, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView, mActivity);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Shop model = mItemsData.get(position);

        viewHolder.tvCode.setText(String.valueOf(model.getId()));
        viewHolder.tvDescription.setText(model.getProduct().getDescription());
        viewHolder.tvValue.setText(Product.formatValue(model.getProduct().getValue()));

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
        ImageView imgRemove;

        CartActivity mActivity;

        public ViewHolder(View itemLayoutView, final CartActivity activity) {
            super(itemLayoutView);
            mActivity = activity;
            tvCode = (TextView) itemLayoutView.findViewById(R.id.code);
            tvDescription = (TextView) itemLayoutView.findViewById(R.id.description);
            tvValue = (TextView) itemLayoutView.findViewById(R.id.value);
            tvAdd = (TextView)itemLayoutView.findViewById(R.id.add);
            imgRemove = (ImageView) itemLayoutView.findViewById(R.id.img_remove);

            imgRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersistenceShop persistence = new PersistenceShop(v.getContext());
                    try {
                        persistence.remove(Long.valueOf(tvCode.getText().toString()));
                        mActivity.finish();
                        mActivity.startActivity(mActivity.getIntent());

                    } finally {
                        persistence.close();
                    }
                }
            });

        }


    }
}
