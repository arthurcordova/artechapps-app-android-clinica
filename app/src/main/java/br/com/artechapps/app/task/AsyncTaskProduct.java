package br.com.artechapps.app.task;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import br.com.artechapps.app.activity.MainMenuActivity;
import br.com.artechapps.app.adapter.RVAdapterProduct;
import br.com.artechapps.app.database.PersistenceProduct;
import br.com.artechapps.app.model.Product;
import br.com.artechapps.app.utils.EndPoints;

/**
 * Created by arthurcordova on 7/16/16.
 */
public class AsyncTaskProduct extends AsyncTaskHttp {

    private JSONArray mJson;
    private PersistenceProduct mPersistence;
    private RecyclerView mRecyclerView;
    private ArrayList<Product> mList;
    private MainMenuActivity mActivity;

    public AsyncTaskProduct(String msg, Context context, boolean showDialog, RecyclerView recyclerView, MainMenuActivity activity) {
        mMsg = msg;
        mContext = context;
        mShowDialog = showDialog;
        mRecyclerView = recyclerView;
        mActivity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String code = params[0];
            mJson = connectServerA(EndPoints.PRODUCTS.concat(code), null, true);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (mJson != null && mJson.length() > 0){
            mPersistence = new PersistenceProduct(mContext);
            mPersistence.save(mJson);

            mList = mPersistence.getProduct();

            RVAdapterProduct mAdapterPatient = new RVAdapterProduct(mList, mActivity);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(mAdapterPatient);

            mPersistence.close();

        }
    }
}
