package com.example.way_eating.data;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("userID")
    private Integer userID;

    @SerializedName("userName")
    private String userName;

    @SerializedName("userSex")
    private String userSex;

    @SerializedName("userAge")
    private Integer userAge;

    @SerializedName("userPhone")
    private String userPhone;

    @SerializedName("userEmail")
    private String userEmail;

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public Integer getUserID() {
        return this.userID;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getUserSex() {
        return this.userSex;
    }

    public Integer getUserAge() {
        return this.userAge;
    }

    public String getUserPhone() {
        return this.userPhone;
    }

    public String getUserEmail() {
        return this.userEmail;
    }
}