package com.example.way_eating.ui.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.way_eating.R;

import java.util.ArrayList;
import java.util.List;

public class MypageFragment extends Fragment {
    //이미지 리스트 생성
    List<Integer> galleryID = new ArrayList<Integer>();

    private MypageViewModel mypageViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mypageViewModel =
                ViewModelProviders.of(this).get(MypageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mypage, container, false);

        //text값 넣기
        TextView customerName = (TextView) root.findViewById(R.id.customerName);
        TextView customerPhoneNumber = (TextView) root.findViewById(R.id.customerPhoneNumber);
        TextView customerEmailAddress  = (TextView) root.findViewById(R.id.customerEmailAddress);

        customerName.setText(getResources().getString(R.string.customerName));
        customerPhoneNumber.setText(getResources().getString(R.string.customerPhoneNumber));
        customerEmailAddress.setText(getResources().getString(R.string.customerEmailAddress));

        return root;
    }
}