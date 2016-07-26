package br.com.artechapps.app.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import br.com.artechapps.app.activity.FilterMessageActivity;

/**
 * Created by arthurcordova on 7/25/16.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private int mPosition;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
//        final Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);

        mPosition = getArguments().getInt("position");
        int year = getArguments().getInt("year");
        int month = getArguments().getInt("month");
        int day = getArguments().getInt("day");

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        if (mPosition == 0){
            ((FilterMessageActivity) getActivity()).mTvStartDate.setText(String.valueOf(day)+
                    "/"+
                    String.valueOf(month) +
                    "/"+
                    String.valueOf(year));
        } else {
            ((FilterMessageActivity) getActivity()).mTvEndDate.setText(String.valueOf(day)+
                    "/"+
                    String.valueOf(month) +
                    "/"+
                    String.valueOf(year));
        }

    }
}