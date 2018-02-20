package com.example.crizmamegastore.weather.Utils;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.design.BuildConfig;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.example.crizmamegastore.weather.App.AppController;
import com.example.crizmamegastore.weather.R;
import com.example.crizmamegastore.weather.UI.views.Toaster;
import com.google.gson.Gson;
import java.net.BindException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.text.format.DateUtils.MINUTE_IN_MILLIS;


public class CommonUtil {

    public static boolean isALog = true;

    public static void onPrintLog(Object o) {
        if (isALog) {
            Log.e("Response >>>>", new Gson().toJson(o));
        }
    }

    public static void PrintLogE(String print) {
        if (BuildConfig.DEBUG) {
            Log.e(AppController.TAG, print);
        }
    }


    public static String makeURL(double sourceLat, double sourceLog, double destLat, double destLog) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("http://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");
        urlString.append(Double.toString(sourceLat));
        urlString.append(",");
        urlString.append(Double.toString(sourceLog));
        urlString.append("&destination=");
        urlString.append(Double.toString(destLat));
        urlString.append(",");
        urlString.append(Double.toString(destLog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        return urlString.toString();
    }

    public static String getLanguage() {
        String language = Locale.getDefault().getDisplayLanguage();
        return language;


    }

    public static void requestFocus(View view, Window window) {
        if (view.requestFocus()) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    public static int handleException(Context context, Throwable t) {
        if (t instanceof SocketTimeoutException) {
            makeToast(context, R.string.time_out_error);
            return R.string.time_out_error;
        } else if (t instanceof UnknownHostException) {
            makeToast(context, R.string.connection_error);
            return R.string.connection_error;
        } else if (t instanceof ConnectException) {
            makeToast(context, R.string.connection_error);
            return R.string.connection_error;
        } else if (t instanceof NoRouteToHostException) {
            makeToast(context, R.string.connection_error);
            return R.string.connection_error;
        } else if (t instanceof PortUnreachableException) {
            makeToast(context, R.string.connection_error);
            return R.string.connection_error;
        } else if (t instanceof UnknownServiceException) {
            makeToast(context, R.string.connection_error);
            return R.string.connection_error;
        } else if (t instanceof BindException) {
            makeToast(context, R.string.connection_error);
            return R.string.connection_error;
        } else {
            makeToast(context, t.getLocalizedMessage());
            return R.string.connection_error;
        }
    }

    public static void makeToast(Context context, int msgId) {
        Toaster toaster = new Toaster(context);
        toaster.makeToast(context.getString(msgId));

    }

    public static void makeToast(Context context, String msg) {
        Toaster toaster = new Toaster(context);
        toaster.makeToast(msg);

    }


    public static void setConfig(String language, Context context) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());

    }


    public static String getFormattedTime(String date) {
        Date parse = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            parse = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parse);
        long updated = calendar.getTimeInMillis();
        return DateUtils.getRelativeTimeSpanString(updated, System.currentTimeMillis(), MINUTE_IN_MILLIS).toString();
    }

    public void showParElevation(boolean showHide, AppBarLayout app_bar, float elevation) {
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            if (showHide) {
                app_bar.setElevation(elevation);
            } else {
                app_bar.setElevation((float) 0.0);
            }
        }
    }

    public static void makeSnake(View v , int message){
        Snackbar snackbar = Snackbar
                .make(v, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void PrintResponseLog(Object o) {
        if (BuildConfig.DEBUG) {
            Log.e("Tagd", "response :" + new Gson().toJson(o));
        }
    }


}
