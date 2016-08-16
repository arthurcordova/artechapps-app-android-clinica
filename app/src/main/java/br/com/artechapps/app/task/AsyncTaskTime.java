package br.com.artechapps.app.task;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import br.com.artechapps.app.utils.EndPoints;

/**
 * Created by arthurcordova on 8/13/16.
 */
public class AsyncTaskTime extends AsyncTaskHttp {

    private JSONArray mJson;

    public AsyncTaskTime(Context context) {
        mContext = context;
        mShowDialog = false;

    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String code = params[0];
            String product = params[1];
            String date = params[2];
            mJson = connectServerA(EndPoints.TIME.concat(code).concat("/")
                    .concat(product).concat("/").concat(date), null, true);
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

        }
    }
}



//    http://www2.beautyclinic.com.br/clinwebservice2/servidor/horarios/3/143/12-08-2016
