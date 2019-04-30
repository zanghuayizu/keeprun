package com.example.liaodh.keeprun.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.liaodh.keeprun.livedata.WeatherInfo;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

public class MsgViewModel extends BaseViewModel {
    public MsgViewModel(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<WeatherInfo> mWeatherInfo;

    public MutableLiveData<WeatherInfo> getWeatherInfo() {
        if(mWeatherInfo == null) {
            mWeatherInfo = new MutableLiveData<>();
        }
        getWeatherInfoFromNet();
        return mWeatherInfo;
    }

    private void getWeatherInfoFromNet() {
        //请求天气信息
        WeatherInfo weatherInfo = new WeatherInfo();
        mWeatherInfo.setValue(weatherInfo);
    }
}
