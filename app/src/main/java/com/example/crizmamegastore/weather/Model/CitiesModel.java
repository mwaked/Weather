package com.example.crizmamegastore.weather.Model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class CitiesModel {

    @Id
    private long id;

    private String CityName;

    private String Lat;

    private String Long;

    public CitiesModel(int ID, String CityName, String Lat, String Long) {
        id = ID;
        this.CityName = CityName;
        this.Lat = Lat;
        this.Long = Long;
    }

    public CitiesModel(String CityName, String Lat, String Long) {
        this.CityName = CityName;
        this.Lat = Lat;
        this.Long = Long;
    }


    public CitiesModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }
}
