package com.example.liaodh.keeprun.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.liaodh.keeprun.R;
import com.example.liaodh.keeprun.util.SpUserInfoUtil;
import com.example.liaodh.keeprun.view.login.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
    }

    private void initView() {
        if (!SpUserInfoUtil.isUserLogin()) {
            LoginFragment loginFragment = new LoginFragment();
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            loginFragment.show(ft, TAG);
        }else {
            Intent intent = new Intent(this,MainActivity.class);
            long id = 1;
            intent.putExtra("tag",id);
            startActivity(intent);
            finish();
        }
    }
}
