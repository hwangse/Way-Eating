package com.example.way_eating.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    private ServiceApi service;
    private EditText email;
    private EditText pw;
    private EditText name;
    private Button join;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        email = (EditText) findViewById(R.id.joinEmail);
        pw = (EditText) findViewById(R.id.joinPW);
        name = (EditText) findViewById(R.id.joinName);
        join = (Button) findViewById(R.id.joinJoin);
        progress = (ProgressBar) findViewById(R.id.joinProgress);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        //회원가입 버튼이 눌렸을 때
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptJoin();
            }
        });
    }

    //회원가입 정보 유효성 검사
    private void attemptJoin() {
        email.setError(null);
        pw.setError(null);
        name.setError(null);

        String strName = name.getText().toString();
        String strEmail = email.getText().toString();
        String strPW = pw.getText().toString();
        boolean cancel = false;
        View focusView = null;

        //이름 유효성 검사
        if (strName.isEmpty()) {
            name.setError("이름을 입력해주세요.");
            focusView = name;
            cancel = true;
        }

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
        else {  //오류가 없는 경우 회원가입
            startJoin(new JoinData(strName, strEmail, strPW));
            showProgress(true);
        }
    }

    //유효한 정보에 대해 서버에 회원가입 요청을 보냄
    private void startJoin(JoinData data) {
        service.userJoin(data).enqueue(new Callback<JoinResponse>() {
                                                                                         //통신이 성공했을 경우 호출되는 메소드. response 객체에 응답으로 돌려받은 데이터가 들어있음
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                JoinResponse result = response.body();
                //Toast.makeText(JoinActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);

                //if (result.getCode() == 200) {
                Toast.makeText(JoinActivity.this, "회원가입 완료", Toast.LENGTH_SHORT).show();
                //finish();
                //}
            }

            //통신이 실패했을 경우 호출되는 메소드
            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(JoinActivity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("회원가입 에러 발생", t.getMessage());
                showProgress(false);
            }
        });
    }

    //progress bar 설정
    private void showProgress(boolean show) {
        progress.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}