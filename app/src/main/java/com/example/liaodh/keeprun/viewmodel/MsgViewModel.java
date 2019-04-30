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
        String city = null;
        try {
            city = java.net.URLEncoder.encode("北京", "utf-8");
            //拼地址
            String apiUrl = "http://t.weather.sojson.com/api/weather/city/101030100";
            //开始请求
            URL url= new URL(apiUrl);
            mWeatherInfo.setValue(new WeatherInfo());
            URLConnection open = url.openConnection();
            InputStream input = open.getInputStream();
            //这里转换为String，带上包名，怕你们引错包
            String result = input.toString();
            //输出
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
