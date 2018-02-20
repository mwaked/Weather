package com.example.crizmamegastore.weather.GPS;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;

    boolean isGPSEnabled = false;

    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;


    Location location;
    double latitude;
    double longitude;


    GPSTrakerListner gpsTrakerListner;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 100; // 100 meters

    private static final long MIN_TIME_BW_UPDATES = 1000; // 1 minute

    protected LocationManager locationManager;

    public GPSTracker(Context context, GPSTrakerListner gpsTrakerListner) {
        this.mContext = context;
        this.gpsTrakerListner = gpsTrakerListner;
    }

    public GPSTracker(Context context) {
        this.mContext = context;
    }

    @SuppressLint("MissingPermission")
    public Location getLocation() {
        try {

            locationManager = (LocationManager) mContext
                    .getSystemService(Context.LOCATION_SERVICE);

            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            if (!isGPSEnabled && !isNetworkEnabled) {
                Log.e("GPS", "No GPS And Network Enabled");
            } else {
                this.canGetLocation = true;

                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        Log.e("GPS", "GPS Enabled");
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        updateGPSCoordinates(location);
                    }
                }
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            Log.e("GPS", "Network");
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            updateGPSCoordinates(location);
                        }
                    }

                }

                if (getLatitude() == 0.0 && getLongitude() == 0.0) {
                    Log.e("latLng", "lat:" + getLatitude() + " lng:" + getLongitude());
                    gpsTrakerListner.onStartTracker();
                }
            }
        } catch (Exception e) {
            Log.e("GPS",
                    "Impossible to connect to LocationManager", e);
        }
        return location;
    }

    public void updateGPSCoordinates(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Log.e("LatLng:", "Lat:" + latitude + "  Lng:" + longitude);
            gpsTrakerListner.onTrackerSuccess(latitude, longitude);
        } else {
            Log.e("GPS", "Location null");
        }
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void onLocationChanged(Location location) {
        Log.e("GPS", "Location :" + location.toString());
        updateGPSCoordinates(location);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.e("GPS", "StatusChanged" + provider.toString());

    }


    public void onProviderDisabled(String provider) {

    }

    public void onProviderEnabled(String provider) {
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }


}
