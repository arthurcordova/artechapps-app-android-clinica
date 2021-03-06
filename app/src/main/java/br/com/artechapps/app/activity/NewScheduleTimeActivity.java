package br.com.artechapps.app.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.artechapps.app.BuildConfig;
import br.com.artechapps.app.R;
import br.com.artechapps.app.model.Product;
import br.com.artechapps.app.task.AsyncTaskTime;

public class NewScheduleTimeActivity extends AppCompatActivity {

    private Product mProduct;
    private RecyclerView mRecyclerView;
    private ImageView mImgCalendar;
    private TextView mTvNullList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule_time);

        mProduct = (Product) getIntent().getSerializableExtra("model");
        mRecyclerView = (RecyclerView) findViewById(R.id.rvTimes);
        mImgCalendar = (ImageView) findViewById(R.id.action_calendar);
        mTvNullList = (TextView) findViewById(R.id.tv_null_list);
        mTvNullList.setVisibility(View.INVISIBLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Escolha um horário");

        mImgCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        showDatePickerDialog();
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

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable("model", mProduct);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private Product model;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            Bundle args = getArguments();
            model = (Product) args.get("model");

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            super.onCancel(dialog);
            TextView date = (TextView) getActivity().findViewById(R.id.label_date);
            TextView tvNullList = (TextView) getActivity().findViewById(R.id.tv_null_list);
            tvNullList.setVisibility(View.INVISIBLE);
            RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.rvTimes);

            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dateStr = sdf.format(now);

            date.setText(dateStr);
            model.setDate(dateStr.replaceAll("/", "-"));
            AsyncTaskTime async = new AsyncTaskTime(getActivity(), recyclerView, tvNullList, model);
            async.execute(String.valueOf(BuildConfig.FILIAL), String.valueOf(model.getId()),model.getDate());
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextView date = (TextView) getActivity().findViewById(R.id.label_date);
            TextView tvNullList = (TextView) getActivity().findViewById(R.id.tv_null_list);
            tvNullList.setVisibility(View.INVISIBLE);
            RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.rvTimes);
            date.setText(String.valueOf(day).concat("/") + String.valueOf(month+1).concat("/") + String.valueOf(year));
            model.setDate(String.valueOf(day).concat("-") + String.valueOf(month+1).concat("-") + String.valueOf(year));
            AsyncTaskTime async = new AsyncTaskTime(getActivity(), recyclerView, tvNullList, model);
            async.execute(String.valueOf(BuildConfig.FILIAL), String.valueOf(model.getId()),model.getDate());
        }
    }

}
