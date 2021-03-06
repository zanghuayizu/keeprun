package com.example.liaodh.keeprun.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.databinding.FragmentRunBinding;
import com.example.liaodh.keeprun.util.SpUserInfoUtil;
import com.example.liaodh.keeprun.view.commod.ColorArcProgressBar;
import com.facebook.drawee.view.SimpleDraweeView;


public class RunFragment extends Fragment {

    private FragmentRunBinding runBinding;

    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return initView(inflater, container);
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        runBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_run, container, false);
        initBar();
        initListener();
        return runBinding.getRoot();

    }

    private void initListener() {
        runBinding.startRunImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),RunningManAvtivity.class);
                startActivity(intent);
            }
        });
    }

    ColorArcProgressBar arcProgressBar;
    private void initBar() {
        arcProgressBar = runBinding.healthBar;
        runBinding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arcProgressBar.setCurrentValues(0);
                arcProgressBar.setCurrentValues(SpUserInfoUtil.getTodaySteps()-SpUserInfoUtil.getLastMostSteps());
            }
        });
        arcProgressBar.setMaxValues(Float.valueOf(SpUserInfoUtil.getStepNum()));
        arcProgressBar.setCurrentValues(SpUserInfoUtil.getTodaySteps()-SpUserInfoUtil.getLastMostSteps());
        arcProgressBar.setTitle("今日步数");

        SimpleDraweeView draweeView = runBinding.userRunImage;
        Uri uri = Uri.parse("res:///" + R.drawable.runningman);
        draweeView.setImageURI(uri);

        draweeView = runBinding.startRunImage;
        draweeView.setImageURI(uri);
        new stepThread().start();
    }

    @Override
    public void onResume() {
        super.onResume();
        runBinding.healthBarMaxnum.setText(String.valueOf(SpUserInfoUtil.getStepNum()));
    }

    class stepThread extends Thread{
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(10*1000);
                    Message message = new Message();
                    message.what = 1;
                    mHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }while (true);
        }
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    arcProgressBar.setCurrentValues(SpUserInfoUtil.getTodaySteps()
                            -SpUserInfoUtil.getLastMostSteps());
                    break;
                default:
                    break;
            }
        }
    };
}
