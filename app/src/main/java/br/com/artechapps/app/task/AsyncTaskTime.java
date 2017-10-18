package br.com.artechapps.app.task;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private TextView mTvNullList;

    public AsyncTaskTime(Context context, RecyclerView recyclerView, TextView tvNullList, Product product) {
        mContext = context;
        mMsg = "Atualizando horários...";
        mShowDialog = true;
        mRecyclerView = recyclerView;
        mProduct = product;
        mTvNullList = tvNullList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mTvNullList.setVisibility(View.INVISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String code = params[0];
            String product = params[1];
            String date = params[2];

            String[] aDate = date.split("-");

            String sDay = aDate[0];
            if (Integer.valueOf(sDay) < 10) {
                sDay = "0" + sDay;
            }

            String sMonth = aDate[1];
            if (Integer.valueOf(sMonth) < 10) {
                sMonth = "0" + sMonth;
            }
            String sYear = aDate[2];
            String sDate = sDay + "-" + sMonth + "-" + sYear;

            mJson = connectServerA(EndPoints.TIME.concat(code).concat("/")
                    .concat(product).concat("/").concat(sDate), null, true);
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
        SimpleDateFormat sdfHour = new SimpleDateFormat("H:mm");
        Date now = new Date();
        String currentHour = sdfHour.format(now);

        int hourC = Integer.parseInt(currentHour.split(":")[0]);
        int minC = Integer.parseInt(currentHour.split(":")[1]);

        if (mJson != null && mJson.length() > 0){

            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < mJson.length(); i++) {
                try {
                    JSONObject json = mJson.getJSONObject(i);
                    String [] d = json.getString("horarioInicial").split(":");

                    int hourS = Integer.parseInt(d[0]);
                    int minS = Integer.parseInt(d[1]);

                    if (hourS == hourC) {
                        if (minS >= minC) {
                            String hour = d[0].concat(":").concat(d[1]);
                            list.add(hour);
                        }
                    } else if (hourS >= hourC) {
                        String hour = d[0].concat(":").concat(d[1]);
                        list.add(hour);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            RVAdapterTimes rvAdapter = new RVAdapterTimes(list, mProduct);

            mRecyclerView.setLayoutManager(new GridLayoutManager(mRecyclerView.getContext(), 3));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(rvAdapter);

        } else {
            mTvNullList.setVisibility(View.VISIBLE);
        }
    }
}



//    http://www2.beautyclinic.com.br/clinwebservice2/servidor/horarios/3/143/12-08-2016
