package com.example.liaodh.keeprun.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.databinding.LogindiaologFragmentThirdBinding;

public class LoginDialogThirdFragment extends BaseDialogFragment implements View.OnClickListener {

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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                goForth();
                dismiss();
                break;
            case R.id.location_image:
                //调用定位
                break;
            default:
                break;
        }
    }

    private void goForth() {
        if (getActivity() != null) {
            LoginDialogForthFragment forthFragment = new LoginDialogForthFragment();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            forthFragment.show(ft, getTag());
        }
    }
}
