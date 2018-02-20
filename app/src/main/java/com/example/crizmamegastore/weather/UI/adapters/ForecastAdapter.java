package com.example.crizmamegastore.weather.UI.adapters;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.crizmamegastore.weather.Base.ParentRecyclerAdapter;
import com.example.crizmamegastore.weather.Base.ParentRecyclerViewHolder;
import com.example.crizmamegastore.weather.Model.ForecastResponse.Weather;
import com.example.crizmamegastore.weather.R;
import com.example.crizmamegastore.weather.Utils.DateUtils;

import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class ForecastAdapter extends ParentRecyclerAdapter<Weather> {

    Typeface weather_font;
    Weather weather ;

    public ForecastAdapter(final Context mContext, final List<Weather> data , Typeface font) {
        super(mContext, data);
        this.weather_font = font ;
    }

    @Override
    public ParentRecyclerViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycle_forecast_row, parent, false);
        weatherViewHolder holder = new weatherViewHolder(itemView);
        holder.setClickableRootView(holder.layout_content);
        holder.setOnItemClickListener(itemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ParentRecyclerViewHolder holder, final int position) {
        final weatherViewHolder weatherViewHolder = (weatherViewHolder) holder;
        weather = data.get(position);

        if (position % 2 == 1) {
            weatherViewHolder.layout_content.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        } else {
            weatherViewHolder.layout_content.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));
        }

        weatherViewHolder.weather_icon.setTypeface(weather_font);

        switch (weather.weather_icon){
            case "01d":
                weatherViewHolder.weather_icon.setText(R.string.wi_day_sunny);
                break;
            case "02d":
                weatherViewHolder.weather_icon.setText(R.string.wi_cloudy_gusts);
                break;
            case "03d":
                weatherViewHolder.weather_icon.setText(R.string.wi_cloud_down);
                break;
            case "10d":
                weatherViewHolder.weather_icon.setText(R.string.wi_day_rain_mix);
                break;
            case "11d":
                weatherViewHolder.weather_icon.setText(R.string.wi_day_thunderstorm);
                break;
            case "13d":
                weatherViewHolder.weather_icon.setText(R.string.wi_day_snow);
                break;
            case "01n":
                weatherViewHolder.weather_icon.setText(R.string.wi_night_clear);
                break;
            case "04d":
                weatherViewHolder.weather_icon.setText(R.string.wi_cloudy);
                break;
            case "04n":
                weatherViewHolder.weather_icon.setText(R.string.wi_night_cloudy);
                break;
            case "02n":
                weatherViewHolder.weather_icon.setText(R.string.wi_night_cloudy);
                break;
            case "03n":
                weatherViewHolder.weather_icon.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "10n":
                weatherViewHolder.weather_icon.setText(R.string.wi_night_cloudy_gusts);
                break;
            case "11n":
                weatherViewHolder.weather_icon.setText(R.string.wi_night_rain);
                break;
            case "13n":
                weatherViewHolder.weather_icon.setText(R.string.wi_night_snow);
                break;

        }

        long timestamp = Long.parseLong(weather.time);
        weatherViewHolder.time.setText(DateUtils.getDate(timestamp * 1000L));
        weatherViewHolder.rain_description.setText(weather.rain_descr);
        weatherViewHolder.humidity.setText(context.getString(R.string.humidity)+weather.humidity);

    }


    public class weatherViewHolder extends ParentRecyclerViewHolder {

        @BindView(R.id.recycler_id)
        CardView cv;

        @BindView(R.id.layout_content)
        LinearLayout layout_content;

        @BindView(R.id.weather_icon_text)
        TextView weather_icon;

        @BindView(R.id.humidity_TextView)
        TextView humidity;

        @BindView(R.id.rain_description__TextView)
        TextView rain_description;

        @BindView(R.id.time_TextView)
        TextView time;

        weatherViewHolder(View itemView) {
            super(itemView);
        }

    }

}

