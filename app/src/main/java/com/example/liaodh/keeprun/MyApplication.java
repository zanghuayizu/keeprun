package com.example.liaodh.keeprun;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.liaodh.keeprun.util.AssetsUtil;
import com.example.liaodh.keeprun.util.HttpUtil;
import com.example.liaodh.keeprun.util.LocationUtil;
import com.example.liaodh.keeprun.util.SpUserInfoUtil;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static org.litepal.LitePalApplication.getContext;

public class MyApplication extends Application implements SensorEventListener{
    private static MyApplication mInstance;
    public static Context getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        LitePal.initialize(this);
        mInstance = this;
        startMyServiceCountStep();
        startMyServiceGetWeatherMsg();
    }

    private void startMyServiceGetWeatherMsg() {
        //从网络获取信息然后存入本地
        String cityCode;
        String address;
        while (LocationUtil.cityName == null){
            LocationUtil.getCNBylocation(getContext());
        }
        if (!TextUtils.isEmpty(SpUserInfoUtil.getCityName())
                && (SpUserInfoUtil.getCityName().contains(LocationUtil.cityName)
                || LocationUtil.cityName.contains(SpUserInfoUtil.getCityName()))){
            cityCode = SpUserInfoUtil.getCityCode();
        }else {
            cityCode = AssetsUtil.getCityCodeByCityNameFromJson(getContext(), LocationUtil.cityName);
            SpUserInfoUtil.setCityName(LocationUtil.cityName);
            SpUserInfoUtil.setCityCode(cityCode);
        }
        address = "http://t.weather.sojson.com/api/weather/city/" + cityCode;
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response){
                try {
                    if (response.code() <= 200) {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONObject weatherObject = jsonObject.optJSONObject("data");
                        String wendu = weatherObject.optString("wendu") + "℃";
                        SpUserInfoUtil.setWeatherToday(wendu);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private SensorManager sensorManager;
    private Sensor stepCounter;
    private void startMyServiceCountStep() {
        //后台统计步数
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(stepCounter != null){
            // 如果sensor找到，则注册监听器
            sensorManager.registerListener(this,stepCounter,1000000);
        }
    }

    private float mSteps;
    @Override
    public void onSensorChanged(SensorEvent event) {
        mSteps = event.values[0];
        SpUserInfoUtil.setTodaySteps(mSteps);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
