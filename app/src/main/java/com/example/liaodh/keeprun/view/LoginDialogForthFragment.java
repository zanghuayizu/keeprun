package com.example.liaodh.keeprun.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.databinding.LogindialogFragmentForthBinding;

public class LoginDialogForthFragment extends BaseDialogFragment implements OnClickListener {

    private LogindialogFragmentForthBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return initView(inflater, container);
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.logindialog_fragment_forth, container, false);
        initListener();
        return mBinding.getRoot();
    }

    private void initListener() {
        mBinding.login.setOnClickListener(this);
        mBinding.userImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                dismiss();
                break;
            case R.id.user_image:
                //拍照或者从相册上传
                break;
            default:
                break;
        }
    }
}
