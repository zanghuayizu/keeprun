package com.example.liaodh.keeprun.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
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
import com.example.liaodh.keeprun.util.SpUserInfoUtil;
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
        if (SpUserInfoUtil.isUserLogin()){
            userBinding.userName.setText(SpUserInfoUtil.getUserName());
            userBinding.userHeight.setText(SpUserInfoUtil.getUserHeight());
            userBinding.weekTime.setText(SpUserInfoUtil.getWeekTime());
            userBinding.weekDis.setText(SpUserInfoUtil.getWeekDis());
            userBinding.allTime.setText(SpUserInfoUtil.getAllTime());
            userBinding.allDis.setText(SpUserInfoUtil.getAllDis());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_in:
                //设置用户信息页面
                Intent intent = new Intent(getActivity(),UserEditActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
