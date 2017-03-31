
package com.practice.jmy3028.gmappracticeapplication.model2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rain implements Serializable{

    @SerializedName("3h")
    @Expose
    private double _3h;

    public double get3h() {
        return _3h;
    }

    public void set3h(double _3h) {
        this._3h = _3h;
    }

}
