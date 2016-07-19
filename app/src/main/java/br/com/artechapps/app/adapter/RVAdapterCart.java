package br.com.artechapps.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.artechapps.app.R;
import br.com.artechapps.app.activity.CartActivity;
import br.com.artechapps.app.activity.MainMenuActivity;
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
        Shop model = mItemsData.get(position);
//        viewHolder.tvCode.setText(String.valueOf(model.getId()));

        viewHolder.tvDescription.setText(model.getProduct().getDescription());
//        viewHolder.tvValue.setText(model.formatValue(model.getValue()));

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

        CartActivity mActivity;

        public ViewHolder(View itemLayoutView, final CartActivity activity) {
            super(itemLayoutView);
            mActivity = activity;
            tvCode = (TextView) itemLayoutView.findViewById(R.id.code);
            tvDescription = (TextView) itemLayoutView.findViewById(R.id.description);
            tvValue = (TextView) itemLayoutView.findViewById(R.id.value);
            tvAdd = (TextView)itemLayoutView.findViewById(R.id.add);

//            tvAdd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mActivity.setCounter(1);
//                    mActivity.updateShopCart();
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
//                    } finally {
//                        persistenceShop.close();
//                    }
//                }
//            });
        }
    }
}
