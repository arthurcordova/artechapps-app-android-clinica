package br.com.artechapps.app.task;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.artechapps.app.BuildConfig;
import br.com.artechapps.app.model.User;
import br.com.artechapps.app.utils.EndPoints;

/**
 * Created by arthurcordova on 7/12/16.
 */
public class AsyncTaskNewUser extends AsyncTaskHttp {

    private JSONObject mJson;

    public AsyncTaskNewUser(String msg, Context context, boolean showDialog) {
        mMsg = msg;
        mContext = context;
        mShowDialog = showDialog;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            User user = new User();
            user.setCodFilial(BuildConfig.FILIAL);
            user.setSenha("1");
            user.setCpfcnpj("06048472978");
            user.setName("Arthur Cordova Stapassoli");
            user.setOpcad(1L);
            user.setTipopessoa("F");

            Gson gson = new Gson();
            String userJson = gson.toJson(user);

            mJson = connectToServer(EndPoints.NEW_USER, userJson);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }






}
