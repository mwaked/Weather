package com.example.crizmamegastore.weather.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import com.example.crizmamegastore.weather.R;


public final class DialogUtil {

    private DialogUtil() {
    }

    public static ProgressDialog showProgressDialog(Context context, String message, boolean cancelable) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(message);
        dialog.setCancelable(cancelable);
        dialog.show();
        return dialog;
    }


    public static AlertDialog showAlertDialog(Context context, String message,
                                              DialogInterface.OnClickListener positiveClickListener ,DialogInterface.OnClickListener negativeClickListener ) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(message);
        if (negativeClickListener != null) {
            dialogBuilder.setPositiveButton(context.getString(R.string.no),negativeClickListener);
        }
        if(positiveClickListener != null){
            dialogBuilder.setNegativeButton(context.getString(R.string.yes), positiveClickListener);
        }

        AlertDialog dialog = dialogBuilder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }


}
