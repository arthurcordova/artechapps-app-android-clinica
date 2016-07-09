package br.com.artechapps.app.task;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import br.com.artechapps.app.model.User;
import br.com.artechapps.app.utils.EndPoints;

/**
 * Created by arthurcordova on 7/8/16.
 */
public class AsyncTaskLogin extends AsyncTaskHttp {

    private JSONObject mJson;

    public AsyncTaskLogin(String msg, Context context, boolean showDialod) {
        mMsg = msg;
        mContext = context;
        mShowDialog = showDialod;

    }

    @Override
    protected String doInBackground(String... params) {
        try {
            User user = new User();
            user.setCodFilial(1L);
            user.setSenha("1");
            user.setCpfcnpj("11111111111");

            Gson gson = new Gson();
            String userJson = gson.toJson(user);

            mJson = connectToServer(EndPoints.LOGIN, userJson);
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
        if (mJson != null){
            try {
                mJson.getString("codCliente");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
