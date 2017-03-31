
package com.practice.jmy3028.gmappracticeapplication.model2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Wind implements Serializable{

    @SerializedName("speed")
    @Expose
    private double speed;
    @SerializedName("deg")
    @Expose
    private double deg;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDeg() {
        return deg;
    }

    public void setDeg(double deg) {
        this.deg = deg;
    }

}
