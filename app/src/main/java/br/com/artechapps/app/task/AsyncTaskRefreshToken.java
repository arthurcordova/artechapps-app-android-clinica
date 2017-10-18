package br.com.artechapps.app.task;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.artechapps.app.BuildConfig;
import br.com.artechapps.app.activity.LoginActivity;
import br.com.artechapps.app.activity.NewUserActivity;
import br.com.artechapps.app.model.User;
import br.com.artechapps.app.utils.EndPoints;

/**
 * Created by arthurcordova on 7/12/16.
 */
public class AsyncTaskRefreshToken extends AsyncTaskHttp {

    private JSONObject mJson;

    public AsyncTaskRefreshToken(String msg, AppCompatActivity activity, boolean showDialog) {
        mMsg = msg;
        mContext = activity;
        mShowDialog = showDialog;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String user = params[0];
            String token = params[1];
            String establishment = String.valueOf(BuildConfig.FILIAL);
            String type = "a";

            String url = EndPoints.REFRESH_TOKEN + user + "/" + token + "/" + establishment + "/" + type;

            mJson = connectServerO(url, null, true);
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
            Log.d("SAVE-TOKEN", mJson.toString());
        }
    }
}
