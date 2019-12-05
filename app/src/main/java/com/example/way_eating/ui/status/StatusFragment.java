package com.example.way_eating.ui.status;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
//import android.support.v4.app.Fragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.way_eating.R;
import com.example.way_eating.data.WaitingInfo;

import static com.example.way_eating.ui.home.HomeFragment.systemData;

public class StatusFragment extends Fragment {

    private StatusViewModel statusViewModel;
    private TextView myWaitingStatus;
    private TextView restaurantName;
    private TextView restaurantOrder;
    private TextView restaurantTime;

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
        // 대기 현황 정보를 설정한다.
        if(systemData.user.isAlreadyRegistered()) {
            WaitingInfo waitingInfo=systemData.user.waitingInfo;
            setWaitingStatus(waitingInfo.getStoreId(),waitingInfo.getTimeTotal(),waitingInfo.getOrderInLine());
        }else {
            myWaitingStatus.setVisibility(View.VISIBLE);
            restaurantName.setVisibility(View.INVISIBLE);
            restaurantOrder.setVisibility(View.INVISIBLE);
            restaurantTime.setVisibility(View.INVISIBLE);
        }

//        restaurantName.setText(getResources().getString(R.string.restaurantName));
//        restaurantOrder.setText("나의 순서 : " + getResources().getString(R.string.restaurantOrder) + "번째");
//        restaurantTime.setText("예상 대기시간 : " + getResources().getString(R.string.restaurantTime) + "분");
/*
        //info용 버튼 **연습용임
        Button btnInfo = (Button) root.findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.onFragmentChanged();
            }
        });
*/
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
    }
}