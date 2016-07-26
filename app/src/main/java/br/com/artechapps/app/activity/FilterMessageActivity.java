package br.com.artechapps.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;

import br.com.artechapps.app.R;
import br.com.artechapps.app.utils.DatePickerFragment;

public class FilterMessageActivity extends AppCompatActivity {

    private ImageView mWidgetStartDate;
    private ImageView mWidgetEndDate;

    public TextView mTvStartDate;
    public TextView mTvEndDate;
    public Switch mSwRead;
    public Switch mSwNotRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_message);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Filtrar mensagens");

        mWidgetStartDate = (ImageView) findViewById(R.id.widget_start_date);
        mWidgetEndDate = (ImageView) findViewById(R.id.widget_end_date);
        mTvStartDate = (TextView) findViewById(R.id.tv_start_date);
        mTvEndDate = (TextView) findViewById(R.id.tv_end_date);
        mSwRead = (Switch) findViewById(R.id.sw_read);
        mSwNotRead = (Switch) findViewById(R.id.sw_not_read);

        setCurrentDate(mTvStartDate);
        setCurrentDate(mTvEndDate);

        mWidgetStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(mTvStartDate, 0);
            }
        });

        mWidgetEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(mTvEndDate, 1);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_message_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_ok:

                Intent result = new Intent();
                result.putExtra("start_date", mTvStartDate.getText().toString());
                result.putExtra("end_date", mTvEndDate.getText().toString());
                result.putExtra("is_read", mSwRead.isChecked());
                result.putExtra("is_not_read", mSwNotRead.isChecked());
                setResult(RESULT_OK, result);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDatePickerDialog(View v, int position) {
        Bundle args = new Bundle();
        int year = 0, month = 0, day = 0;
        String[] array;
        if (position == 0) {
            array = mTvStartDate.getText().toString().split("/");
            day = Integer.parseInt(array[0]);
            month = Integer.parseInt(array[1]);
            year = Integer.parseInt(array[2]);
        } else {
            array = mTvEndDate.getText().toString().split("/");
            day = Integer.parseInt(array[0]);
            month = Integer.parseInt(array[1]);
            year = Integer.parseInt(array[2]);
        }

        args.putInt("position", position);
        args.putInt("year", year);
        args.putInt("month", month);
        args.putInt("day", day);
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "");
    }

    private void setCurrentDate(TextView tv) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        tv.setText(String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year));

    }

}
