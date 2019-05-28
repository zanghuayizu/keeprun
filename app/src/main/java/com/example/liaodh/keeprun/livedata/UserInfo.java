package com.example.liaodh.keeprun.livedata;


public class UserInfo extends BaseLiveData {

    private int userId;

    private String userName;

    private String userSex;

    private int userHeight;

    private int userWeight;

    private int userStepLong;

    private int userStepNum;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public int getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(int userHeight) {
        this.userHeight = userHeight;
    }

    public int getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(int userWeight) {
        this.userWeight = userWeight;
    }

    public int getUserStepLong() {
        return userStepLong;
    }

    public void setUserStepLong(int userStepLong) {
        this.userStepLong = userStepLong;
    }

    public int getUserStepNum() {
        return userStepNum;
    }

    public void setUserStepNum(int userStepNum) {
        this.userStepNum = userStepNum;
    }
}
