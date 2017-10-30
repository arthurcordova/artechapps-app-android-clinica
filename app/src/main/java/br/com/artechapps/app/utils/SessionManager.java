package br.com.artechapps.app.utils;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import br.com.artechapps.app.database.PersistenceBudget;
import br.com.artechapps.app.database.PersistenceMessage;
import br.com.artechapps.app.database.PersistenceSchedule;
import br.com.artechapps.app.database.PersistenceShop;
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
    private final String KEY_CODE = "user_code";
    private final String KEY_ACTIVE = "active";
    private final String KEY_COUNT_MESSAGES = "messages";
    private final String KEY_COUNT_APPOINTMENTS = "appointments";
    private final String KEY_COUNT_SCORE = "score";
    private final String KEY_COUNT_DISCOUNT = "discount";

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
        editor.putLong(KEY_CODE, user.getCode());
        editor.putInt(KEY_COUNT_APPOINTMENTS, user.getAppointments());
        editor.putInt(KEY_COUNT_MESSAGES, user.getMessages());
        editor.putInt(KEY_COUNT_SCORE, user.getScore());
        editor.putInt(KEY_COUNT_DISCOUNT, user.getDiscount());
        editor.commit();
    }

    public void destroySessionLogin(Class redirect) {
        editor.clear();
        editor.commit();

        new PersistenceBudget(context).remove();
        new PersistenceMessage(context).remove();
        new PersistenceSchedule(context).remove();
        new PersistenceShop(context).remove();

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
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        context.startActivity(i);
        ((AppCompatActivity) context).finish();
    }

    private boolean isLoggedIn() {
        return preferences.getBoolean(KEY_ACTIVE, false);
    }

    public User getSessionUser() {
        if (isLoggedIn()) {
            User user = new User();
            user.setCpfcnpj(preferences.getString(KEY_CPF, null));
            user.setName(preferences.getString(KEY_NAME, null));
            user.setCodFilial(preferences.getLong(KEY_FILIAL, 1L));
            user.setActive(preferences.getBoolean(KEY_ACTIVE, false));
            user.setCode(preferences.getLong(KEY_CODE, 0L));
            user.setAppointments(preferences.getInt(KEY_COUNT_APPOINTMENTS, 0));
            user.setMessages(preferences.getInt(KEY_COUNT_MESSAGES, 0));
            user.setScore(preferences.getInt(KEY_COUNT_SCORE, 0));
            user.setDiscount(preferences.getInt(KEY_COUNT_DISCOUNT, 0));
            return user;
        }
        return null;
    }
}
