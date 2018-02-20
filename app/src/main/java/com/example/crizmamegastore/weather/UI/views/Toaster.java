package com.example.crizmamegastore.weather.UI.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crizmamegastore.weather.R;

public class Toaster extends Toast {

    Context mContext;

    public Toaster(Context mContext) {
        super(mContext);
        this.mContext = mContext;

    }

    public void makeToast(String message) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast_custom_layout, null);
        TextView tv_toast = (TextView) view.findViewById(R.id.tv_toast_message);
        Toast toast = new Toast(mContext);
        tv_toast.setText(message);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}