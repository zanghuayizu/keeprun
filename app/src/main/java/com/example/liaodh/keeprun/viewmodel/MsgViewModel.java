package com.example.liaodh.keeprun.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.liaodh.keeprun.livedata.WeatherInfo;
import com.example.liaodh.keeprun.util.AssetsUtil;
import com.example.liaodh.keeprun.util.HttpUtil;
import com.example.liaodh.keeprun.util.LocationUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MsgViewModel extends BaseViewModel {
    public MsgViewModel(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<WeatherInfo> mWeatherInfo;

    public MutableLiveData<WeatherInfo> getWeatherInfo(String cityCode) {
        if (mWeatherInfo == null) {
            mWeatherInfo = new MutableLiveData<>();
        }
        getWeatherInfoFromNet(cityCode);
        return mWeatherInfo;
    }

    private void getWeatherInfoFromNet(String cityCode) {
        String address = "http://t.weather.sojson.com/api/weather/city/" + cityCode;
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("MainFragment", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) return;
                Log.e("MainFragment", response.toString());
                try {
                    if (response.code() <= 200) {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONObject cityinfoObject = jsonObject.optJSONObject("cityInfo");
                        String cityName = cityinfoObject.optString("city");
                        JSONObject weatherObject = jsonObject.optJSONObject("data");
                        String wendu = weatherObject.optString("wendu");
                        WeatherInfo weatherInfo = new WeatherInfo();
                        weatherInfo.setCityName(cityName);
                        weatherInfo.setWendu(wendu);
                        mWeatherInfo.setValue(weatherInfo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

