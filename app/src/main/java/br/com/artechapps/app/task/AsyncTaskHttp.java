package br.com.artechapps.app.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
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

public class AsyncTaskHttp extends AsyncTask<String, String, String> {

    private static final int CONN_TIME_OUT = 10000;
    private static final String METHOD_POST = "POST";
    private static final String METHOD_GET = "GET";
    private static final boolean CONN_INPUT = true;

    protected String mMsg;
    protected Context mContext;
    protected boolean mShowDialog;
    protected ProgressDialog mDialog;
    protected int mResponseCode;

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

    public JSONObject connectServerO(String urlStr, String param, boolean isGET) throws JSONException, IOException {
        return new JSONObject(new JSONTokener(urlConnection(urlStr, param, isGET)));
    }

    public JSONArray connectServerA(String urlStr, String param, boolean isGET) throws JSONException, IOException {
        return new JSONArray(new JSONTokener(urlConnection(urlStr, param, isGET)));
    }

    public String urlConnection(String urlStr, String parameters, boolean isGET) throws JSONException, IOException {
        StringBuilder result = new StringBuilder();

        URL url = new URL(urlStr);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setReadTimeout(CONN_TIME_OUT);
        urlConnection.setConnectTimeout(CONN_TIME_OUT);
        urlConnection.setDoInput(CONN_INPUT);
        urlConnection.setRequestProperty("Content-Type", "application/json");
        if (isGET){
            urlConnection.setRequestMethod(METHOD_GET);
        } else {
            urlConnection.setRequestMethod(METHOD_POST);
        }

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
        mResponseCode = urlConnection.getResponseCode();
        urlConnection.disconnect();

        urlConnection.getResponseCode();

        return result.toString();
    }

    @Override
    protected String doInBackground(String... params) {
        return null;
    }
}
