package com.example.crizmamegastore.weather.Utils;

import android.support.design.BuildConfig;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import com.example.crizmamegastore.weather.App.AppController;

public class CommonUtil {

    public static void PrintLogE(String print) {
        if (BuildConfig.DEBUG) {
            Log.e(AppController.TAG, print);
        }
    }

    public static void makeSnake(View v , int message){
        Snackbar snackbar = Snackbar
                .make(v, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }


}
