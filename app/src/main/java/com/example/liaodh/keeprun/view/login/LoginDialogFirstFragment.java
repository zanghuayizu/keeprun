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
import com.example.liaodh.keeprun.databinding.LogindialogFragementFirstBinding;
import com.example.liaodh.keeprun.util.SpUserInfoUtil;
import com.example.liaodh.keeprun.view.BaseDialogFragment;
import com.example.liaodh.keeprun.view.commod.CommonToast;

public class LoginDialogFirstFragment extends BaseDialogFragment implements View.OnClickListener,DialogInterface.OnKeyListener {


    private LogindialogFragementFirstBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return initView(inflater, container);
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.logindialog_fragement_first, container, false);
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
                goSecond();
                break;
            default:
                break;
        }

    }

    private void goSecond() {
        if (TextUtils.isEmpty(mBinding.loginUserName.getText())){
            CommonToast.showShortToast("请输入昵称");
        }else {
            if (!mBinding.userSexBoy.isChecked() && !mBinding.userSexGirl.isChecked()) {
                CommonToast.showShortToast("请选择性别");
            }else {
                SpUserInfoUtil.setUserName(mBinding.loginUserName.getText().toString());
                SpUserInfoUtil.setIsBoy(mBinding.userSexBoy.isChecked());
                if (getActivity() != null) {
                    LoginDialogSecondFragment secondFragment = new LoginDialogSecondFragment();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    secondFragment.show(ft, getTag());
                }
                dismiss();
            }
        }
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP;
    }
}
