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
import br.com.artechapps.app.adapter.RVAdapterBudget;
import br.com.artechapps.app.database.PersistenceBudget;
import br.com.artechapps.app.model.Budget;
import br.com.artechapps.app.utils.EndPoints;

/**
 * Created by arthurcordova on 7/16/16.
 */
public class AsyncTaskBudget extends AsyncTaskHttp {

    private JSONArray mJson;
    private PersistenceBudget mPersistence;
    private RecyclerView mRecyclerView;
    private ArrayList<Budget> mList;
    private MainMenuActivity mActivity;

    public AsyncTaskBudget(String msg, Context context, boolean showDialog, RecyclerView recyclerView, MainMenuActivity activity) {
        mContext = context;
        mShowDialog = showDialog;
        mRecyclerView = recyclerView;
        mActivity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String code = params[0];
            mJson = connectServerA(EndPoints.MONEY_PER_CLIENT.concat(code), null, true);
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
        if (mJson != null && mJson.length() > 0) {
            try {
                mPersistence = new PersistenceBudget(mContext);
                mPersistence.save(mJson);
            } finally {
                mPersistence.close();
            }

            try {
                mPersistence = new PersistenceBudget(mContext);
                mList = mPersistence.getRecords();

                RVAdapterBudget adapter = new RVAdapterBudget(mList, mActivity);

                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(adapter);

            } finally {
                mPersistence.close();

            }

        }

    }

}
