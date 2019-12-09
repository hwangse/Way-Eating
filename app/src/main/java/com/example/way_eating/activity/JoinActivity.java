package com.example.way_eating.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private EditText name;
    private EditText email;
    private EditText pw;
    private EditText age;
    private RadioGroup sex;
    private RadioButton sexSelected;
    private EditText phone;
    private Button join;
    //private Button master;
    private ProgressBar progress;
    private ServiceApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        name = (EditText) findViewById(R.id.joinName);
        email = (EditText) findViewById(R.id.joinEmail);
        pw = (EditText) findViewById(R.id.joinPW);
        age = (EditText) findViewById(R.id.joinAge);
        sex = (RadioGroup) findViewById(R.id.joinSex);
        phone = (EditText) findViewById(R.id.joinPhone);
        join = (Button) findViewById(R.id.joinJoin);
        //master = (Button) findViewById(R.id.joinMaster);
        progress = (ProgressBar) findViewById(R.id.joinProgress);
        service = RetrofitClient.getClient().create(ServiceApi.class);

        //성별 받아오기
        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                sexSelected = (RadioButton) findViewById(id);
            }
        });

        //회원가입 버튼이 눌렸을 때
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptJoin();
            }
        });

        //마스터 버튼
        /*master.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJoin(new JoinData("admin", "admin@way", "123456", 24, "여", "01012345678"));
                showProgress(true);
            }
        });*/
    }

    //회원가입 양식 유효성 검사
    private void attemptJoin() {
        name.setError(null);
        email.setError(null);
        pw.setError(null);
        age.setError(null);
        phone.setError(null);

        //JoinData에 넘겨줄 파라미터
        String strName = name.getText().toString();
        String strEmail = email.getText().toString();
        String strPW = pw.getText().toString();
        String strAge = age.getText().toString();
        Integer intAge = null;
        String strSex = null;
        String strPhone = phone.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //휴대폰 번호 유효성 검사
        if (strPhone.isEmpty()) {
            phone.setError("휴대폰 번호를 입력해주세요.");
            focusView = phone;
            cancel = true;
        }
        //성별 유효성 검사
        if (sexSelected == null) {
            sexSelected = findViewById(R.id.joinMale);
            sexSelected.setError("성별을 입력해주세요.");
            focusView = sexSelected;
            cancel = true;
        }
        else {
            sexSelected.setError(null);
            strSex = sexSelected.getText().toString();
        }
        //나이 유효성 검사
        if (strAge.isEmpty()) {
            age.setError("나이를 입력해주세요.");
            focusView = age;
            cancel = true;
        }
        else
            intAge = Integer.parseInt(age.getText().toString());
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
        //이메일 유효성 검사
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
        //이름 유효성 검사
        if (strName.isEmpty()) {
            name.setError("이름을 입력해주세요.");
            focusView = name;
            cancel = true;
        }

        if (cancel) //오류가 있는 경우 경고창
            focusView.requestFocus();
        else {  //오류가 없는 경우 회원가입 시도
            startJoin(new JoinData(strName, strEmail, strPW, intAge, strSex, strPhone));
            showProgress(true);
        }
    }

    //유효한 양식에 대해 서버에 회원가입 요청을 보냄
    private void startJoin(JoinData data) {
        service.userJoin(data).enqueue(new Callback<JoinResponse>() {
            //통신이 성공했을 경우 호출되는 메소드. response 객체에 응답으로 돌려받은 데이터가 들어있음
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                JoinResponse result = response.body();

                Toast.makeText(JoinActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);
                if (result.getCode() == 200) {  //성공적으로 회원가입을 완료한 경우 액티비티 종료
                    finish();
                }
            }

            //통신이 실패했을 경우 호출되는 메소드
            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(JoinActivity.this, "서버에 접속할 수 없습니다", Toast.LENGTH_SHORT).show();
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