
package com.practice.jmy3028.gmappracticeapplication.model2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sys implements Serializable{

    @SerializedName("pod")
    @Expose
    private String pod;

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

}
