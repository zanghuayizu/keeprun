package com.example.liaodh.keeprun.view;

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
import com.example.liaodh.keeprun.databinding.LogindialogFragmentSecondBinding;
import com.example.liaodh.keeprun.util.SpUserInfoUtil;

public class LoginDialogSecondFragment extends BaseDialogFragment
        implements View.OnClickListener ,DialogInterface.OnKeyListener{


    private LogindialogFragmentSecondBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return initView(inflater, container);
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.logindialog_fragment_second, container, false);
        initListener();
        return mBinding.getRoot();
    }

    private void initListener() {
        mBinding.login.setOnClickListener(this);
        getDialog().setOnKeyListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                goThird();
                break;
            default:
                break;
        }
    }

    private void goThird() {
        if (TextUtils.isEmpty(mBinding.userHeight.getText()) || TextUtils.isEmpty(mBinding.userWeight.getText())){
            CommonToast.showShortToast("请输入身高或者体重");
        }else {
            SpUserInfoUtil.setUserHeight(mBinding.userHeight.getText().toString());
            SpUserInfoUtil.setUserHeight(mBinding.userWeight.getText().toString());
            if (getActivity() != null) {
                LoginDialogThirdFragment thirdFragment = new LoginDialogThirdFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                thirdFragment.show(ft, getTag());
            }
            dismiss();
        }
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP;
    }
}
