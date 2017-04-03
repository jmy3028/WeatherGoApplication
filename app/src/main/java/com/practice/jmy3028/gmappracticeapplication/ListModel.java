package com.practice.jmy3028.gmappracticeapplication;

import com.practice.jmy3028.gmappracticeapplication.model2.Weather;

import java.util.List;

/**
 * Created by jmy3028 on 17. 3. 31.
 */

public class ListModel {

    private String mWeather;
    private double mTemp;
    private double mWindSpeed;
    private double mWindDir;
    private double mPressure;
    private double mHumidity;

    public ListModel(String mWeather, double mTemp,
                     double mWindSpeed, double mWindDir,
                     double mPressure, double mHumidity) {
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

    public double getmWindSpeed() {
        return mWindSpeed;
    }

    public void setmWindSpeed(double mWindSpeed) {
        this.mWindSpeed = mWindSpeed;
    }

    public double getmWindDir() {
        return mWindDir;
    }

    public void setmWindDir(double mWindDir) {
        this.mWindDir = mWindDir;
    }

    public double getmPressure() {
        return mPressure;
    }

    public void setmPressure(double mPressure) {
        this.mPressure = mPressure;
    }

    public double getmHumidity() {
        return mHumidity;
    }

    public void setmHumidity(double mHumidity) {
        this.mHumidity = mHumidity;
    }

    @Override
    public String toString() {
        return "ListModel{" +
                "mWeather='" + mWeather + '\'' +
                ", mTemp=" + mTemp +
                ", mWindSpeed=" + mWindSpeed +
                ", mWindDir=" + mWindDir +
                ", mPressure=" + mPressure +
                ", mHumidity=" + mHumidity +
                '}';
    }
}
