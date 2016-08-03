package br.com.artechapps.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.artechapps.app.BuildConfig;
import br.com.artechapps.app.R;
import br.com.artechapps.app.task.AsyncTaskDoctor;

public class NewScheduleDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule_doctor);

        new AsyncTaskDoctor("Carregando médicos...", this, true).execute(String.valueOf(BuildConfig.FILIAL));

    }
}
