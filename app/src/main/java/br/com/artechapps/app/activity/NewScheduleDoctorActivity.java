package br.com.artechapps.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import br.com.artechapps.app.BuildConfig;
import br.com.artechapps.app.R;
import br.com.artechapps.app.model.Product;
import br.com.artechapps.app.task.AsyncTaskDoctor;

public class NewScheduleDoctorActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule_doctor);

        Product product = (Product) getIntent().getSerializableExtra("model");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Escolha o médico");

        mRecyclerView = (RecyclerView) findViewById(R.id.rvDoctors);

        new AsyncTaskDoctor("Carregando médicos...", this, true, mRecyclerView, product).execute(String.valueOf(BuildConfig.FILIAL));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
