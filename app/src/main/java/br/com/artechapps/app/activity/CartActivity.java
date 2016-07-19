package br.com.artechapps.app.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import br.com.artechapps.app.R;
import br.com.artechapps.app.adapter.RVAdapterCart;
import br.com.artechapps.app.adapter.RVAdapterProduct;
import br.com.artechapps.app.database.PersistenceProduct;
import br.com.artechapps.app.database.PersistenceShop;
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

        mRvCart = (RecyclerView) findViewById(R.id.rv_cart_detail);


        mPersistence = new PersistenceShop(this);
        mList = mPersistence.getRecords();

        RVAdapterCart mAdapter = new RVAdapterCart(mList, this);

        mRvCart.setLayoutManager(new LinearLayoutManager(this));
        mRvCart.setItemAnimator(new DefaultItemAnimator());
        mRvCart.setAdapter(mAdapter);



    }

}
