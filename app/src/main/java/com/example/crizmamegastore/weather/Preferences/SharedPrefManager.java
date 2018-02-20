package com.example.crizmamegastore.weather.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.crizmamegastore.weather.App.Constant;
import com.example.crizmamegastore.weather.Model.WeatherData;
import com.google.gson.Gson;

public class SharedPrefManager {

    Context mContext;

    SharedPreferences mSharedPreferences;

    SharedPreferences.Editor mEditor;

    public SharedPrefManager(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = mContext.getSharedPreferences(Constant.SharedPrefKey.SHARED_PREF_NAME, mContext.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public WeatherData getWeatherData() {
        Gson gson = new Gson();
        return gson.fromJson(mSharedPreferences.getString(Constant.SharedPrefKey.WEATHER, null), WeatherData.class);
    }

    public void setWeatherData(WeatherData weatherData) {
        mEditor.putString(Constant.SharedPrefKey.WEATHER, new Gson().toJson(weatherData));
        mEditor.apply();
    }

    public void clearPref() {
        mEditor.clear();
        mEditor.apply();
    }
}
