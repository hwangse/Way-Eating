package com.example.way_eating.data;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("userEmail")
    String userEmail;

    @SerializedName("userPW")
    String userPW;

    public LoginData(String userEmail, String userPW) {
        this.userEmail = userEmail;
        this.userPW = userPW;
    }
}