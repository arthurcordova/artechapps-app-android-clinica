package br.com.artechapps.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.artechapps.app.R;
import br.com.artechapps.app.adapter.RVAdapterCart;
import br.com.artechapps.app.database.PersistenceShop;
import br.com.artechapps.app.model.Product;
import br.com.artechapps.app.model.Shop;

public class CartActivity extends AppCompatActivity {

    private RecyclerView mRvCart;
    private PersistenceShop mPersistence;
    private ArrayList<Shop> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRvCart = (RecyclerView) findViewById(R.id.rv_cart_detail);
        mPersistence = new PersistenceShop(this);
        mList = mPersistence.getRecords();

        RVAdapterCart mAdapter = new RVAdapterCart(mList, this);
        mAdapter.notifyDataSetChanged();

        mRvCart.setLayoutManager(new LinearLayoutManager(this));
        mRvCart.setItemAnimator(new DefaultItemAnimator());
        mRvCart.setAdapter(mAdapter);

        final TextView total = (TextView) findViewById(R.id.total_value);

        PersistenceShop persistence = new PersistenceShop(this);
        ArrayList<Shop> list = persistence.getRecords();
        persistence.close();
        double totalValue = 0d;
        for (Shop model : list){
            totalValue += (model.getProduct().getValue() * model.getAmount()) ;
        }
        total.setText(String.valueOf(Product.formatValue(totalValue)));

    }
}
