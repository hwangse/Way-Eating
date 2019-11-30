package com.example.way_eating.data;

import com.google.gson.annotations.SerializedName;

public class JoinData {
    @SerializedName("userName")
    private String userName;

    @SerializedName("userEmail")
    private String userEmail;

    @SerializedName("userPW")
    private String userPW;

    @SerializedName("userAge")
    private Integer userAge;

    @SerializedName("userSex")
    private String userSex;

    @SerializedName("userPhone")
    private String userPhone;

    public JoinData(String userName, String userEmail, String userPW, Integer userAge, String userSex, String userPhone) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPW = userPW;
        this.userAge = userAge;
        this.userSex = userSex;
        this.userPhone  = userPhone;
    }
}