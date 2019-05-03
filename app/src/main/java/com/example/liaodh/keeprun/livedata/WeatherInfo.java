package com.example.liaodh.keeprun.livedata;

public class WeatherInfo extends BaseLiveData {
    private String wendu;
    private String cityName;

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
