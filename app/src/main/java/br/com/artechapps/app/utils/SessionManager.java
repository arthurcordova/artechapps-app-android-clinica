package br.com.artechapps.app.utils;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import br.com.artechapps.app.model.User;

/**
 * Created by arthurcordova on 7/5/16.
 */

public final class SessionManager {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    private final String PREF_NAME = "pref_session";

    private final String KEY_CPF = "login";
    private final String KEY_FILIAL = "code";
    private final String KEY_NAME = "name";
    private final String KEY_ACTIVE = "active";

    public SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void createSessionLogin(User user) {
        editor.putBoolean(KEY_ACTIVE, true);
        editor.putLong(KEY_FILIAL, user.getCodFilial());
        editor.putString(KEY_CPF, user.getCpfcnpj());
        editor.putString(KEY_NAME, user.getName());
        editor.commit();
    }

    public void destroySessionLogin(Class redirect) {
        editor.clear();
        editor.commit();

        redirectToTarget(redirect);
    }


    public void checkLogin(Class targetTrue, Class targetFalse) {
        if (isLoggedIn()) {
            redirectToTarget(targetTrue);
        } else {
            redirectToTarget(targetFalse);
        }


    }

    public void redirectToTarget(Class clazz) {
        Intent i = new Intent(context, clazz);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        ((AppCompatActivity) context).finish();
    }

    private boolean isLoggedIn() {
        return preferences.getBoolean(KEY_ACTIVE, false);
    }

    public User getSessionUser() {
        User user = null;
        if (isLoggedIn()) {

            if (user != null) {
                user.setCpfcnpj(preferences.getString(KEY_CPF, null));
                user.setName(preferences.getString(KEY_NAME, null));
                user.setCodFilial(preferences.getLong(KEY_FILIAL, 1L));
                user.setActive(preferences.getBoolean(KEY_ACTIVE, false));
            }
        }
        return user;
    }
}
