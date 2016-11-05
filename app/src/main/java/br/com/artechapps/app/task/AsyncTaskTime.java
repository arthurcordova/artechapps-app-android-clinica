package br.com.artechapps.app.task;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import br.com.artechapps.app.adapter.RVAdapterDoctor;
import br.com.artechapps.app.adapter.RVAdapterTimes;
import br.com.artechapps.app.model.Product;
import br.com.artechapps.app.utils.EndPoints;

/**
 * Created by arthurcordova on 8/13/16.
 */
public class AsyncTaskTime extends AsyncTaskHttp {

    private JSONArray mJson;
    private RecyclerView mRecyclerView;
    private Product mProduct;

    public AsyncTaskTime(Context context, RecyclerView recyclerView, Product product) {
        mContext = context;
        mMsg = "Atualizando horários...";
        mShowDialog = true;
        mRecyclerView = recyclerView;
        mProduct = product;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String code = params[0];
            String product = params[1];
            String date = params[2];
            mJson = connectServerA(EndPoints.TIME.concat(code).concat("/")
                    .concat(product).concat("/").concat(date), null, true);
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

            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < mJson.length(); i++) {
                try {
                    JSONObject json = mJson.getJSONObject(i);
                    String [] d = json.getString("horarioInicial").split(":");
                    list.add(d[0].concat(":").concat(d[1]));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            RVAdapterTimes rvAdapter = new RVAdapterTimes(list, mProduct);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(rvAdapter);

        }
    }
}



//    http://www2.beautyclinic.com.br/clinwebservice2/servidor/horarios/3/143/12-08-2016
