package com.example.liaodh.keeprun;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.TextUtils;

import com.example.liaodh.keeprun.util.LocationUtil;
import com.example.liaodh.keeprun.util.SpUserInfoUtil;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.litepal.LitePal;

import java.util.Calendar;

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
        startQueryLocation();
    }

    private void startQueryLocation() {
        LocationUtil.getCNBylocation(getContext());
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
        //只有在日期变化的时候才改变存储的值
        setCurrentDay(mSteps);
        SpUserInfoUtil.setTodaySteps(mSteps);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void setCurrentDay(float steps) {
        Calendar cal = Calendar.getInstance();
        String mWay = String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        if (TextUtils.isEmpty(SpUserInfoUtil.getToday())){
            SpUserInfoUtil.setToday(mWay);
        }else {
            if (!SpUserInfoUtil.getToday().equals(mWay)){
                SpUserInfoUtil.setToday(mWay);
                SpUserInfoUtil.setLastMostSteps(steps);
            }
        }
    }
}
