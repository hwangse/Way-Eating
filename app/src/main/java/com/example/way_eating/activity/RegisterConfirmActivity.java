package com.example.way_eating.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.way_eating.R;
import com.example.way_eating.network.RegisterWaiting;
import com.example.way_eating.ui.status.StatusFragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import static com.example.way_eating.ui.home.HomeFragment.systemData;

public class RegisterConfirmActivity extends Activity {
    private TextView name;
    private TextView numOfPeopleTxt;
    private TextView menuSelected;
    private TextView timeRemain;
    private int timeExpected=15;

    private String numOfPeople;
    private String menus;
    private ArrayList<String> menuArr;
    private Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_confirm);


        // 이전 activity에서 음식점 정보(인덱스) 받아오기
        Intent intent2 = getIntent();
        position = intent2.getIntExtra("position", 0);

        // 앞선 RegisterInfo Activity로 부터 대기 인원 및 메뉴 정보 받아오기
        Intent intent=getIntent();
        numOfPeople=intent.getStringExtra("numOfPeople");
        menus=intent.getStringExtra("menu");
        menuArr=intent.getStringArrayListExtra("menuArr");

        name=findViewById(R.id.name);
        numOfPeopleTxt=findViewById(R.id.numOfPeople);
        menuSelected=findViewById(R.id.menuSelected);
        timeRemain=findViewById(R.id.timeRemain);

        name.setText("이름 : "+ systemData.user.getName());
        numOfPeopleTxt.setText("인원수 : "+numOfPeople);
        menuSelected.setText(menus);
        ///////// 임시로 대기 시간 설정
        timeRemain.setText("예상 대기 시간 : "+timeExpected+"분");
    }

    // 대기 등록 취소
    public void onCancel(View v){
        //액티비티(팝업) 닫기
        finish();
    }
    // 대기 등록 확인 (확정)
    public void onConfirm(View v){
        // 등록된 대기 정보가 있는지 확인, 있다면 생성하면 안됨!!
        if(systemData.user.isAlreadyRegistered()){
            // 경고 다이얼로그
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            // 제목셋팅
            alertDialogBuilder.setTitle("경고");
            // AlertDialog 셋팅
            alertDialogBuilder
                    .setMessage("이미 등록된 웨이팅이 있습니다.")
                    .setCancelable(false)
                    .setPositiveButton("확인", (DialogInterface dialog, int id)-> {
                                    finish();
                                });
            // 다이얼로그 생성
            AlertDialog alertDialog = alertDialogBuilder.create();
            // 다이얼로그 보여주기
            alertDialog.show();
            return;
        }
        // 등록된 웨이팅이 없다면 서버에 대기 등록 요청하기
        try{ // POST 요청할 Json object 생성
            JSONObject waitingInfo=new JSONObject();
            waitingInfo.put("storeId",position);
            waitingInfo.put("userId",systemData.user.getId()); // 유저 아이디
            waitingInfo.put("name",systemData.user.getName()); // 유저 이름
            Integer people=-1;
            // 사람수 (문자열) 을 숫자로 바꿔서 전송
            if(numOfPeople.charAt(0)=='1') people=1;
            else if(numOfPeople.charAt(0)=='3')people=3;
            else people=5;
            final Integer ppl=people;
            waitingInfo.put("numOfPeople",people); // 대기 고객 명수
            waitingInfo.put("menu",new JSONArray(menuArr)); // 요청 메뉴
            //Toast.makeText(this,waitingInfo.toString(),Toast.LENGTH_SHORT).show();

            // 서버 통신하는 클래스 호출
            RegisterWaiting registerWaiting=new RegisterWaiting((String output)->{
                // 서버 통신 후 처리할 동작
                try{
                    // 서버에서 보낸 예측 대기시간, 대기 순서를 가져옴
                    JSONObject jsonObject=new JSONObject(output);
                    Integer time=jsonObject.getInt("time");
                    Integer rank=jsonObject.getInt("rank");
                    // user 클래스에 웨이팅 정보 생성 (식당 아이디, 주문 메뉴, 대기 인원수)
                    systemData.user.createWaitingInfo(position,menus,ppl,time,rank);

                    // 대기 등록 완료 다이얼로그
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    // 제목셋팅
                    alertDialogBuilder.setTitle("대기 등록 완료");
                    // AlertDialog 셋팅
                    alertDialogBuilder
                            .setMessage(systemData.stores.get(position).getName()+"에 "+rank+"번째로 웨이팅 등록이 완료되었습니다." +
                                    "\n대기 소요 시간 : "+time+"분")
                            .setCancelable(false)
                            .setPositiveButton("확인", (DialogInterface dialog, int id)-> {
                                finish();
                            });
                    // 다이얼로그 생성
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // 다이얼로그 보여주기
                    alertDialog.show();

//                    // 대기 상태를 나타내는 Status Fragment의 메소드 호출 -> 이거 시도하다가 망함. 걍 안할래
//                    StatusFragment sf=(StatusFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_status);
//                    sf.setWaitingStatus(position,time,rank);
                }catch(JSONException e){
                    e.printStackTrace();
                }


            },waitingInfo);
            registerWaiting.execute();

        }catch (JSONException e){
            e.printStackTrace();
        }

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
