package com.example.way_eating.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.way_eating.R;
import com.example.way_eating.data.LoginData;
import com.example.way_eating.data.LoginResponse;
import com.example.way_eating.event.BackPressCloseHandler;
import com.example.way_eating.network.RetrofitClient;
import com.example.way_eating.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends Activity {

    private BackPressCloseHandler backPressCloseHandler;
    private EditText email;
    private EditText pw;
    private Button login;
    private Button join;
    private ProgressBar progress;
    private ServiceApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        backPressCloseHandler = new BackPressCloseHandler(this);
        email = (EditText) findViewById(R.id.loginEmail);
        pw = (EditText) findViewById(R.id.loginPW);
        login = (Button) findViewById(R.id.loginLogin);
        join = (Button) findViewById(R.id.loginJoin);
        progress = (ProgressBar) findViewById(R.id.loginProgress);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        //로그인 또는 회원가입 버튼 눌렸을 때의 이벤트 처리
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.loginLogin: //로그인
                        attemptLogin();
                        break;
                    case R.id.loginJoin:  //회원가입
                        Intent intent = new Intent(LogInActivity.this, JoinActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };
        login.setOnClickListener(listener);
        join.setOnClickListener(listener);
    }

    //뒤로 버튼 눌렸을 때 이벤트 처리
    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    //로그인 정보 유효성 검사
    private void attemptLogin() {
        email.setError(null);
        pw.setError(null);

        String strEmail = email.getText().toString();
        String strPW = pw.getText().toString();
        boolean cancel = false;
        View focusView = null;

        //패스워드 유효성 검사
        if (strPW.isEmpty()) {
            pw.setError("비밀번호를 입력해주세요.");
            focusView = pw;
            cancel = true;
        }
        else if (strPW.length() < 6) {
            pw.setError("6자 이상의 비밀번호를 입력해주세요.");
            focusView = pw;
            cancel = true;
        }

        //아이디 유효성 검사
        if (strEmail.isEmpty()) {
            email.setError("이메일을 입력해주세요.");
            focusView = email;
            cancel = true;
        }
        else if (strEmail.length() < 6) {
            email.setError("6자 이상의 이메일을 입력해주세요.");
            focusView = email;
            cancel = true;
        }
        else if (!strEmail.contains("@")) {
            email.setError("올바른 이메일 양식이 아닙니다.");
            focusView = email;
            cancel = true;
        }

        if (cancel) //오류가 있는 경우 경고창
            focusView.requestFocus();
        else {  //오류가 없는 경우 로그인
            startLogin(new LoginData(strEmail, strPW));
            showProgress(true);
        }
    }

    //유효한 정보에 대해 서버에 로그인 요청을 보냄
    private void startLogin(LoginData data) {
        service.userLogin(data).enqueue(new Callback<LoginResponse>() {
            //통신이 성공했을 경우 호출되는 메소드. response 객체에 응답으로 돌려받은 데이터가 들어있음
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse result = response.body();
                //Toast.makeText(LogInActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);
                //if (result.getCode() == 200) {
                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                //}
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LogInActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
                showProgress(false);
            }
        });
    }

    private void showProgress(boolean show) {
        progress.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}