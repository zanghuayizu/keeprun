package com.example.liaodh.keeprun.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.databinding.ViewChrysanthemumLoadingBinding;

public class LoadingChrysanthemum extends RelativeLayout{

    private ViewChrysanthemumLoadingBinding mBinding;

    public LoadingChrysanthemum(Context context) {
        super(context);
        initView(context);
    }

    public LoadingChrysanthemum(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout
                .view_chrysanthemum_loading,this,true);
    }

    public ViewChrysanthemumLoadingBinding getBinding(){
        return mBinding;
    }
}
