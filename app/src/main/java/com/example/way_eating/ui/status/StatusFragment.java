package com.example.way_eating.ui.status;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.way_eating.R;
import com.example.way_eating.data.WaitingInfo;
import com.example.way_eating.network.RegisterWaiting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.way_eating.ui.home.HomeFragment.systemData;

public class StatusFragment extends Fragment {

    private StatusViewModel statusViewModel;
    private TextView myWaitingStatus;
    private TextView restaurantName;
    private TextView restaurantOrder;
    private TextView restaurantTime;

    private boolean isWaitingRegistered=false; // 현재 대기 등록중인 상태인지 확인하는 flag
    private Button btnCancelWaiting;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statusViewModel =
                ViewModelProviders.of(this).get(StatusViewModel.class);
        View root = inflater.inflate(R.layout.fragment_status, container, false);

        // 기본 waiting status text 설정
        myWaitingStatus=root.findViewById(R.id.waitingStatus);
        restaurantName =  root.findViewById(R.id.restaurantName);
        restaurantOrder = root.findViewById(R.id.restaurantOrder);
        restaurantTime = root.findViewById(R.id.restaurantTime);

        // 웨이팅 취소 버튼 설정, onClickListener 설정
        btnCancelWaiting=root.findViewById(R.id.btnCancelWaiting);
        btnCancelWaiting.setOnClickListener((View view)-> {

                try{
                    // 웨이팅 취소시 보낼 정보를 JSON 파일로 생성
                    JSONObject waitingInfo=new JSONObject();
                    waitingInfo.put("storeId",systemData.user.waitingInfo.getStoreId()); // 상점 아이디
                    waitingInfo.put("userId",systemData.user.getId()); // 유저 아이디
                    waitingInfo.put("name",systemData.user.getName()); // 유저 이름
                    waitingInfo.put("order",systemData.user.waitingInfo.getOrderInLine()); // 유저 순서 (대기 목록에서 취소할 때 필요함)
                    waitingInfo.put("flag",0); // 대기 등록인지 대기 취소인지 알려주는 flag (1일경우 대기 등록, 0일경우 대기 취소)

                    RegisterWaiting registerWaiting=new RegisterWaiting((String output)->{
                        try{
                            // 취소 요청에 대한 서버의 응답을 다시 parsing
                            JSONObject jsonObject=new JSONObject(output);
                            Integer code=jsonObject.getInt("code");
                            if(code==200){ // 대기 취소가 설공했다면
                                systemData.user.waitingInfo=null;
                                isWaitingRegistered=false;

                                // 대기 취소 완료 다이얼로그
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                alertDialogBuilder.setTitle("웨이팅 취소 완료");
                                alertDialogBuilder
                                        .setMessage("성공적으로 웨이팅이 취소되었습니다.")
                                        .setCancelable(false)
                                        .setPositiveButton("확인", (DialogInterface dialog, int id)-> {
                                            // 현재 프래그먼트 reload (대기 취소 정보를 반영하기 위해)
                                            FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                                            fragmentTransaction.detach(this).attach(this).commit();
                                        });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    },waitingInfo);
                    registerWaiting.execute();
                }catch(JSONException e){
                    e.printStackTrace();
                }
            });

        // 대기 현황 정보를 설정한다.
        if(systemData.user.isAlreadyRegistered()) {
            isWaitingRegistered=true;
            WaitingInfo waitingInfo=systemData.user.waitingInfo;
            setWaitingStatus(waitingInfo.getStoreId(),waitingInfo.getTimeTotal(),waitingInfo.getOrderInLine());
        }else { // 대기 현황이 없다.
            isWaitingRegistered=false;
            myWaitingStatus.setVisibility(View.VISIBLE);
            restaurantName.setVisibility(View.INVISIBLE);
            restaurantOrder.setVisibility(View.INVISIBLE);
            restaurantTime.setVisibility(View.INVISIBLE);
            btnCancelWaiting.setVisibility(View.INVISIBLE);
        }
        return root;
    }

    public void setWaitingStatus(Integer storeId,Integer time, Integer order){
        // 등록된 대기 등록 정보가 있을 때 대기 정보를 설정한다.
        myWaitingStatus.setVisibility(View.INVISIBLE);

        restaurantName.setText(systemData.stores.get(storeId).getName());
        restaurantOrder.setText("나의 순서 : "+order+"번 째");
        restaurantTime.setText("예상 대기 시간 : "+time+" 분");

        restaurantName.setVisibility(View.VISIBLE);
        restaurantOrder.setVisibility(View.VISIBLE);
        restaurantTime.setVisibility(View.VISIBLE);
        btnCancelWaiting.setVisibility(View.VISIBLE);
    }
}