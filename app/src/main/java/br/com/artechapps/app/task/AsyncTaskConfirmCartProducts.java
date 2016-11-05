package br.com.artechapps.app.task;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import br.com.artechapps.app.activity.MainMenuActivity;
import br.com.artechapps.app.database.PersistenceShop;
import br.com.artechapps.app.model.Budget;
import br.com.artechapps.app.model.BudgetConfirm;
import br.com.artechapps.app.model.BudgetConfirmProduct;
import br.com.artechapps.app.model.Shop;
import br.com.artechapps.app.utils.EndPoints;

/**
 * Created by arthurcordova on 7/16/16.
 */
public class AsyncTaskConfirmCartProducts extends AsyncTaskHttp {

    private String mCodOrcamento;
    private PersistenceShop mPersistence = null;

    public AsyncTaskConfirmCartProducts(Context context, boolean showDialog) {
        mMsg = "Salvando produtos...";
        mContext = context;
        mShowDialog = showDialog;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = null;

        ArrayList<Shop> list = null;
        mCodOrcamento = params[0];
        try {
            mPersistence = new PersistenceShop(mContext);
            list = mPersistence.getRecords();

            for (Shop model: list) {
                BudgetConfirmProduct product = new BudgetConfirmProduct(model.getProduct().getId(), model.getProduct().getValue());
                String parameter = new Gson().toJson(product);
                result = urlConnection(EndPoints.CONFIRM_CART_PRODUCTS.concat(mCodOrcamento), parameter, false);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mPersistence.close();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != null){
            mPersistence = new PersistenceShop(mContext);
            mPersistence.deleteAll();
            mPersistence.close();

            Intent it = new Intent(mContext, MainMenuActivity.class);
            it.putExtra("origin", AsyncTaskConfirmCartProducts.class.getName());
            mContext.startActivity(it);
        }
    }
}
