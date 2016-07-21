package br.com.artechapps.app.task;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import br.com.artechapps.app.database.PersistenceMessage;
import br.com.artechapps.app.utils.EndPoints;

/**
 * Created by arthurcordova on 7/16/16.
 */
public class AsyncTaskMessages extends AsyncTaskHttp {

    private JSONArray mJson;

    public AsyncTaskMessages(String msg, Context context, boolean showDialog) {
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
        if (mJson != null && mJson.length() > 0) {
            PersistenceMessage persistence = null;
            try {
                persistence = new PersistenceMessage(mContext);
                persistence.save(mJson);
            } finally {
                persistence.close();
            }

        }
    }
}
