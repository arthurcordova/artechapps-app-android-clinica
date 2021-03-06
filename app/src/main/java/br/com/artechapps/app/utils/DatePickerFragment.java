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

        mPosition = getArguments().getInt("position");
        int year = getArguments().getInt("year");
        int month = getArguments().getInt("month");
        int day = getArguments().getInt("day");

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        if (mPosition == 0){
            ((FilterMessageActivity) getActivity()).mTvStartDate.setText(String.valueOf(day)+
                    "/"+
                    String.valueOf(month + 1) +
                    "/"+
                    String.valueOf(year));
        } else {
            ((FilterMessageActivity) getActivity()).mTvEndDate.setText(String.valueOf(day)+
                    "/"+
                    String.valueOf(month + 1) +
                    "/"+
                    String.valueOf(year));
        }

    }
}