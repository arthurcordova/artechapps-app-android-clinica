package br.com.artechapps.app.task;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.artechapps.app.BuildConfig;
import br.com.artechapps.app.activity.NewUserActivity;
import br.com.artechapps.app.model.User;
import br.com.artechapps.app.utils.EndPoints;

/**
 * Created by arthurcordova on 7/12/16.
 */
public class AsyncTaskNewUser extends AsyncTaskHttp {

    private JSONObject mJson;
    private NewUserActivity mActivity;

    public AsyncTaskNewUser(String msg, NewUserActivity activity, boolean showDialog) {
        mMsg = msg;
        mActivity = activity;
        mContext = activity;
        mShowDialog = showDialog;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String name = params[0];
            String cpf = params[1].replace(".","").replace("-","");;
            String pwd = params[2];
            String email = params[3];

            User user = new User();
            user.setCodFilial(BuildConfig.FILIAL);
            user.setSenha(pwd);
            user.setCpfcnpj(cpf);
            user.setName(name);
            user.setOpcad(1L);
            user.setTipopessoa("F");
            user.setEmail(email);

            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String userJson = gson.toJson(user);

            mJson = connectServerO(EndPoints.NEW_USER, userJson,false);
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
        Toast.makeText(mContext,"Usuário criado com sucesso.",Toast.LENGTH_LONG).show();
        mActivity.finish();
//        if (mJson == null){
//            Toast.makeText(mContext,"Erro ao criar novo usuário. Por favor entre em contato conosco.",Toast.LENGTH_LONG).show();
//        }

    }
}
