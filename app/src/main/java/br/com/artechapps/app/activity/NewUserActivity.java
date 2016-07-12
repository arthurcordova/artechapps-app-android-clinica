package br.com.artechapps.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.artechapps.app.R;
import br.com.artechapps.app.task.AsyncTaskNewUser;

public class NewUserActivity extends AppCompatActivity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mButton = (Button) findViewById(R.id.new_user_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTaskNewUser("Criando novo usuário...",NewUserActivity.this, true).execute();
            }
        });


    }


}
