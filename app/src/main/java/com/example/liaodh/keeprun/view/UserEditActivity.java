package com.example.liaodh.keeprun.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.databinding.ActivityUserEditBinding;
import com.example.liaodh.keeprun.util.SpUserInfoUtil;
import com.example.liaodh.keeprun.view.login.LoginDialogSecondFragment;
import com.example.liaodh.keeprun.view.login.SelectHeightWeightDialogFragment;

public class UserEditActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "UserEditActivity";
    private ActivityUserEditBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        initViewModel();
    }

    private void initViewModel() {
    }

    private void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_edit);
        mBinding.back.setOnClickListener(this);
        mBinding.save.setOnClickListener(this);
        mBinding.userHeight.setOnClickListener(this);
        mBinding.userWeight.setOnClickListener(this);
        mBinding.userImage.setOnClickListener(this);

        setViewData();
    }

    private void setViewData() {
        if (SpUserInfoUtil.isUserLogin()) {
            setUserImage();
            mBinding.userName.setText(SpUserInfoUtil.getUserName());
            if (SpUserInfoUtil.getIsBoy()) {
                mBinding.userSexBoy.toggle();
            } else {
                mBinding.userSexGirl.toggle();
            }
            mBinding.userHeight.setText(String.valueOf(SpUserInfoUtil.getUserHeight()));
            mBinding.userWeight.setText(String.valueOf(SpUserInfoUtil.getUserWeight()));
            mBinding.userStepLong.setText(String.valueOf(SpUserInfoUtil.getStepLong()));
            mBinding.userStepNum.setText(String.valueOf(SpUserInfoUtil.getStepNum()));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent = new Intent(this, MainActivity.class);
                long id = 2;
                intent.putExtra("tag", id);
                startActivity(intent);
                finish();
                break;
            case R.id.save:
                saveInLocal();
                break;
            case R.id.user_height:
                goSelect(1);
                break;
            case R.id.user_weight:
                goSelect(2);
                break;
        }
    }

    private void goSelect(final int i) {
        SelectHeightWeightDialogFragment fragment = new SelectHeightWeightDialogFragment();
        fragment.setSelectListener(i, new LoginDialogSecondFragment.setSelect() {
            @Override
            public void setSelect(int current) {
                if (i == 1) {
                    mBinding.userHeight.setText(String.valueOf(current + 100));
                } else if (i == 2) {
                    mBinding.userWeight.setText(String.valueOf(current + 30));
                }
            }
        });
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragment.show(ft, TAG);
    }

    private void saveInLocal() {
        saveUserImage();
        SpUserInfoUtil.setUserName(String.valueOf(mBinding.userName.getText()));
        SpUserInfoUtil.setIsBoy(mBinding.userSexBoy.isChecked());
        SpUserInfoUtil.setUserHeight(String.valueOf(mBinding.userHeight.getText()));
        SpUserInfoUtil.setUserWeight(String.valueOf(mBinding.userWeight.getText()));
        SpUserInfoUtil.setStepNum(String.valueOf(mBinding.userStepNum.getText()));
        SpUserInfoUtil.setStepLong(String.valueOf(mBinding.userStepLong.getText()));
        saveServer();
    }

    private void saveServer() {

    }


    private void setUserImage() {

    }

    private void saveUserImage() {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        long id = 2;
        intent.putExtra("tag", id);
        startActivity(intent);
        finish();
    }
}
