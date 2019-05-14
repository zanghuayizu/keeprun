package com.example.liaodh.keeprun.view.login;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.databinding.LogindiaologFragmentThirdBinding;
import com.example.liaodh.keeprun.util.AssetsUtil;
import com.example.liaodh.keeprun.util.LocationUtil;
import com.example.liaodh.keeprun.util.SpUserInfoUtil;
import com.example.liaodh.keeprun.view.BaseDialogFragment;
import com.example.liaodh.keeprun.view.commod.CommonToast;
import com.example.liaodh.keeprun.view.commod.LoadingChrysanthemum;

public class LoginDialogThirdFragment extends BaseDialogFragment
        implements View.OnClickListener ,DialogInterface.OnKeyListener {

    private LogindiaologFragmentThirdBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return initView(inflater, container);
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.logindiaolog_fragment_third, container, false);
        initListener();
        return mBinding.getRoot();
    }

    private void initListener() {
        mBinding.login.setOnClickListener(this);
        mBinding.locationImage.setOnClickListener(this);
        getDialog().setOnKeyListener(this);
        CommonToast.showShortToast("定位前请先打开网络");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                goForth();
                break;
            case R.id.location_image:
                //调用定位
                setUserWhere();
                break;
            default:
                break;
        }
    }

    private void setUserWhere() {
        getUserLocation();
    }

    private void goForth() {
        if (TextUtils.isEmpty(mBinding.location.getText())){
            CommonToast.showShortToast("请先定位");
        }else {
            if (getActivity() != null) {
                LoginDialogForthFragment forthFragment = new LoginDialogForthFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                forthFragment.show(ft, getTag());
            }
            dismiss();
        }
    }

    private void getUserLocation() {
        //从网络获取信息然后存入本地
        LoadingChrysanthemum.showLoadingChrysanthemum(mBinding.rlRoot,getContext());
        String cityCode;
        LocationUtil.getCNBylocation(getContext());
        while (TextUtils.isEmpty(LocationUtil.cityName)){
            LocationUtil.getCNBylocation(getContext());
        }
        cityCode = AssetsUtil.getCityCodeByCityNameFromJson(getContext(), LocationUtil.cityName);
        SpUserInfoUtil.setCityCode(cityCode);
        mBinding.location.setText(SpUserInfoUtil.getCityName());
        LoadingChrysanthemum.hideLoadingChrysanthemum();
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP;
    }
}
