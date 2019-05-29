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
import com.example.liaodh.keeprun.util.HttpUtil;
import com.example.liaodh.keeprun.util.SpUserInfoUtil;
import com.example.liaodh.keeprun.view.commod.CommonToast;
import com.example.liaodh.keeprun.view.login.LoginDialogSecondFragment;
import com.example.liaodh.keeprun.view.login.SelectHeightWeightDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static org.litepal.LitePalApplication.getContext;

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
        mBinding.userStepLong.setOnClickListener(this);

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
            case R.id.user_step_long:
                goSelect(3);
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
                }else {
                    mBinding.userStepLong.setText(String.valueOf((current+5)*10));
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
        String url = HttpUtil.baseUrl + "saveUserInfo?"
                +"userId="+SpUserInfoUtil.getUserId()
                +"&userName="+SpUserInfoUtil.getUserName()
                +"&sex="+SpUserInfoUtil.getIsBoy()
                +"&height="+SpUserInfoUtil.getUserHeight()
                +"&weight="+SpUserInfoUtil.getUserWeight()
                +"&stepLong="+SpUserInfoUtil.getStepLong()
                +"&stepNum="+SpUserInfoUtil.getStepNum();
        HttpUtil.saveUserInfo(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CommonToast.showShortToast("网络错误");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            String code = jsonObject.optString("resCode");
                            if (code.equals("202")){
                                CommonToast.showShortToast("保存成功");
                            }else {
                                CommonToast.showShortToast("网络错误");
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
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
