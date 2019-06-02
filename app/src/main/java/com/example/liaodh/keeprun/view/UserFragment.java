package com.example.liaodh.keeprun.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.databinding.FragmentUserBinding;
import com.example.liaodh.keeprun.util.HttpUtil;
import com.example.liaodh.keeprun.util.SpUserInfoUtil;
import com.example.liaodh.keeprun.view.commod.CommonToast;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static org.litepal.LitePalApplication.getContext;

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
        initListener();
        return userBinding.getRoot();
    }

    private void initListener() {
        userBinding.iconIn.setOnClickListener(this);
        userBinding.reviewDataIn.setOnClickListener(this);
    }

    private void initViewData() {
        initUserInfo();
        if (SpUserInfoUtil.isUserLogin()) {
            String url = HttpUtil.baseUrl + "getUserRunInfo?" + "userId=" + SpUserInfoUtil.getUserId();
            HttpUtil.getUserInfo(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CommonToast.showShortToast("失败");
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        try {
                            JSONArray runInfo = new JSONArray(response.body().string());
                            JSONObject object = runInfo.getJSONObject(runInfo.length()-1);
                            SpUserInfoUtil.setWeekTime(String.valueOf(object.optLong("weekTimes")));
                            SpUserInfoUtil.setWeekDis(String.valueOf(object.optLong("weekDis")));
                            SpUserInfoUtil.setAllTime(String.valueOf(object.optLong("allTimes")));
                            SpUserInfoUtil.setAllDis(String.valueOf(object.optLong("allDis")));

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    initViewDatas();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }
            });
        }
    }
    private Uri imageUri;
    private File outputImage;
    private void initUserInfo() {
        userBinding.userName.setText(SpUserInfoUtil.getUserName());
        userBinding.userHeight.setText(SpUserInfoUtil.getUserHeight());
        userBinding.userWeight.setText(SpUserInfoUtil.getUserWeight());
        outputImage = new File(getActivity().getExternalCacheDir(),"output_image.jpg");
        if (Build.VERSION.SDK_INT >= 24){
            imageUri = FileProvider.getUriForFile(getContext(),
                    "com.example.liaodh.keeprun.fileprovider",outputImage);
        }else {
            imageUri = Uri.fromFile(outputImage);
        }
        userBinding.userImage.setImageURI(imageUri);
    }

    private void initViewDatas() {
        userBinding.weekTime.setText(SpUserInfoUtil.getWeekTime());
        userBinding.weekDis.setText(SpUserInfoUtil.getWeekDis());
        userBinding.allTime.setText(SpUserInfoUtil.getAllTime());
        userBinding.allDis.setText(SpUserInfoUtil.getAllDis());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_in:
                //设置用户信息页面
                if (getActivity() != null) {
                    Intent intent = new Intent(getActivity(), UserEditActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.review_data_in:
                if (getActivity() != null) {
                    Intent intent = new Intent(getActivity(), ReviewDataActivity.class);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initViewData();
    }
}
