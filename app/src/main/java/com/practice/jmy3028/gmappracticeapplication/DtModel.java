package com.practice.jmy3028.gmappracticeapplication;

/**
 * Created by jmy3028 on 17. 3. 31.
 */

public class DtModel {
    private String mDtdata;

    public DtModel(String mDtdata) {
        this.mDtdata = mDtdata;
    }

    public String getmDtdata() {
        return mDtdata;
    }

    public void setmDtdata(String mDtdata) {
        this.mDtdata = mDtdata;
    }

    @Override
    public String toString() {
        return "DtModel{" +
                "mDtdata='" + mDtdata + '\'' +
                '}';
    }
}
