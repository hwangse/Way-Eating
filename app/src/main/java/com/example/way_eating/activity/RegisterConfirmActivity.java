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
    private TextView numOfPeopleTxt;
    private TextView menuSelected;
    private TextView timeRemain;
    private int timeExpected=15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_confirm);

        // 앞선 RegisterInfo Activity로 부터 대기 인원 및 메뉴 정보 받아오기
        Intent intent=getIntent();
        String numOfPeople=intent.getStringExtra("numOfPeople");
        String menus=intent.getStringExtra("menu");

        name=findViewById(R.id.name);
        numOfPeopleTxt=findViewById(R.id.numOfPeople);
        menuSelected=findViewById(R.id.menuSelected);
        timeRemain=findViewById(R.id.timeRemain);

        name.setText("이름 : "+ HomeFragment.systemData.user.getName());
        numOfPeopleTxt.setText("인원수 : "+numOfPeople);
        menuSelected.setText(menus);
        ///////// 임시로 대기 시간 설정
        timeRemain.setText("예상 대기 시간 : "+timeExpected+"분");
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
