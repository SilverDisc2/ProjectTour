package com.example.project;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerClass {
Context context;
    private long  setMiliSec;
    private String dateSet,value;
    private SimpleDateFormat dateAndTimeSDF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private SimpleDateFormat dateSDF = new SimpleDateFormat("dd MMM yyyy");

    public String setUpDatePicker(final Button button) {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override

            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;
            value = year + "/" + month + "/" + dayOfMonth + " 00:00:00";


              Date date = null;

                try {
                    date = dateAndTimeSDF.parse(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                setMiliSec = date.getTime();
                Log.d("Date in MS", "" + setMiliSec);
                //miliSec1=setMiliSec;
               // button.setText(dateSDF.format(date));
            }
        };
//miliSec=value;
        dateSet=value;
       // miliSec = setMiliSec;
        //button.setText(String.valueOf(miliSec));
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context, dateSetListener, year, month, day);
        datePickerDialog.show();
        return dateSet;
    }
}
