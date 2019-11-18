package com.example.way_eating.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.way_eating.R;
import com.example.way_eating.data.JoinData;
import com.example.way_eating.data.JoinResponse;
import com.example.way_eating.network.RetrofitClient;
import com.example.way_eating.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends Activity {
    private ServiceApi service = RetrofitClient.getClient().create(ServiceApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
    }

    //서버에 보내는 요청 처리
    private void startJoin(JoinData data) {
        service.userJoin(data).enqueue(new Callback<JoinResponse>() {
            //통신이 성공했을 경우 호출되는 메소드. response 객체에 응답으로 돌려받은 데이터가 들어있음
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                JoinResponse result = response.body();
                Toast.makeText(JoinActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                //showProgress(false);

                if (result.getCode() == 200) {
                    finish();
                }
            }

            //통신이 실패했을 경우 호출되는 메소드
            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(JoinActivity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("회원가입 에러 발생", t.getMessage());
                //showProgress(false);
            }
        });
    }
}