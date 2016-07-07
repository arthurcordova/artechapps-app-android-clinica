package br.com.artechapps.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.artechapps.app.R;

public class NewUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
