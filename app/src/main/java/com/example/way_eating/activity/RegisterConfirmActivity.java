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

public class RegisterConfirmActivity extends Activity {
    TextView txtText;
    Spinner selectPeopleNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_confirm);

        //UI 객체생성
        txtText = (TextView)findViewById(R.id.numText);
        // 인원수 고르는 spinner 생성
        selectPeopleNum=findViewById(R.id.numSpinner);

        String[] str = getResources().getStringArray(R.array.spinnerArray);
        //2번에서 생성한 spinner_item.xml과 str을 인자로 어댑터 생성.
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,R.layout.spinner_item,str);
        //adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectPeopleNum.setAdapter(adapter);

        //spinner 이벤트 리스너
        selectPeopleNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(selectPeopleNum.getSelectedItemPosition() > 0){
                    //선택된 항목
                    //Log.v("알림",selectPeopleNum.getSelectedItem().toString()+ "is selected");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //데이터 가져오기
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        txtText.setText(data);
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

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
