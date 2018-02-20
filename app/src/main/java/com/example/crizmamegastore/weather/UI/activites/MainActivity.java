package com.example.crizmamegastore.weather.UI.activites;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.crizmamegastore.weather.App.AppController;
import com.example.crizmamegastore.weather.App.Constant;
import com.example.crizmamegastore.weather.Base.ParentActivity;
import com.example.crizmamegastore.weather.GPS.GPSTracker;
import com.example.crizmamegastore.weather.GPS.GPSTrakerListner;
import com.example.crizmamegastore.weather.Listners.ChooseCityListener;
import com.example.crizmamegastore.weather.Model.CitiesModel;
import com.example.crizmamegastore.weather.Model.CitiesModel_;
import com.example.crizmamegastore.weather.Model.ForecastResponse.Weather;
import com.example.crizmamegastore.weather.Model.ForecastResponse.WeatherData;
import com.example.crizmamegastore.weather.Network.RetroWeb;
import com.example.crizmamegastore.weather.Network.ServiceApi;
import com.example.crizmamegastore.weather.R;
import com.example.crizmamegastore.weather.UI.adapters.ForecastAdapter;
import com.example.crizmamegastore.weather.UI.views.ChooseCityDialog;
import com.example.crizmamegastore.weather.Utils.CommonUtil;
import com.example.crizmamegastore.weather.Utils.DialogUtil;
import com.example.crizmamegastore.weather.Utils.LocationUtils;
import com.example.crizmamegastore.weather.Utils.PermissionUtils;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends ParentActivity implements GPSTrakerListner , ChooseCityListener {

    GPSTracker gps;
    public String mLang, mLat;
    private AlertDialog mAlertDialog;
    boolean startTracker = false;
    List<Weather> mWeather = new ArrayList<>();
    ForecastAdapter forecastAdapter;
    LinearLayoutManager mLinearLayoutManager;
    private Typeface weatherFont;
    private final static String PATH_TO_WEATHER_FONT = "fonts/weather.ttf";
    String cityName = "" ;

    BoxStore mBoxStore;
    private Box<CitiesModel> weatherModelBox;
    ChooseCityDialog mChooseCityDialog ;

    @BindView(R.id.rv_recycle)
    RecyclerView rv_recycle;

    @BindView(R.id.lay_progress)
    RelativeLayout lay_progress;

    @BindView(R.id.lay_no_item)
    RelativeLayout lay_no_item;

    @BindView(R.id.lay_no_internet)
    RelativeLayout lay_no_internet;

    @BindView(R.id.iv_no_item)
    ImageView iv_no_item;

    @BindView(R.id.tv_no_content)
    TextView tv_no_content;

    @BindView(R.id.weather_report)
    TextView weather_report;

    @BindView(R.id.place)
    TextView place;

    @BindView(R.id.weather_icon)
    TextView weather_icon;

    @BindView(R.id.weather_humid)
    TextView weather_humid;

    @BindView(R.id.main_layout)
    RelativeLayout main_layout;

    @BindView(R.id.layout_main_info)
    RelativeLayout layout_main_info;

    @BindView(R.id.fab_add)
    FloatingActionButton fab_add ;

    @BindView(R.id.appBar)
    AppBarLayout appBar ;

    private Query<CitiesModel> notesQuery;
    private List<CitiesModel> modelArrayList;

    public static void startActivity(AppCompatActivity mAppCompatActivity) {
        Intent mIntent = new Intent(mAppCompatActivity, MainActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mAppCompatActivity.finish();
        mAppCompatActivity.startActivity(mIntent);
    }

    @Override
    protected void initializeComponents() {

        getLocationWithPermission();

        weatherFont = Typeface.createFromAsset(getAssets(), PATH_TO_WEATHER_FONT);
        mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv_recycle.setLayoutManager(mLinearLayoutManager);
        forecastAdapter = new ForecastAdapter(mContext, mWeather, weatherFont);
        rv_recycle.setAdapter(forecastAdapter);

        mBoxStore = ((AppController) getApplication()).getBoxStore();
        weatherModelBox = mBoxStore.boxFor(CitiesModel.class);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isEnableToolbar() {
        return true;
    }

    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected boolean isEnableBack() {
        return false;
    }

    @Override
    protected boolean hideInputType() {
        return true;
    }

    @OnClick(R.id.iv_cities)
    void onHistoryClick() {
        mChooseCityDialog = new ChooseCityDialog(MainActivity.this);
        mChooseCityDialog.onChooseCityListener(this);
        mChooseCityDialog.show();
    }

    @OnClick(R.id.iv_search)
    void onSearchClick() {
        searchCities();
    }

    @OnClick(R.id.fab_add)
    void onAddClick() {
        mAlertDialog = DialogUtil.showAlertDialog(MainActivity.this, "Add  " + "\"" + cityName + "\"" + "  to yout cities", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, final int i) {
                addToMyCities();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mAlertDialog.dismiss();
            }
        });
    }

    private void addToMyCities() {
        boolean alreadyAdded = false ;
        notesQuery = weatherModelBox.query().order(CitiesModel_.id).build();
        modelArrayList = notesQuery.find();
        CitiesModel citiesModel = new CitiesModel(cityName, mLat, mLang);
        if(citiesModel.equals("")){
            return;
        }
        for (int i = 0; i < modelArrayList.size(); i++) {
            if(modelArrayList.get(i).getCityName().equals(citiesModel.getCityName())){
                alreadyAdded = true ;
            }
        }
        if(alreadyAdded){
            CommonUtil.makeSnake(main_layout  , R.string.city_added_befoer);
        }else{
            weatherModelBox.put(citiesModel);
        }
    }

    @Override
    public void onTrackerSuccess(Double lat, Double log) {
        Log.e("Direction", "Direction Success");
        if (startTracker) {
            if (lat != 0.0 && log != 0.0) {
                Log.e("LATLNG", "Lat:" + mLat + "  Lng:" + Double.toString(log));
            }
        }
    }

    @Override
    public void onStartTracker() {
        startTracker = true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Code", "requestCode: " + requestCode);
        switch (requestCode) {
            case Constant.RequestCode.GET_LOCATION: {
                    Log.e("Code", "request GPS Enabled True");
                    getCurrentLocation();
            }
            break;
            case Constant.RequestCode.GPS_ENABLING: {
                    Log.e("Code", "request GPS Enabled True");
                    getCurrentLocation();
            }
            break;
            default:
                finish();
        }
    }


    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case 800: {
                if (grantResults.length > 0) {
                    Log.e("Permission", "All permission are granted");
                    getCurrentLocation();
                    for (int i = 0; i < grantResults.length; i++) {
                        Log.e("Permission", grantResults[0] + "");
                    }
                } else {
                    setWeatherData(mSharedPrefManager.getWeatherData());
                    Log.e("Permission", "permission arn't granted");
                }
                return;
            }
        }
    }

    @Override
    public void onCarTypeListener(CitiesModel citiesModel) {
        mLat = citiesModel.getLat();
        mLang = citiesModel.getLong();
        getForecast();
    }

    @SuppressLint("RestrictedApi")
    private void searchCities() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(this.getString(R.string.search_title));
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setMaxLines(1);
        input.setSingleLine(true);
        alert.setView(input, 32, 0, 32, 0);
        alert.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String result = input.getText().toString();
                if (!result.isEmpty()) {
                    List<Double> latitudeAndLongitudeFromAddress = LocationUtils.getLatitudeAndLongitudeFromAddress(MainActivity.this, result);
                    try {
                        mLat = String.valueOf(latitudeAndLongitudeFromAddress.get(0));
                        mLang = String.valueOf(latitudeAndLongitudeFromAddress.get(1));
                        getForecast();
                    }
                    catch (IndexOutOfBoundsException e){
                        CommonUtil.makeSnake(main_layout , R.string.address_not_found);
                    }
                }
            }
        });
        alert.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
    }


    public void getLocationWithPermission() {
        gps = new GPSTracker(this, this);
        if (PermissionUtils.canMakeSmores(Build.VERSION_CODES.LOLLIPOP_MR1)) {
            if (!PermissionUtils.hasPermissions(MainActivity.this, PermissionUtils.GPS_PERMISSION)) {
                CommonUtil.PrintLogE("Permission not granted");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(PermissionUtils.GPS_PERMISSION,
                            Constant.RequestPermission.REQUEST_GPS_LOCATION);
                    Log.e("GPS", "1");
                }
            } else {
                getCurrentLocation();
                Log.e("GPS", "2");
            }
        } else {
            Log.e("GPS", "3");
            getCurrentLocation();
        }

    }

    void getCurrentLocation() {
        gps.getLocation();
        if (!gps.canGetLocation()) {
            mAlertDialog = DialogUtil.showAlertDialog(MainActivity.this,
                    getString(R.string.gps_detect), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialogInterface, final int i) {
                            mAlertDialog.dismiss();
                            Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, Constant.RequestCode.GPS_ENABLING);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mAlertDialog.dismiss();
                            setWeatherData(mSharedPrefManager.getWeatherData());
                        }
                    });
        } else {
            if (gps.getLatitude() != 0.0 && gps.getLongitude() != 0.0) {
                mLat = String.valueOf(gps.getLatitude());
                mLang = String.valueOf(gps.getLongitude());
                Log.e("LAT", mLat);
                Log.e("LNG", mLang);
                getForecast();
            }
        }
    }


    void getForecast() {
        String apiKey = getResources().getString(R.string.apiKey);

        lay_progress.setVisibility(View.VISIBLE);
        lay_no_internet.setVisibility(View.GONE);
        lay_no_item.setVisibility(View.GONE);

        RetroWeb.getClient().create(ServiceApi.class).getWeatherInfo(mLat, mLang, "40", apiKey)
                .enqueue(new Callback<WeatherData>() {
                    @Override
                    public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                        if (response.isSuccessful()) {

                            lay_progress.setVisibility(View.GONE);
                            layout_main_info.setVisibility(View.VISIBLE);

                            mSharedPrefManager.setWeatherData(response.body());

                            if (response.body().getList().size() > 0) {

                                setWeatherData(response.body());

                            } else {
                                lay_no_item.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherData> call, Throwable t) {
                        lay_progress.setVisibility(View.GONE);
                        layout_main_info.setVisibility(View.VISIBLE);
                        CommonUtil.makeSnake(main_layout, R.string.connection_error);
                        setWeatherData(mSharedPrefManager.getWeatherData());
                    }
                });
    }


    protected void setWeatherData(WeatherData weatherData) {
        if(weatherData != null) {
            weather_icon.setTypeface(weatherFont);
            weather_report.setText(weatherData.getList().get(0).getWeather().get(0).getDescription());
            place.setText(weatherData.getCity().getName() + ", " + weatherData.getCity().getCountry());
            cityName = weatherData.getCity().getName() ;
            setToolbarTitle(weatherData.getCity().getName() + ", " + weatherData.getCity().getCountry());
            weather_humid.setText(getString(R.string.humidity)+weatherData.getList().get(0).getMain().getHumidity());
            switch (weatherData.getList().get(0).getWeather().get(0).getIcon()) {
                case "01d":
                    weather_icon.setText(R.string.wi_day_sunny);
                    break;
                case "02d":
                    weather_icon.setText(R.string.wi_cloudy_gusts);
                    break;
                case "03d":
                    weather_icon.setText(R.string.wi_cloud_down);
                    break;
                case "04d":
                    weather_icon.setText(R.string.wi_cloudy);
                    break;
                case "04n":
                    weather_icon.setText(R.string.wi_night_cloudy);
                    break;
                case "10d":
                    weather_icon.setText(R.string.wi_day_rain_mix);
                    break;
                case "11d":
                    weather_icon.setText(R.string.wi_day_thunderstorm);
                    break;
                case "13d":
                    weather_icon.setText(R.string.wi_day_snow);
                    break;
                case "01n":
                    weather_icon.setText(R.string.wi_night_clear);
                    break;
                case "02n":
                    weather_icon.setText(R.string.wi_night_cloudy);
                    break;
                case "03n":
                    weather_icon.setText(R.string.wi_night_cloudy_gusts);
                    break;
                case "10n":
                    weather_icon.setText(R.string.wi_night_cloudy_gusts);
                    break;
                case "11n":
                    weather_icon.setText(R.string.wi_night_rain);
                    break;
                case "13n":
                    weather_icon.setText(R.string.wi_night_snow);
                    break;
            }
            String[] humidity = new String[40];
            String[] rain_description = new String[40];
            String[] icon = new String[40];
            String[] time = new String[40];
            for (int i = 0; i < weatherData.getList().size(); i++) {
                humidity[i] = String.valueOf(weatherData.getList().get(i).getMain().getHumidity());
                rain_description[i] = String.valueOf(weatherData.getList().get(i).getWeather().get(0).getDescription());
                icon[i] = String.valueOf(weatherData.getList().get(i).getWeather().get(0).getIcon());
                time[i] = String.valueOf(weatherData.getList().get(i).getDt());

                Log.w("humidity", humidity[i]);
                Log.w("rain_description", rain_description[i]);
                Log.w("icon", icon[i]);
                Log.w("time", time[i]);

                mWeather.add(new Weather(String.valueOf(weatherData.getList().get(i).getWeather().get(0).getIcon()),
                        String.valueOf(weatherData.getList().get(i).getMain().getHumidity()),
                        String.valueOf(weatherData.getList().get(i).getWeather().get(0).getDescription()),
                        String.valueOf(weatherData.getList().get(i).getDt())));

                forecastAdapter.notifyDataSetChanged();

            }
        }else{
            finish();
        }
    }

}
