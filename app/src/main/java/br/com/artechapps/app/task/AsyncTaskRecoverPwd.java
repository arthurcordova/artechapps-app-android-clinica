package br.com.artechapps.app.task;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.artechapps.app.R;
import br.com.artechapps.app.activity.ResetPasswordActivity;
import br.com.artechapps.app.utils.EndPoints;

/**
 * Created by arthurcordova on 10/10/2017.
 */
public class AsyncTaskRecoverPwd extends AsyncTaskHttp {

    private JSONObject mJson;
    private ResetPasswordActivity mActivity;

    public AsyncTaskRecoverPwd(String msg, ResetPasswordActivity activity, boolean showDialog) {
        mMsg = msg;
        mActivity = activity;
        mContext = activity;
        mShowDialog = showDialog;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String email = params[0];
            mJson = connectServerO(EndPoints.RECOVER_PWD.concat(email), null, true);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String results) {
        super.onPostExecute(results);

        if (mJson != null) {
            try {
                new AlertDialog.Builder(mContext, R.style.BeautyDialog)
                        .setTitle("Mensagem")
                        .setMessage(mJson.getString("mensagem"))
                        .setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            } catch (JSONException e) {
                Log.d("RECOVER", "ERRO");
            }
        }
    }
}
