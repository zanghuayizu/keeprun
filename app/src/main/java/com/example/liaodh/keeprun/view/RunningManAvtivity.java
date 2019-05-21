package com.example.liaodh.keeprun.view;

import android.databinding.DataBindingUtil;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.MediaController;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.databinding.RunningmanBinding;
import com.example.liaodh.keeprun.util.SpUserInfoUtil;

public class RunningManAvtivity extends AppCompatActivity implements SensorEventListener {

    private RunningmanBinding mBinding;
    double lastLat;
    double lastLng;
    boolean isFirst = true;
    ImageView Compass;  //指南针图片
    float currentDegree = 0f; //指南针图片转过的角度
    SensorManager mSensorManager; //管理器

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    long sysTime = System.currentTimeMillis();//获取系统时间
                    CharSequence sysTimeStr = DateFormat.format("mm:ss", sysTime - startTime);//时间显示格式
                    mBinding.runTime.setText(sysTimeStr); //更新时间
                    mBinding.weidu.setText("纬度 " + SpUserInfoUtil.getLat().substring(0, 7));
                    mBinding.jingdu.setText("经度" + SpUserInfoUtil.getLng().substring(0, 8));
                    double curLat = Double.parseDouble(SpUserInfoUtil.getLat());//纬度
                    double curLng = Double.parseDouble(SpUserInfoUtil.getLng());//经度
                    if (isFirst) {
                        lastLat = curLat;
                        lastLng = curLng;
                        isFirst = false;
                        SpUserInfoUtil.setRunDis(0);
                    }
                    SpUserInfoUtil.setRunDis((float) (SpUserInfoUtil.getRunDis() + getDistance(curLat, curLng, lastLat, lastLng)));
                    mBinding.runDis.setText(String.valueOf(SpUserInfoUtil.getRunDis()));
                    lastLat = curLat;
                    lastLng = curLng;
                    break;
                default:
                    break;
            }
        }
    };
    private long startTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.runningman);
        Compass = mBinding.compass;
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE); //获取管理服务
        startVideo();
    }

    private void startVideo() {

        mBinding.video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                }
            }
        });
        MediaController mediaController = new MediaController(this);
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.video;
        mBinding.video.setVideoURI(Uri.parse(uri));
        mBinding.video.setMediaController(mediaController);
        mediaController.setMediaPlayer(mBinding.video);
        mBinding.video.requestFocus();
        mBinding.video.start();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.video.setVisibility(View.GONE);
                startRun();
            }
        }, 5000);
    }

    private void startRun() {
        startTime = System.currentTimeMillis();//获取系统时间
        new TimeThread().start();
    }

    class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Message msg = new Message();
                    msg.what = 1;  //消息(一个整型值)
                    mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }

    private static final double EARTH_RADIUS = 6378137.0;

    public static double getDistance(double longitude1, double latitude1,
                                     double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    @Override
    protected void onResume(){
        super.onResume();
        //注册监听器
        mSensorManager.registerListener( this
                , mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    //取消注册
    @Override
    protected void onPause(){
        mSensorManager.unregisterListener(this);
        super.onPause();

    }

    @Override
    protected void onStop(){
        mSensorManager.unregisterListener(this);
        super.onStop();

    }

    //传感器值改变
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    //精度改变
    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        //获取触发event的传感器类型
        int sensorType = event.sensor.getType();

        switch(sensorType){
            case Sensor.TYPE_ORIENTATION:
                float degree = event.values[0]; //获取z转过的角度
                //穿件旋转动画
                RotateAnimation ra = new RotateAnimation(currentDegree,-degree, Animation.RELATIVE_TO_SELF,0.5f
                        ,Animation.RELATIVE_TO_SELF,0.5f);
                ra.setDuration(100);//动画持续时间
                Compass.startAnimation(ra);
                currentDegree = -degree;
                break;

        }
    }
}

