package com.example.way_eating.network;

import com.example.way_eating.data.JoinData;
import com.example.way_eating.data.JoinResponse;
import com.example.way_eating.data.LoginData;
import com.example.way_eating.data.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApi {
    @POST("/android_user_login")
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("/android_user_join")
    Call<JoinResponse> userJoin(@Body JoinData data);

}