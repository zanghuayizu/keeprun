package com.example.liaodh.keeprun.view.login;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.databinding.FragmentLoginBinding;
import com.example.liaodh.keeprun.util.HttpUtil;
import com.example.liaodh.keeprun.view.BaseDialogFragment;
import com.example.liaodh.keeprun.view.commod.CommonToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginFragment extends BaseDialogFragment
        implements View.OnClickListener, DialogInterface.OnKeyListener {

    private FragmentLoginBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return initView(inflater, container);
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        initListener();
        return mBinding.getRoot();
    }

    private void initListener() {
        mBinding.login.setOnClickListener(this);
        getDialog().setOnKeyListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                checkInput();
                break;
        }
    }

    private void checkInput() {
        if (getActivity() != null) {
            if (TextUtils.isEmpty(mBinding.userAccount.getText())
                    || TextUtils.isEmpty(mBinding.password.getText())) {
                CommonToast.showShortToast("请输入账户和密码");
            } else {
                String pattrn = "^(?![^a-zA-Z]+$)(?!\\D+$).{8,16}$";
                if (!isMatcherFinded(pattrn, mBinding.userAccount.getText())
                        || !isMatcherFinded(pattrn, mBinding.password.getText())) {
                    CommonToast.showShortToast("请输入8-16位的数字和字母");
                } else {
                    //判断账户是否已经存在，如果已经存在，则判断账户密码是否正确
                    //由服务器判断。规定返回数字
                    //202：用户存在，账户密码正确
                    //203：用户存在，账户密码错误
                    //302：账户不存在，注册成功，然后完善账户信息
                    //login(mBinding.userAccount.getText().toString(), mBinding.password.getText().toString());
                    registor();
                }
            }
        }
    }

    private void login(String account, String password) {
        String url = HttpUtil.baseUrl + "login?account=" + account + "&password=" + password;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                CommonToast.showShortToast("网络错误");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //服务器的返回值
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        int code = jsonObject.optInt("resCode");
                        switch (code) {
                            case 302:
                                //注册，完善账户信息
                                registor();
                                break;
                            case 202:
                                //登录成功
                                loginSecceed();
                                break;
                            case 203:
                                CommonToast.showShortToast("密码错误");
                                break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void loginSecceed() {
        CommonToast.showShortToast("登录成功");
        dismiss();
    }

    private void registor() {
        CommonToast.showShortToast("完善个人信息");
        if (getActivity() != null) {
            LoginDialogFirstFragment firstFragment = new LoginDialogFirstFragment();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            firstFragment.show(ft, getTag());
        }
        dismiss();
    }

    private boolean isMatcherFinded(String patternStr, CharSequence input) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP;
    }
}
