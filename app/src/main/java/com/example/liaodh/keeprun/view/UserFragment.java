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

public class UserFragment extends Fragment {

    private FragmentUserBinding userBinding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return initView(inflater,container);
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
       userBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_user,container,false);
       initViewData();
       return userBinding.getRoot();
    }

    private void initViewData() {
        SimpleDraweeView draweeView = userBinding.userImage;
        Uri uri = Uri.parse("res:///" + R.drawable.timg);
        draweeView.setImageURI(uri);
    }
}
