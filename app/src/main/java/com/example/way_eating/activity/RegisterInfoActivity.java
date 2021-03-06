package com.example.way_eating.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.way_eating.R;
import com.example.way_eating.data.Store;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.example.way_eating.ui.home.HomeFragment.systemData;


public class RegisterInfoActivity extends Activity {
    TextView txtText;
    Spinner selectPeopleNum;
    // 메뉴선택을 위한 checkbox  -> 나중에 DB 메뉴와 연동시킬 예정, 아직은 아님 11/30 (세현)
    //CheckBox chk1,chk2,chk3;
    Store selected; // 선택된 음식점 (클릭된 음식점)
    List<CheckBox> menus = new ArrayList<>(); // 메뉴를 전시할 checkbox 배열
    int menuChecked=0;
    //int menuNum=selected.menu.length();
    //////////////
    int position; // 이전 activity 에서 음식점 index를 받아올 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_info);

        // 이전 activity에서 선택된 음식점 정보 받아오기
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        selected=systemData.stores.get(position);

        /////// 메뉴 선택을 위한 checkbox, 나중에 수정할 예정 by 세현
        // 음식점의 메뉴 개수에 따라 동적으로 메뉴 체크 박스를 생성한다.
        LinearLayout layout = findViewById(R.id.menuLayout);
        Iterator<String> iter = selected.menu.keys();
        while (iter.hasNext()) {
            CheckBox cb = new CheckBox(this);
            String key = iter.next();
            try {
                Integer value = (Integer)selected.menu.get(key);
                cb.setText(key+" : "+value+"원");
                cb.setTextColor(Color.BLACK);
                cb.setOnClickListener(this::onCheckboxClicked);
                menus.add(cb);
                layout.addView(cb);

            } catch (JSONException e) {
                // Something went wrong!
                e.printStackTrace();
            }
        }

        /////////////////////////////////////////////

        txtText = findViewById(R.id.numText);
        // 인원수 고르는 spinner 생성
        selectPeopleNum=findViewById(R.id.numSpinner);
        String[] str = getResources().getStringArray(R.array.spinnerArray);
        //spinner_item.xml과 str을 인자로 어댑터 생성.
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,R.layout.spinner_item,str);
       // adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectPeopleNum.setAdapter(adapter);

        //spinner 이벤트 리스너
        selectPeopleNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(selectPeopleNum.getSelectedItemPosition() > 0){
                    //선택된 항목 로그에 나타내기
                    //Log.v("알림",selectPeopleNum.getSelectedItem().toString()+ "is selected");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    // 메뉴 체크박스 클릭시
    public void onCheckboxClicked(View v){
        boolean checked=((CheckBox)v).isChecked();

        if(checked) {
            menuChecked++;
        }
        else menuChecked--;
    }
    //확인 버튼 클릭
    public void onConfirm(View v){
        // 확인전 인원수, 메뉴 체크 유효성 검사
        if(menuChecked>0) {
            // 대기 등록 확인 액티비티로 이동
            Intent intent = new Intent(this, RegisterConfirmActivity.class);
            // 이동전에 선택된 정보 값 (인원과 메뉴) 전달하기
            intent.putExtra("numOfPeople", selectPeopleNum.getSelectedItem().toString()); // 대기 인원수
            intent.putExtra("position",position);
            // 선택된 메뉴를 서버에 전송하기 위한 Json array
            ArrayList<String> tmpArr=new ArrayList<>();
            String menuTxt="";
            for(CheckBox menu:menus){
                if(menu.isChecked()) {
                    menuTxt += menu.getText().toString() + "\n";
                    tmpArr.add(menu.getText().toString().split(":")[0].substring(0,menu.getText().toString().split(":")[0].length()-1));
                }
            }
            intent.putExtra("menuArr",tmpArr);
            intent.putExtra("menu",menuTxt);
            intent.putExtra("position",position);
            // 선택된 메뉴
            startActivityForResult(intent, 1);
            finish();
            //setResult(RESULT_OK, intent);
        }else{ // 메뉴가 한개도 선택되지 않았을 때
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setPositiveButton("확인", (DialogInterface dialog, int which)-> {
                    dialog.dismiss();
            });
            alert.setMessage("메뉴를 최소 1가지 이상 선택해주세요.");
            AlertDialog err=alert.create();
            err.show();
        }
    }
    public void onCancel(View v){
        // 액티비티 팝업 닫기
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
