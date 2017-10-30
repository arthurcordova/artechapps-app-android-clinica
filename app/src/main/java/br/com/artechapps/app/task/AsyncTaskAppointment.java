package br.com.artechapps.app.task;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import br.com.artechapps.app.activity.MainMenuActivity;
import br.com.artechapps.app.adapter.RVAdapterSchedule;
import br.com.artechapps.app.database.PersistenceSchedule;
import br.com.artechapps.app.livedata.SchedulingLiveData;
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
    private SwipeRefreshLayout mSrl;
    private TextView mNullText;
    private static final int TIME_ANIMATION = 1000;
    public SchedulingLiveData mLiveData;

    public AsyncTaskAppointment(String msg, Context context, boolean showDialog, RecyclerView recyclerView, MainMenuActivity activity, SwipeRefreshLayout srl, TextView nullText) {
        mMsg = msg;
        mContext = context;
        mShowDialog = showDialog;
        mRecyclerView = recyclerView;
        mActivity = activity;
        mSrl = srl;
        mNullText = nullText;
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
                mPersistence.remove();
                mPersistence.save(mJson);
            } finally {
                mPersistence.close();
            }

            mPersistence = new PersistenceSchedule(mContext);
            mList = mPersistence.getRecords();

            if (mRecyclerView != null) {
                RVAdapterSchedule adapter = new RVAdapterSchedule(mList, mActivity);

                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(adapter);

                mNullText.setVisibility(View.INVISIBLE);
            } else {
                int counter =  mPersistence.count();
                if (mLiveData != null){
                    mLiveData.getCurrentName().setValue(String.valueOf(counter));
                }
            }
            mPersistence.close();

        } else {
            if (mNullText != null) {
                mNullText.setVisibility(View.VISIBLE);
            }
        }
        if (mSrl != null) {
            mSrl.setRefreshing(false);
        }
    }

    private void setAnimationCounter(int value, int duration, final TextView textView) {
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, value);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setText(String.valueOf(animation.getAnimatedValue()));
            }
        });
        animator.setEvaluator(new TypeEvaluator<Integer>() {
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                return Math.round(startValue + (endValue - startValue) * fraction);
            }
        });
        animator.setDuration(duration);
        animator.start();

    }
}
