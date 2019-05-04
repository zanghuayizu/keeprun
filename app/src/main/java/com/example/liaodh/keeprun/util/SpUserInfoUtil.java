package com.example.liaodh.keeprun.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.liaodh.keeprun.MyApplication;

public class SpUserInfoUtil {
    private static final String USER_INFO = "user_info";
    private static final SharedPreferences mSp = MyApplication.getInstance().getApplicationContext()
            .getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);

    /** 登录状态**/
    private static final String IS_LOGIN = "isLogin";

    public static boolean setUserIsLogin(boolean isLogin){
        return mSp.edit().putBoolean(IS_LOGIN,isLogin).commit();
    }

    public static boolean isUserLogin(){
        return mSp.getBoolean(IS_LOGIN,false);
    }

    /** 用户账号id**/
    private static final String USER_Id = "userId";

    public static boolean setUserId(String userName){
        return mSp.edit().putString(USER_Id,userName).commit();
    }

    public static String getUserId(){
        return mSp.getString(USER_Id,"");
    }

    /** 用户账号 name**/
    private static final String USER_NAME = "userName";

    public static boolean setUserName(String userName){
        return mSp.edit().putString(USER_NAME,userName).commit();
    }

    public static String getUserName(){
        return mSp.getString(USER_NAME,"");
    }

    private static final String USER_HEIGHT = "user_height";

    public static boolean setUserHeight(String userHeight){
        return mSp.edit().putString(USER_HEIGHT,userHeight).commit();
    }

    public static String getUserHeight(){
        return mSp.getString(USER_HEIGHT,"0.0");
    }

    private static final String USER_WEIGHT = "user_weight";

    public static boolean setUserWeight(String userWeight){
        return mSp.edit().putString(USER_WEIGHT,userWeight).commit();
    }

    public static String getUserWeight(){
        return mSp.getString(USER_WEIGHT,"0.0");
    }

    private static final String WEEK_TIME = "week_time";

    public static boolean setWeekTime(String time){
        return mSp.edit().putString(WEEK_TIME,time).commit();
    }

    public static String getWeekTime() {
        return mSp.getString(WEEK_TIME,"0");
    }

    private static final String ALL_TIME = "all_time";

    public static boolean setAllTime(String time){
        return mSp.edit().putString(ALL_TIME,time).commit();
    }

    public static String getAllTime() {
        return mSp.getString(ALL_TIME,"0");
    }

    private static final String ALL_DIS = "all_dis";

    public static boolean setAllDis(String time){
        return mSp.edit().putString(ALL_DIS,time).commit();
    }

    public static String getAllDis() {
        return mSp.getString(ALL_DIS,"0");
    }

    private static final String WEEK_DIS = "week_dis";

    public static boolean setWeekDis(String time){
        return mSp.edit().putString(WEEK_DIS,time).commit();
    }

    public static String getWeekDis() {
        return mSp.getString(WEEK_DIS,"0");
    }

}
