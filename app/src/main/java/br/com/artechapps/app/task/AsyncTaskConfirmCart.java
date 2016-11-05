package br.com.artechapps.app.task;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import br.com.artechapps.app.adapter.RVAdapterDoctor;
import br.com.artechapps.app.model.BudgetConfirm;
import br.com.artechapps.app.model.Doctor;
import br.com.artechapps.app.model.Product;
import br.com.artechapps.app.utils.EndPoints;

/**
 * Created by arthurcordova on 7/16/16.
 */
public class AsyncTaskConfirmCart extends AsyncTaskHttp {

    private BudgetConfirm mBudgetConfirm;

    public AsyncTaskConfirmCart(String msg, Context context, boolean showDialog, BudgetConfirm budgetConfirm) {
        mMsg = msg;
        mContext = context;
        mShowDialog = showDialog;
        mBudgetConfirm = budgetConfirm;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = null;
        try {
            String parameter = new Gson().toJson(mBudgetConfirm);
            result = urlConnection(EndPoints.CONFIRM_CART, parameter, false);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != null){
            new AsyncTaskConfirmCartProducts(mContext,true).execute(result);
        }
    }
}
