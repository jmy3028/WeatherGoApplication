package com.practice.jmy3028.gmappracticeapplication;

/**
 * Created by jmy3028 on 17. 3. 31.
 */

public class ListModel {

    private String mWeather;
    private double mTemp;
    private String mWindSpeed;
    private float mWindDir;
    private String mPressure;
    private String mHumidity;

    public ListModel(String mWeather, double mTemp,
                     String mWindSpeed, float mWindDir,
                     String mPressure, String mHumidity) {
        this.mWeather = mWeather;
        this.mTemp = mTemp;
        this.mWindSpeed = mWindSpeed;
        this.mWindDir = mWindDir;
        this.mPressure = mPressure;
        this.mHumidity = mHumidity;
    }

    public String getmWeather() {
        return mWeather;
    }

    public void setmWeather(String mWeather) {
        this.mWeather = mWeather;
    }

    public double getmTemp() {
        return mTemp;
    }

    public void setmTemp(double mTemp) {
        this.mTemp = mTemp;
    }

    public String getmWindSpeed() {
        return mWindSpeed;
    }

    public void setmWindSpeed(String mWindSpeed) {
        this.mWindSpeed = mWindSpeed;
    }

    public float getmWindDir() {
        return mWindDir;
    }

    public void setmWindDir(float mWindDir) {
        this.mWindDir = mWindDir;
    }

    public String getmPressure() {
        return mPressure;
    }

    public void setmPressure(String mPressure) {
        this.mPressure = mPressure;
    }

    public String getmHumidity() {
        return mHumidity;
    }

    public void setmHumidity(String mHumidity) {
        this.mHumidity = mHumidity;
    }

    @Override
    public String toString() {
        return "ListModel{" +
                "mWeather='" + mWeather + '\'' +
                ", mTemp=" + mTemp +
                ", mWindSpeed='" + mWindSpeed + '\'' +
                ", mWindDir=" + mWindDir +
                ", mPressure='" + mPressure + '\'' +
                ", mHumidity='" + mHumidity + '\'' +
                '}';
    }
}
