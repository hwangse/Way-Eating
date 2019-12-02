package com.example.way_eating.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.way_eating.R;
import com.example.way_eating.ui.home.HomeFragment;

public class RegisterConfirmActivity extends Activity {
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_confirm);

        name=findViewById(R.id.name);
        name.setText("이름 : "+ HomeFragment.systemData.user.getName());
        //데이터 가져오기
//        Intent intent = getIntent();
//        String data = intent.getStringExtra("data");
    }

    //확인 버튼 클릭
    public void mOnClose(View v){


        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

}
