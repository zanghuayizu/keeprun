package com.example.liaodh.keeprun.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.databinding.FragmentMainBinding;
import com.example.liaodh.keeprun.util.AssetsUtil;
import com.example.liaodh.keeprun.util.HttpUtil;
import com.example.liaodh.keeprun.util.LocationUtil;
import com.example.liaodh.keeprun.util.SpUserInfoUtil;
import com.example.liaodh.keeprun.viewmodel.MsgViewModel;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.SENSOR_SERVICE;

/**
 * 首页
 */
public class MainFragment extends Fragment {
    private FragmentMainBinding mainBinding;
    MsgViewModel msgViewModel;
    private Context context;

    @Nullable
    @Override
    // 创建该Fragment的视图
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        initViewModel();
        return initView(inflater, container);
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        mainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        this.context = getContext();
        initViewData();
        initSensor();
        return mainBinding.getRoot();
    }

    SensorManager sensorManager;
    Sensor shiduSensor;
    Sensor guangSensor;
    Sensor mYaQiangSensor;

    ShiduListner shiduListner;
    GuangListner mGuangListner;
    YaQiangListner mYaQiangListner;

    private void initSensor() {
        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        List<Sensor> list=sensorManager.getSensorList(Sensor.TYPE_ALL);
        Log.e("MainSensor","您的手机有"+list.size()+"个传感器");
        for(Sensor sen:list) {
            String msg = "类型:" + sen.getType() + "名字:" + sen.getName() + "版本:" + sen.getVersion() +
                    "供应商:" + sen.getVendor() + "\n";
            Log.e("MainSensor",msg);
        }











        shiduSensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        if (shiduSensor == null){
            mainBinding.shidu.setText("不支持获取");
        }
        shiduListner = new ShiduListner();
        sensorManager.registerListener(shiduListner,shiduSensor,SensorManager.SENSOR_DELAY_NORMAL);

        guangSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (guangSensor == null){
            mainBinding.guang.setText("不支持获取");
        }
        mGuangListner = new GuangListner();
        sensorManager.registerListener(mGuangListner,guangSensor,SensorManager.SENSOR_DELAY_NORMAL);

        mYaQiangSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if(mYaQiangSensor == null){
            mainBinding.pa.setText("不支持获取");
        }
        mYaQiangListner = new YaQiangListner();
        sensorManager.registerListener(mYaQiangListner,mYaQiangSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    class ShiduListner implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {
            mainBinding.shidu.setText(String.valueOf(event.values[0]));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    class YaQiangListner implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {
            mainBinding.pa.setText(String.valueOf(event.values[0]));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    class GuangListner implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {
            mainBinding.guang.setText(String.valueOf(event.values[0]));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(mGuangListner);
        sensorManager.unregisterListener(mYaQiangListner);
        sensorManager.unregisterListener(shiduListner);
    }

    private void initViewData() {
        initTime();
        if (SpUserInfoUtil.isUserLogin()){
            getWeatherInfo();
            new WeatherThread().start();
            initLocation();
        }
    }

    private void initLocation() {
        mainBinding.userLocation.setText(SpUserInfoUtil.getCityName());
        mainBinding.wendu.setText(SpUserInfoUtil.getWeatherToday());
    }

    private void initTime() {
        new TimeThread().start();
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        String day = String.valueOf(cal.get(Calendar.DATE));
        String current_day_riqi = month + "月" + day + "日";
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
        mainBinding.currentDayRiqi.setText(current_day_riqi);
        mainBinding.currentDayXingqi.setText("星期" + mWay);

    }

    private void initViewModel() {
        msgViewModel = ViewModelProviders.of(this).get(MsgViewModel.class);
    }

    @Override
    // 当Activity的onCreate方法返回时调用
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;  //消息(一个整型值)
                    mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }

    class WeatherThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(30*60*1000);
                    Message msg = new Message();
                    msg.what = 2;  //消息(一个整型值)
                    mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }

    private void getWeatherInfo(){
        String address = "http://t.weather.sojson.com/api/weather/city/" + SpUserInfoUtil.getCityCode();
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
                        final String wendu = weatherObject.optString("wendu") + "℃";
                        SpUserInfoUtil.setWeatherToday(wendu);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mainBinding.wendu.setText(wendu);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //在主线程里面处理消息并更新UI界面
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    long sysTime = System.currentTimeMillis();//获取系统时间
                    CharSequence sysTimeStr = DateFormat.format("hh:mm", sysTime);//时间显示格式
                    mainBinding.currentTime.setText(sysTimeStr); //更新时间
                    mainBinding.userLocationWeidu.setText(SpUserInfoUtil.getLat());
                    mainBinding.userLocationJingdu.setText(SpUserInfoUtil.getLng());
                    break;
                case 2:
                    getWeatherInfo();
                    break;
                default:
                    break;
            }
        }
    };

}
