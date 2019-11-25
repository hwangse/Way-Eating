package com.example.way_eating.ui.status;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.way_eating.R;
import com.example.way_eating.activity.MainActivity;

public class StatusFragment extends Fragment {

    private StatusViewModel statusViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statusViewModel =
                ViewModelProviders.of(this).get(StatusViewModel.class);
        View root = inflater.inflate(R.layout.fragment_status, container, false);

        //text 넣기
        TextView restaurantName = (TextView) root.findViewById(R.id.restaurantName);
        TextView restaurantOrder = (TextView) root.findViewById(R.id.restaurantOrder);
        TextView restaurantTime = (TextView) root.findViewById(R.id.restaurantTime);

        restaurantName.setText(getResources().getString(R.string.restaurantName));
        restaurantOrder.setText("나의 순서 : " + getResources().getString(R.string.restaurantOrder) + "번째");
        restaurantTime.setText("예상 대기시간 : " + getResources().getString(R.string.restaurantTime) + "분");
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

}