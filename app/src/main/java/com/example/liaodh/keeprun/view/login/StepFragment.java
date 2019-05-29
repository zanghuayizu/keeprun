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
import com.example.liaodh.keeprun.databinding.LogindialogFragmentSecondBinding;
import com.example.liaodh.keeprun.databinding.LogindialogFragmentStepBinding;
import com.example.liaodh.keeprun.util.SpUserInfoUtil;
import com.example.liaodh.keeprun.view.BaseDialogFragment;
import com.example.liaodh.keeprun.view.commod.CommonToast;

public class StepFragment extends BaseDialogFragment implements View.OnClickListener ,DialogInterface.OnKeyListener{

    private LogindialogFragmentStepBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return initView(inflater, container);
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.logindialog_fragment_step, container, false);
        initListener();
        return mBinding.getRoot();
    }

    private void initListener() {
        mBinding.login.setOnClickListener(this);
        mBinding.userHeight.setOnClickListener(this);
        getDialog().setOnKeyListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                goThird();
                break;
            case R.id.user_height:
                goSelect(3);
                break;
            default:
                break;
        }
    }

    private void goSelect(final int i) {
        if (getActivity() != null) {
            SelectHeightWeightDialogFragment fragment = new SelectHeightWeightDialogFragment();
            fragment.setSelectListener(i,new LoginDialogSecondFragment.setSelect() {
                @Override
                public void setSelect(int current) {
                    if (i == 3){
                        mBinding.userHeight.setText(String.valueOf(current));
                    }
                }
            });
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragment.show(ft, getTag());
        }
    }

    private void goThird() {
        if (TextUtils.isEmpty(mBinding.userHeight.getText()) || TextUtils.isEmpty(mBinding.userWeight.getText())){
            CommonToast.showShortToast("请输入步伐和步数");
        }else {
            SpUserInfoUtil.setStepLong(mBinding.userHeight.getText().toString());
            SpUserInfoUtil.setStepNum(mBinding.userWeight.getText().toString());
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
        if (keyCode == KeyEvent.KEYCODE_BACK ){
            if (getActivity() != null) {
                LoginDialogSecondFragment loginFragment = new LoginDialogSecondFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                loginFragment.show(ft, getTag());
                dismiss();
            }
            return true;
        }
        return false;
    }

    public interface setSelect{
        void setSelect(int current);
    }
}
