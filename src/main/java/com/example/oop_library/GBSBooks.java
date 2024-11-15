package com.example.oop_library;

import com.google.gson.annotations.SerializedName;

public class GBSBooks {
    @SerializedName("volumeInfo")
    private Books volumeInfo;

    public Books getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(Books volumeInfo) {
        this.volumeInfo = volumeInfo;
    }
}