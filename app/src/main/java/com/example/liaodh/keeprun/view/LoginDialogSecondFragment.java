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
import com.example.liaodh.keeprun.databinding.LogindialogFragmentSecondBinding;

public class LoginDialogSecondFragment extends BaseDialogFragment implements View.OnClickListener {


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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                goThird();
                dismiss();
                break;
            default:
                break;
        }
    }

    private void goThird() {
        if (getActivity() != null) {
            LoginDialogThirdFragment thirdFragment = new LoginDialogThirdFragment();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            thirdFragment.show(ft, getTag());
        }
    }
}
