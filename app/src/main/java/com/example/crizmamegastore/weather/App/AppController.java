package com.example.crizmamegastore.weather.App;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.crizmamegastore.weather.BuildConfig;
import com.example.crizmamegastore.weather.Model.MyObjectBox;
import com.example.crizmamegastore.weather.R;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private static AppController mInstance;

    private BoxStore boxStore;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/" + Fonts.regularFont)
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        boxStore = MyObjectBox.builder().androidContext(mInstance).build();
        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(boxStore).start(this);
        }


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }

}