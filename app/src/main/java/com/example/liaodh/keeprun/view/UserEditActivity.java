package com.example.liaodh.keeprun.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.databinding.ActivityUserEditBinding;
import com.example.liaodh.keeprun.util.SpUserInfoUtil;

public class UserEditActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityUserEditBinding mBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_edit);
        mBinding.back.setOnClickListener(this);
        mBinding.save.setOnClickListener(this);
        setViewData();
    }

    private void setViewData() {
        if (SpUserInfoUtil.isUserLogin()){
            setUserImage();
            mBinding.userName.setText(SpUserInfoUtil.getUserName());
            if (SpUserInfoUtil.getIsBoy()) {
                mBinding.userSexBoy.toggle();
            } else {
                mBinding.userSexGirl.toggle();
            }
            mBinding.userHeight.setText(SpUserInfoUtil.getUserHeight());
            mBinding.userWeight.setText(SpUserInfoUtil.getUserWeight());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.save:
                saveUserImage();
                SpUserInfoUtil.setUserName(mBinding.userName.getText().toString());
                SpUserInfoUtil.setIsBoy(mBinding.userSexBoy.isChecked());
                SpUserInfoUtil.setUserHeight(mBinding.userHeight.getText().toString());
                SpUserInfoUtil.setUserWeight(mBinding.userWeight.getText().toString());
                Intent intent = new Intent(this,MainActivity.class);
                long id = 2;
                intent.putExtra("tag",id);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void setUserImage() {

    }

    private void saveUserImage() {

    }
}
