package br.com.artechapps.app.task;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.artechapps.app.activity.MainMenuActivity;
import br.com.artechapps.app.activity.NewScheduleFinalActivity;
import br.com.artechapps.app.database.PersistenceSchedule;
import br.com.artechapps.app.model.Appointment;
import br.com.artechapps.app.utils.EndPoints;

/**
 * Created by arthurcordova on 7/8/16.
 */
public class AsyncTaskCancelAppointment extends AsyncTaskHttp {

    private JSONObject mJson;
    private NewScheduleFinalActivity mActivity;
    private String idSchedule;

    public AsyncTaskCancelAppointment(String msg, Context context, boolean showDialog, NewScheduleFinalActivity activity) {
        mMsg = msg;
        mContext = context;
        mShowDialog = showDialog;
        mActivity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            idSchedule = params[1];
            mJson = connectServerO(EndPoints.CANCEL_APPOINTMENT, params[0],false);
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
        Toast.makeText(mContext,"Agendamento cancelado com sucesso.", Toast.LENGTH_LONG).show();
        PersistenceSchedule pers = new PersistenceSchedule(mContext);
        pers.updateStatusCancel(idSchedule);
        Intent intent = new Intent(mActivity, MainMenuActivity.class);
        intent.putExtra("origin", NewScheduleFinalActivity.class.getName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mActivity.startActivity(intent);
        mActivity.finish();
    }
}
