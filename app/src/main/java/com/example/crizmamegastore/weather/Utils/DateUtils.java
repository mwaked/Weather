package com.example.crizmamegastore.weather.Utils;

import android.text.format.DateFormat;
import android.util.Log;
import java.util.Calendar;

public class DateUtils {

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        Log.e("Time: ", System.currentTimeMillis() + "");
        String date = DateFormat.format("dd MMMM yyyy , HH:mm ", cal).toString();
        return date;
    }


}
