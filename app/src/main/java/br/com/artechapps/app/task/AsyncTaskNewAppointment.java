package br.com.artechapps.app.task;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.artechapps.app.BuildConfig;
import br.com.artechapps.app.activity.MainMenuActivity;
import br.com.artechapps.app.activity.NewScheduleFinalActivity;
import br.com.artechapps.app.model.Appointment;
import br.com.artechapps.app.model.User;
import br.com.artechapps.app.utils.EndPoints;
import br.com.artechapps.app.utils.SessionManager;

/**
 * Created by arthurcordova on 7/8/16.
 */
public class AsyncTaskNewAppointment extends AsyncTaskHttp {

    private JSONObject mJson;
    private Appointment mModel;
    private NewScheduleFinalActivity mActivity;

    public AsyncTaskNewAppointment(String msg, Context context, boolean showDialog, Appointment model, NewScheduleFinalActivity activity) {
        mMsg = msg;
        mContext = context;
        mShowDialog = showDialog;
        mModel = model;
        mActivity = activity;

    }

    @Override
    protected String doInBackground(String... params) {
        try {

            Gson gson = new Gson();
            String userJson = gson.toJson(mModel);

            mJson = connectServerO(EndPoints.CONFIRM_APPOINTMENT, userJson,false);
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
        Toast.makeText(mContext,"Procedimento agendando com sucesso..", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(mActivity, MainMenuActivity.class);
        intent.putExtra("origin", NewScheduleFinalActivity.class.getName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mActivity.startActivity(intent);
        mActivity.finish();


    }
}
