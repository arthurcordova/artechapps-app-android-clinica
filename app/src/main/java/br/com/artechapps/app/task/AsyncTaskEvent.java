package br.com.artechapps.app.task;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import br.com.artechapps.app.utils.EndPoints;

/**
 * Created by arthurcordova on 7/16/16.
 */
public class AsyncTaskEvent extends AsyncTaskHttp {

    private JSONArray mJson;

    public AsyncTaskEvent(String msg, Context context, boolean showDialog) {
        mMsg = msg;
        mContext = context;
        mShowDialog = showDialog;

    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String code = params[0];
            mJson = connectServerA(EndPoints.MESSAGES.concat(code), null, true);
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
//            try {
//                User user = new User();
//                user.setName(mJson.getString("nome"));
//                user.setLastName(mJson.getString("ultimoNome"));
//                user.setCpfcnpj(mJson.getString("cpfcnpj"));
//                user.setActive(mJson.getString("situacao").equals("A"));
//                user.setCodFilial(BuildConfig.FILIAL);
//                user.setCode(mJson.getLong("codcliente"));
//
//                if (user.isActive()){
//                    SessionManager sm = new SessionManager(mContext);
//                    sm.createSessionLogin(user);
//                    sm.redirectToTarget(MainMenuActivity.class);
//                } else {
//                    //user inactive
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }

    }


}
