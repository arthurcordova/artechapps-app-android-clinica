package br.com.artechapps.app.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.artechapps.app.R;

/**
 * Created by arthurcordova on 7/9/16.
 */
public class AsyncTaskHttp extends AsyncTask<String, String, String> {

    private static final int CONN_TIME_OUT = 10000;
    private static final String METHOD_POST = "POST";
    private static final String METHOD_GET = "GET";
    private static final boolean CONN_INPUT = true;

    protected String mMsg;
    protected Context mContext;
    protected boolean mShowDialog;
    protected ProgressDialog mDialog;

    @Override
    protected void onPreExecute() {
        if (mShowDialog){
            mDialog = new ProgressDialog(mContext);
            mDialog.setTitle(R.string.title_dialog_wait);
            mDialog.setMessage(mMsg);
            mDialog.setCancelable(false);
            mDialog.show();
        }
    }

    @Override
    protected void onPostExecute(String results) {
        super.onPostExecute(results);
        if ((mShowDialog) && (mDialog != null) && ( mDialog.isShowing())) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public JSONObject connectToServer(String urlStr, String param) throws JSONException, IOException {
        return new JSONObject(new JSONTokener(urlConnection(urlStr, param)));
    }

    public String urlConnection(String urlStr, String parameters) throws JSONException, IOException {
        StringBuilder result = new StringBuilder();

        URL url = new URL(urlStr);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setReadTimeout(CONN_TIME_OUT);
        urlConnection.setConnectTimeout(CONN_TIME_OUT);
        urlConnection.setRequestMethod(METHOD_POST);
        urlConnection.setDoInput(CONN_INPUT);
        urlConnection.setRequestProperty("Content-Type", "application/json");

        if (parameters != null){
            OutputStream output = new BufferedOutputStream(urlConnection.getOutputStream());
            output.write(parameters.getBytes());
            output.flush();
            output.close();
        }
        urlConnection.connect();
        BufferedReader bf;
        bf = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
        String inputLine;
        while ((inputLine = bf.readLine()) != null) {
            result.append(inputLine);
        }
        urlConnection.disconnect();
        return result.toString();
    }

    @Override
    protected String doInBackground(String... params) {
        return null;
    }
}
