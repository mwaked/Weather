package com.example.crizmamegastore.weather.GPS;


public interface GPSTrakerListner {
    void onTrackerSuccess(Double lat, Double log);

    void onStartTracker();
}
