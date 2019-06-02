package com.example.liaodh.keeprun.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.example.liaodh.keeprun.MyApplication;

public class SpUserInfoUtil {
    private static final String USER_INFO = "user_info";
    private static final SharedPreferences mSp = MyApplication.getInstance().getApplicationContext()
            .getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);

    /**
     * 登录状态
     **/
    private static final String IS_LOGIN = "isLogin";

    public static boolean setUserIsLogin(boolean isLogin) {
        return mSp.edit().putBoolean(IS_LOGIN, isLogin).commit();
    }

    public static boolean isUserLogin() {
        return mSp.getBoolean(IS_LOGIN, false);
    }

    /**
     * 用户账号id
     **/
    private static final String USER_Id = "userId";

    public static boolean setUserId(String userName) {
        return mSp.edit().putString(USER_Id, userName).commit();
    }

    public static String getUserId() {
        return mSp.getString(USER_Id, "");
    }

    /**
     * 用户账号 name
     **/
    private static final String USER_NAME = "userName";

    public static boolean setUserName(String userName) {
        return mSp.edit().putString(USER_NAME, userName).commit();
    }

    public static String getUserName() {
        return mSp.getString(USER_NAME, "奔跑吧");
    }

    private static final String USER_HEIGHT = "user_height";

    public static boolean setUserHeight(String userHeight) {
        return mSp.edit().putString(USER_HEIGHT, userHeight).commit();
    }

    public static String getUserHeight() {
        return mSp.getString(USER_HEIGHT, "");
    }

    private static final String USER_WEIGHT = "user_weight";

    public static boolean setUserWeight(String userWeight) {
        return mSp.edit().putString(USER_WEIGHT, userWeight).commit();
    }

    public static String getUserWeight() {
        return mSp.getString(USER_WEIGHT, "");
    }

    private static final String WEEK_TIME = "week_time";

    public static boolean setWeekTime(String time) {
        return mSp.edit().putString(WEEK_TIME, time).commit();
    }

    public static String getWeekTime() {
        return mSp.getString(WEEK_TIME, "0");
    }

    private static final String ALL_TIME = "all_time";

    public static boolean setAllTime(String time) {
        return mSp.edit().putString(ALL_TIME, time).commit();
    }

    public static String getAllTime() {
        return mSp.getString(ALL_TIME, "0");
    }

    private static final String ALL_DIS = "all_dis";

    public static boolean setAllDis(String time) {
        return mSp.edit().putString(ALL_DIS, time).commit();
    }

    public static String getAllDis() {
        return mSp.getString(ALL_DIS, "0");
    }

    private static final String WEEK_DIS = "week_dis";

    public static boolean setWeekDis(String time) {
        return mSp.edit().putString(WEEK_DIS, time).commit();
    }

    public static String getWeekDis() {
        return mSp.getString(WEEK_DIS, "0");
    }

    private static final String IS_BOY = "user_sex";

    public static boolean setIsBoy(boolean isBoy) {
        return mSp.edit().putBoolean(IS_BOY, isBoy).commit();
    }

    public static boolean getIsBoy() {
        return mSp.getBoolean(IS_BOY, false);
    }

    private static final String CITY_NAME = "city_name";

    public static boolean setCityName(String cityName) {
        return mSp.edit().putString(CITY_NAME, cityName).commit();
    }

    public static String getCityName() {
        return mSp.getString(CITY_NAME, "");
    }

    private static final String SUB_CITY_NAME = "sub_city_name";

    public static boolean setSubCityName(String subCityName) {
        return mSp.edit().putString(SUB_CITY_NAME, subCityName).commit();
    }

    public static String getSubCityName() {
        return mSp.getString(SUB_CITY_NAME, "");
    }

    private static final String CITY_CODE = "city_code";

    public static String getCityCode() {
        return mSp.getString(CITY_CODE, "");
    }

    public static boolean setCityCode(String cityCode) {
        return mSp.edit().putString(CITY_CODE, cityCode).commit();
    }

    private static final String WEATHER_TODAY = "weather_today";

    public static boolean setWeatherToday(String weatherToday) {
        return mSp.edit().putString(WEATHER_TODAY, weatherToday).commit();
    }

    public static String getWeatherToday() {
        return mSp.getString(WEATHER_TODAY, "0");
    }


    private static final String TODAY = "today";

    public static boolean setToday(String today) {
        return mSp.edit().putString(TODAY, today).commit();
    }

    public static String getToday() {
        return mSp.getString(TODAY, "");
    }

    private static final String USER_TODAY_STEP = "user_today_step";

    public static boolean setTodaySteps(float todaySteps) {
        return mSp.edit().putFloat(USER_TODAY_STEP, todaySteps).commit();
    }

    public static float getTodaySteps() {
        return mSp.getFloat(USER_TODAY_STEP, 0);
    }

    private static final String LAST_MOST_STEPS = "last_most_steps";

    public static boolean setLastMostSteps(float steps) {
        return mSp.edit().putFloat(LAST_MOST_STEPS, steps).commit();
    }

    public static float getLastMostSteps() {
        return mSp.getFloat(LAST_MOST_STEPS, 0);
    }

    private static final String MAX_STEP = "max_step";

    public static float getMaxSteps() {
        return mSp.getFloat(MAX_STEP, 10000);
    }

    public static boolean setMaxSteps(float steps) {
        return mSp.edit().putFloat(MAX_STEP, steps).commit();
    }

    private static final String LOCATION_LAT = "location_lat";

    public static boolean setLat(String lat) {
        return mSp.edit().putString(LOCATION_LAT, lat).commit();
    }

    public static String getLat() {
        return mSp.getString(LOCATION_LAT, "");
    }

    private static final String LOCATION_LNG = "location_lng";

    public static boolean setLng(String lng) {
        return mSp.edit().putString(LOCATION_LNG, lng).commit();
    }

    public static String getLng() {
        return mSp.getString(LOCATION_LNG, "");
    }

    private static final String RUN_DIS = "rundis";

    public static boolean setRunDis(float runDis) {
        return mSp.edit().putFloat(RUN_DIS, runDis).commit();
    }

    public static float getRunDis() {
        return mSp.getFloat(RUN_DIS, 0);
    }


    private static final String STEP_LONG = "step_long";

    public static boolean setStepLong(String stepLong) {
        return mSp.edit().putString(STEP_LONG, stepLong).commit();
    }

    public static String getStepLong() {
        return mSp.getString(STEP_LONG, "");
    }

    private static final String STEP_NUM = "ste_num";

    public static boolean setStepNum(String stepNum) {
        return mSp.edit().putString(STEP_NUM, stepNum).commit();
    }

    public static String getStepNum() {
        return mSp.getString(STEP_NUM, "");
    }


    private static final String RUN_TIMES = "run_times";

    public static boolean setTimes(String s) {
        return mSp.edit().putString(RUN_TIMES, s).commit();
    }

    public static String getTimes() {
        return mSp.getString(RUN_TIMES, "0");
    }

    private static final String KALULI = "kaluli";

    public static boolean setKaluli(String ka) {
        return mSp.edit().putString(KALULI, ka).commit();
    }
    public static String getKaluli(){
        return mSp.getString(KALULI,"0");
    }

    private static final String RUN_STEPS = "run_steps";
    public static void setRunSteps(String s) {
        mSp.edit().putString(RUN_STEPS,s).commit();
    }
    public static String getRunSteps(){
        return mSp.getString(RUN_STEPS,"");
    }
}
