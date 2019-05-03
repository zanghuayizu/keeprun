package com.example.liaodh.keeprun.util;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AssetsUtil {

    private static final String fileName = "citycode.json";
    public static String getCityCodeByCityNameFromJson(Context context,String cityName) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            return handelJson(stringBuilder.toString(),cityName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String handelJson(String string, String cityName) {
        try {
            JSONArray cityCode = new JSONArray(string);
            for (int i = 0;i < cityCode.length();i ++){
                JSONObject city = cityCode.getJSONObject(i);
                if (city.optString("city_name").contains(cityName)
                        || cityName.contains(city.optString("city_name"))) {
                    return city.optString("city_code");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
