package br.com.artechapps.app.task;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by arthurcordova on 7/16/16.
 */
public class AsyncTaskDoctor extends AsyncTaskHttp {

    private JSONArray mJson;
    private RecyclerView mRecyclerView;
//    private ArrayList<Product> mList;
//    private MainMenuActivity mActivity;

    public AsyncTaskDoctor(String msg, Context context, boolean showDialog) {
        mMsg = msg;
        mContext = context;
        mShowDialog = showDialog;
//        mRecyclerView = recyclerView;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String code = params[0];
            mJson = connectServerA("http://www2.beautyclinic.com.br/clinwebservice2/servidor/medicos/".concat(code), null, true);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        http://www2.beautyclinic.com.br/clinwebservice/servidor/medicos/3
//        http://www2.beautyclinic.com.br/clinwebservice2/servidor/medicos/3

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (mJson != null && mJson.length() > 0){
//            mPersistence = new PersistenceProduct(mContext);
//            mPersistence.save(mJson);
//
//            mList = mPersistence.getProduct();
//
//            RVAdapterProduct mAdapterPatient = new RVAdapterProduct(mList, mActivity);
//
//            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//            mRecyclerView.setAdapter(mAdapterPatient);
//
//            mPersistence.close();

        }
    }
}
