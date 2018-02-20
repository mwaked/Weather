package com.example.crizmamegastore.weather.Utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.crizmamegastore.weather.App.AppController.TAG;


public class LocationUtils {

    public static List<Address> getAddressByLatLong(Context mContext , double latitude , double longitude){
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (Exception e) {
            Log.e("EXCEPTION" , e.getMessage());
        }
        String address = addresses.get(0).getAddressLine(0);
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();

        return addresses ;
    }

    public static List<Double> getLatitudeAndLongitudeFromAddress(Context mContext, String searchedAddress){

        ArrayList<Double> latlng = new ArrayList<>();
        Geocoder coder = new Geocoder(mContext, Locale.getDefault());
        List<Address> address;
        try {
            address = coder.getFromLocationName(searchedAddress,5);
            if (address == null) {
            }
            Address location = address.get(0);
            latlng.add(location.getLatitude());
            latlng.add(location.getLongitude());
            Log.d(TAG, "Latitude : "+ location.getLatitude()+ "Longitude : "+ location.getLongitude());

        }catch(Exception e){
            Log.d(TAG, "ERROR : Address Not Found");
        }
        return latlng;
    }

}
