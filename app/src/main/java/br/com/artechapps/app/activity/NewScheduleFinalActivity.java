package br.com.artechapps.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import br.com.artechapps.app.R;
import br.com.artechapps.app.model.Product;

public class NewScheduleFinalActivity extends AppCompatActivity {

    private Product mModel;

    private TextView mTvProcedureName;
    private TextView mTvDoctorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule_final);

        mModel = (Product) getIntent().getSerializableExtra("model");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTvDoctorName = (TextView) findViewById(R.id.tv_doctor_name);
        mTvProcedureName = (TextView) findViewById(R.id.tv_procedure_name);

        mTvProcedureName.setText(mModel.getDescription());

    }

}
