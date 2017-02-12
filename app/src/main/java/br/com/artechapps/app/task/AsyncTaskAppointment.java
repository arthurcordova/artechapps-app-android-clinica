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
import br.com.artechapps.app.adapter.RVAdapterSchedule;
import br.com.artechapps.app.database.PersistenceSchedule;
import br.com.artechapps.app.model.Schedule;
import br.com.artechapps.app.utils.EndPoints;

/**
 * Created by arthurcordova on 7/16/16.
 */
public class AsyncTaskAppointment extends AsyncTaskHttp {

    private JSONArray mJson;
    private PersistenceSchedule mPersistence;
    private RecyclerView mRecyclerView;
    private ArrayList<Schedule> mList;
    private MainMenuActivity mActivity;

    public AsyncTaskAppointment(String msg, Context context, boolean showDialog, RecyclerView recyclerView, MainMenuActivity activity) {
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
            mJson = connectServerA(EndPoints.SCHEDULES.concat(code), null, true);
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
            try {
                mPersistence = new PersistenceSchedule(mContext);
                mPersistence.save(mJson);
            } finally {
                mPersistence.close();
            }

            mPersistence = new PersistenceSchedule(mContext);
            mList = mPersistence.getRecords();

            RVAdapterSchedule adapter = new RVAdapterSchedule(mList, mActivity);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(adapter);

            mPersistence.close();
        }
    }
}
