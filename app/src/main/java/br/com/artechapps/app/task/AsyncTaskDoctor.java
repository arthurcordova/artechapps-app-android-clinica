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

import br.com.artechapps.app.adapter.RVAdapterDoctor;
import br.com.artechapps.app.model.Doctor;
import br.com.artechapps.app.model.Product;

/**
 * Created by arthurcordova on 7/16/16.
 */
public class AsyncTaskDoctor extends AsyncTaskHttp {

    private JSONArray mJson;
    private RecyclerView mRecyclerView;
    private ArrayList<Doctor> mList;
    private RVAdapterDoctor mRvAdapter;
    private Product mProduct;

    public AsyncTaskDoctor(String msg, Context context, boolean showDialog, RecyclerView recyclerView, Product product) {
        mMsg = msg;
        mContext = context;
        mShowDialog = showDialog;
        mRecyclerView = recyclerView;
        mProduct = product;
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

            mList = new ArrayList<>();
            for (int i = 0; i < mJson.length(); i++) {
                try {
                    JSONObject json = mJson.getJSONObject(i);
                    Doctor doctor = new Doctor();
                    doctor.setCode(json.getLong("codFunc"));
                    doctor.setName(json.getString("nome"));
                    mList.add(doctor);

                } catch (JSONException e) {
                    Log.e("JSON", e.getMessage());
                }
            }

            mRvAdapter = new RVAdapterDoctor(mList, mProduct);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(mRvAdapter);

        }
    }
}
