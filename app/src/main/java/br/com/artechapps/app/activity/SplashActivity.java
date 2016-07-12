package br.com.artechapps.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.artechapps.app.R;
import br.com.artechapps.app.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onResume() {
        super.onResume();

        SessionManager sm = new SessionManager(SplashActivity.this);
        sm.checkLogin(MainMenuActivity.class, LoginActivity.class);
    }

}
