package com.example.liaodh.keeprun.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.databinding.FragmentUserBinding;
import com.facebook.drawee.view.SimpleDraweeView;

public class UserFragment extends Fragment implements View.OnClickListener {

    private FragmentUserBinding userBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return initView(inflater, container);
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        userBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false);
        initViewData();
        initListener();
        return userBinding.getRoot();
    }

    private void initListener() {
        userBinding.iconIn.setOnClickListener(this);
    }

    private void initViewData() {
        SimpleDraweeView draweeView = userBinding.userImage;
        Uri uri = Uri.parse("res:///" + R.drawable.timg);
        draweeView.setImageURI(uri);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_in:
                //设置用户信息页面
                break;
            default:
                break;
        }
    }
}
