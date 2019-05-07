package com.example.liaodh.keeprun.view;


import android.view.Gravity;
import android.widget.Toast;

import com.example.liaodh.keeprun.MyApplication;

public class CommonToast {

    public static Toast mLongToast;
    public static Toast mShortToast;

    public static void showLongToast(String content) {
        if (mLongToast == null) {
            mLongToast = Toast.makeText(MyApplication.getInstance().getApplicationContext(), content, Toast.LENGTH_LONG);
            mLongToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        } else {
            mLongToast.setText(content);
            mLongToast.setDuration(Toast.LENGTH_LONG);
        }
        mLongToast.show();
    }

    public static void showShortToast(String content) {
        if (mShortToast == null) {
            mShortToast = Toast.makeText(MyApplication.getInstance().getApplicationContext(), content, Toast.LENGTH_SHORT);
            mShortToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        } else {
            mShortToast.setText(content);
            mShortToast.setDuration(Toast.LENGTH_SHORT);
        }
        mShortToast.show();
    }
}
