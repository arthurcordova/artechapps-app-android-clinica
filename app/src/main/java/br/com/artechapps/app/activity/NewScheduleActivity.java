package br.com.artechapps.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import br.com.artechapps.app.R;
import br.com.artechapps.app.adapter.RVAdapterProductSchedule;
import br.com.artechapps.app.database.PersistenceProduct;
import br.com.artechapps.app.model.Product;

public class NewScheduleActivity extends AppCompatActivity {

    private RecyclerView mRvProduct;
    private PersistenceProduct mPersistence;
    private ArrayList<Product> mList;
    private RVAdapterProductSchedule mRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);

        mRvProduct = (RecyclerView) findViewById(R.id.rvProducts);

//        new AsyncTaskProduct("Carregando produtos...",this,true, mRvProduct, ).execute(String.valueOf(BuildConfig.FILIAL));

        mPersistence = new PersistenceProduct(this);
        mList = mPersistence.getProduct();

        mRvAdapter = new RVAdapterProductSchedule(mList, this);

        mRvProduct.setLayoutManager(new LinearLayoutManager(this));
        mRvProduct.setItemAnimator(new DefaultItemAnimator());
        mRvProduct.setAdapter(mRvAdapter);

        mPersistence.close();


    }
}
