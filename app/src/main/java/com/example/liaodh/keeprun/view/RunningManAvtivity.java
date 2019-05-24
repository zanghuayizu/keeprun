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
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.MediaController;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.databinding.RunningmanBinding;
import com.example.liaodh.keeprun.view.commod.CommonToast;

import java.util.Timer;
import java.util.TimerTask;

public class RunningManAvtivity extends AppCompatActivity implements SensorEventListener,
        View.OnClickListener, View.OnKeyListener, View.OnLongClickListener {

    private RunningmanBinding mBinding;
    TimerTask task;
    Timer timer;
    ImageView Compass;  //指南针图片
    float currentDegree = 0f; //指南针图片转过的角度
    SensorManager mSensorManager; //管理器
    private Sensor stepCounter;//步伐总数传感器
    private Sensor stepDetector;//单次步伐传感器

    private Handler mHandler = new Handler();

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
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); //获取管理服务
        stepDetector = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);//获取单次计步传感器
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
        initListener();
        startTime = System.currentTimeMillis();//获取系统时间
        run();
    }

    private void initListener() {
        mBinding.start.setOnClickListener(this);
        mBinding.pouse.setOnClickListener(this);
        mBinding.stop.setOnClickListener(this);
        mBinding.stop.setOnLongClickListener(this);
        mSensorManager.registerListener(this
                , mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR), SensorManager.SENSOR_DELAY_GAME);
    }


    long pouseTime;
    long reStartTime;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                reStartTime = System.currentTimeMillis();
                if (task != null) {
                    task = null;
                    task = new MyTimerTask();
                    timer.schedule(task, 0, 1000);
                }
                mBinding.stop.setVisibility(View.GONE);
                mBinding.start.setVisibility(View.GONE);
                mBinding.pouse.setVisibility(View.VISIBLE);
                break;
            case R.id.stop:
                task.cancel();
                CommonToast.showShortToast("长按退出");
                break;
            case R.id.pouse:
                task.cancel();
                pouseTime = System.currentTimeMillis();
                mBinding.stop.setVisibility(View.VISIBLE);
                mBinding.start.setVisibility(View.VISIBLE);
                mBinding.pouse.setVisibility(View.GONE);
                break;
        }
    }

    private void save() {
        //保存数据
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册监听器
        mSensorManager.registerListener(this
                , mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    //取消注册
    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(this);
        super.onPause();

    }

    @Override
    protected void onStop() {
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

        switch (sensorType) {
            case Sensor.TYPE_ORIENTATION:
                float degree = event.values[0]; //获取z转过的角度
                //穿件旋转动画
                RotateAnimation ra = new RotateAnimation(currentDegree, -degree, Animation.RELATIVE_TO_SELF, 0.5f
                        , Animation.RELATIVE_TO_SELF, 0.5f);
                ra.setDuration(100);//动画持续时间
                Compass.startAnimation(ra);
                currentDegree = -degree;
                break;
            case Sensor.TYPE_STEP_DETECTOR:
                mBinding.runDis.setText(String.valueOf(event.values[0]));
                break;
        }
    }

    private void run() {
        timer = new Timer();
        task = new MyTimerTask();
        timer.schedule(task, 0, 1000);
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.stop:
                task.cancel();
                finish();
                save();
                break;
        }
        return true;
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //更新时间
                    updateRunTimes();
                }
            });
        }
    }

    private void updateRunTimes() {
        long sysTime = System.currentTimeMillis();//获取系统时间
        CharSequence sysTimeStr = DateFormat.format("mm:ss",
                sysTime - startTime - (reStartTime - pouseTime));//时间显示格式
        mBinding.runTime.setText(sysTimeStr); //更新时间
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP;
    }

}

