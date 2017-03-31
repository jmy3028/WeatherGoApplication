package com.practice.jmy3028.gmappracticeapplication;

/**
 * Created by jmy3028 on 17. 3. 31.
 */

public class DtModel {
    private double mDtdata;

    public DtModel(double mDtdata) {
        this.mDtdata = mDtdata;
    }

    public double getmDtdata() {
        return mDtdata;
    }

    public void setmDtdata(double mDtdata) {
        this.mDtdata = mDtdata;
    }

    @Override
    public String toString() {
        return "DtModel{" +
                "mDtdata=" + mDtdata +
                '}';
    }
}
