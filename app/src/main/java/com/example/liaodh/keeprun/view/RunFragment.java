package com.example.liaodh.keeprun.view;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.databinding.FragmentRunBinding;
import com.facebook.drawee.view.SimpleDraweeView;

public class RunFragment extends Fragment {

    private FragmentRunBinding runBinding;

    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return initView(inflater, container);
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        runBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_run, container, false);
        initBar();
        return runBinding.getRoot();

    }

    private void initBar() {
        ColorArcProgressBar arcProgressBar;
        arcProgressBar = runBinding.healthBar;
        arcProgressBar.setMaxValues(100);
        arcProgressBar.setTitle("今日步数");
        arcProgressBar.setCurrentValues(60);

        SimpleDraweeView draweeView = runBinding.userRunImage;
        Uri uri = Uri.parse("res:///" + R.drawable.timg);
        draweeView.setImageURI(uri);

        draweeView = runBinding.startRunImage;
        draweeView.setImageURI(uri);
    }
}
