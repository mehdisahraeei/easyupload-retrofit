package com.mahdi.uploadretrofit.Model;

import com.google.gson.annotations.SerializedName;

public class model {

    @SerializedName("t1")
    private String t1;

    @SerializedName("upload")
    private String upload;


    public String getT1() {
        return t1;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }
}
