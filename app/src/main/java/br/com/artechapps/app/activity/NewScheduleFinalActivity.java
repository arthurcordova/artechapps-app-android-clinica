package br.com.artechapps.app.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

import br.com.artechapps.app.BuildConfig;
import br.com.artechapps.app.R;
import br.com.artechapps.app.database.PersistenceMessage;
import br.com.artechapps.app.model.Product;
import br.com.artechapps.app.task.AsyncTaskMessagesDelete;
import br.com.artechapps.app.task.AsyncTaskTime;
import br.com.artechapps.app.utils.DatePickerFragment;

public class NewScheduleFinalActivity extends AppCompatActivity {

    private Product mModel;

    private TextView mTvProcedureName;
    private TextView mTvDoctorName;
    private TextView mTvDateTime;
    private RelativeLayout mLineDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule_final);

        mModel = (Product) getIntent().getSerializableExtra("model");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detalhes do agendamento");

        mTvDoctorName = (TextView) findViewById(R.id.tv_doctor_name);
        mTvProcedureName = (TextView) findViewById(R.id.tv_procedure_name);
        mTvDateTime = (TextView) findViewById(R.id.tv_date_time);
        mTvDoctorName = (TextView) findViewById(R.id.tv_doctor_name);
        mLineDoctor = (RelativeLayout) findViewById(R.id.line_2);

        mTvProcedureName.setText(mModel.getDescription());

        if(mModel.getDoctor() != null){
            mLineDoctor.setVisibility(View.VISIBLE);
            mTvDoctorName.setText(mModel.getDoctor().getName());
        }
        mTvDateTime.setText(mModel.getDate() + "  " + mModel.getTime());

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
