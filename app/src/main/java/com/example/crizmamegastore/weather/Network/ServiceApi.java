package com.example.crizmamegastore.weather.Network;


import com.example.crizmamegastore.weather.Model.ForecastResponse.WeatherData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceApi {

    @GET(Urls.FORECAST)
    Call<WeatherData> getWeatherInfo(
            @Query("lat") String latitude,
            @Query("lon") String longitude,
            @Query("cnt") String cnt,
            @Query("appid") String appid
    );

}
