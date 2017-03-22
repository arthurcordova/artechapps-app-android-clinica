package br.com.artechapps.app.task;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.artechapps.app.activity.MainMenuActivity;
import br.com.artechapps.app.adapter.RVAdapterExecution;
import br.com.artechapps.app.database.PersistenceMessage;
import br.com.artechapps.app.model.Execution;
import br.com.artechapps.app.model.Message;
import br.com.artechapps.app.utils.EndPoints;

/**
 * Created by arthurcordova on 7/16/16.
 */
public class AsyncTaskExecutions extends AsyncTaskHttp {

    private JSONArray mJson;
    private RecyclerView mRecyclerView;
    private ArrayList<Message> mList;
    private MainMenuActivity mActivity;


    public AsyncTaskExecutions(String msg, Context context, boolean showDialog, RecyclerView recyclerView, MainMenuActivity activity) {
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
            mJson = connectServerA(EndPoints.EXECUTIONS.concat(code), null, true);
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

            ArrayList<Execution> list = new ArrayList<>();
            for (int i = 0; i < mJson.length(); i++) {
                try{
                    JSONObject json = mJson.getJSONObject(i);
                    Execution exe = new Execution();
                    exe.setCodigo(json.getInt("codigo"));
                    exe.setProduto(json.getString("produto"));
                    exe.setExecutor(json.getString("executor"));
                    exe.setData(json.getString("data"));
                    exe.setCodOs(json.getInt("codOs"));
                    list.add(exe);

                } catch (JSONException ex) {
                    Log.e("ASYNCEXE", "JSON PARSER ERROR");
                }
            }

            RVAdapterExecution adapter = new RVAdapterExecution(list, mActivity);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(adapter);

        }
    }
}
