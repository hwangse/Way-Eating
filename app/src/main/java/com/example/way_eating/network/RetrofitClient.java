package com.example.way_eating.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final static String BASE_URL = "http://ec2-15-164-226-100.ap-northeast-2.compute.amazonaws.com:3000";    //서버 public dns
    private static Retrofit retrofit = null;

    private RetrofitClient() {
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // 요청을 보낼 BASE_URL 설정
                    .addConverterFactory(GsonConverterFactory.create()) // JSON 파싱을 위한 GsonConverterFactory를 추가한다.
                    .build();
        }

        return retrofit;
    }
}