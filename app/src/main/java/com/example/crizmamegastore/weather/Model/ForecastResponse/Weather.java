package com.example.crizmamegastore.weather.Model.ForecastResponse;

public class Weather {

    public String weather_icon;
    public String humidity;
    public String rain_descr;
    public String time;

    public Weather(String weather_icon, String humidity, String rain_descr, String time) {
        this.weather_icon = weather_icon;
        this.humidity = humidity;
        this.rain_descr = rain_descr;
        this.time = time;
    }
}