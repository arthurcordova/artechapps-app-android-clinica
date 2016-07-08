package br.com.artechapps.app.task;

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
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.artechapps.app.model.User;
import br.com.artechapps.app.utils.EndPoints;

/**
 * Created by arthurcordova on 7/8/16.
 */
public class AsyncTaskLogin extends AsyncTask<String, String, String> {
    private JSONObject mJson;

    @Override
    protected String doInBackground(String... params) {
        try {
            mJson = connectToServer(EndPoints.LOGIN,null);
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
        if (mJson != null){

        }
    }

    private static final int CONN_TIME_OUT = 30000;
    private static final String CONN_METHOD = "POST";
    private static final String CONN_CONTEXT = "TLS";
    private static final boolean CONN_INPUT = true;

    public JSONObject connectToServer(String urlStr, String param) throws JSONException, IOException {
        return new JSONObject(new JSONTokener(urlConnection(urlStr, param)));
    }

    public String urlConnection(String urlStr, String param) throws JSONException, IOException {
        StringBuilder result = new StringBuilder();

        User user = new User();
        user.setCodFilial(1);
        user.setSenha("1");
        user.setCpfcnpj("11111111111");

        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        URL url = new URL(urlStr);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setReadTimeout(CONN_TIME_OUT);
        urlConnection.setConnectTimeout(CONN_TIME_OUT);
        urlConnection.setRequestMethod(CONN_METHOD);
        urlConnection.setDoInput(CONN_INPUT);
        urlConnection.setRequestProperty("Content-Type", "application/json");

        if (userJson != null){
            OutputStream output = new BufferedOutputStream(urlConnection.getOutputStream());
            output.write(userJson.getBytes());
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
}
