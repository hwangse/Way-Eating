package com.example.way_eating.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.way_eating.R;

public class LogInActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //로그인 또는 회원가입 버튼 눌렸을 때의 이벤트 처리
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        Button btnJoin = (Button) findViewById(R.id.btnJoin);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnLogin: //로그인
                        Toast.makeText(getApplicationContext(), "버튼", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.btnJoin:  //회원가입
                        Toast.makeText(getApplicationContext(), "회원가입", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(LogInActivity.this, JoinActivity.class);
                        startActivity(intent2);
                        break;
                }
            }
        };
        btnLogin.setOnClickListener(listener);
        btnJoin.setOnClickListener(listener);
    }
}