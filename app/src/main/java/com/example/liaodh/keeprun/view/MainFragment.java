package com.example.liaodh.keeprun.view;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.databinding.FragmentMainBinding;
import com.example.liaodh.keeprun.livedata.WeatherInfo;
import com.example.liaodh.keeprun.viewmodel.MsgViewModel;

/**
 *    首页
 */
public class MainFragment extends Fragment{
    private FragmentMainBinding mainBinding;
    MsgViewModel msgViewModel;

    @Nullable
    @Override
    // 创建该Fragment的视图
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        initViewModel();
        return initView(inflater,container);
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        mainBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false);
        checkNetWork();
        return mainBinding.getRoot();
    }

    private void initViewModel() {
        msgViewModel = ViewModelProviders.of(this).get(MsgViewModel.class);
    }

    private void checkNetWork() {
        if (msgViewModel != null){
            LoadingChrysanthemum.showLoadingChrysanthemum(mainBinding.rlRoot,getContext());
            MutableLiveData<WeatherInfo> userResultMutableLiveData = msgViewModel.getWeatherInfo();
            userResultMutableLiveData.observe(this, new Observer<WeatherInfo>() {
                @Override
                public void onChanged(@Nullable WeatherInfo userResult) {
                    //TODO
                    LoadingChrysanthemum.hideLoadingChrysanthemum();
                }
            });

        }
    }

    @Override
    // 当Activity的onCreate方法返回时调用
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
