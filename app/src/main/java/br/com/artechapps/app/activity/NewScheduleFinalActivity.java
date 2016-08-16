package br.com.artechapps.app.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import br.com.artechapps.app.BuildConfig;
import br.com.artechapps.app.R;
import br.com.artechapps.app.model.Product;
import br.com.artechapps.app.task.AsyncTaskTime;
import br.com.artechapps.app.utils.DatePickerFragment;

public class NewScheduleFinalActivity extends AppCompatActivity {

    private Product mModel;

    private TextView mTvProcedureName;
    private TextView mTvDoctorName;
    private TextView mTVProcedureCode;

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
        mTVProcedureCode = (TextView) findViewById(R.id.tv_procedure_code);

        mTvProcedureName.setText(mModel.getDescription());
//        mTVProcedureCode.setText();

        showDatePickerDialog();
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextView date = (TextView) getActivity().findViewById(R.id.label_4);
            date.setText(String.valueOf(day).concat("/") + String.valueOf(month+1).concat("/") + String.valueOf(year));

            String dt = String.valueOf(day).concat("-") + String.valueOf(month+1).concat("-") + String.valueOf(year);
            AsyncTaskTime async = new AsyncTaskTime(getActivity());
            async.execute(String.valueOf(BuildConfig.FILIAL), "111",dt);
        }
    }

}
