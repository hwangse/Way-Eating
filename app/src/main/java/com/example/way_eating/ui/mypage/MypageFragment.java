package com.example.way_eating.ui.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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

/*
        TextView mypageName = root.findViewById(R.id.mypage_name);
        TextView mypageId = root.findViewById(R.id.mypage_id);
        TextView mypageEmail = root.findViewById(R.id.mypage_email);
        TextView mypagePhone = root.findViewById(R.id.mypage_phone);

        mypageName.setText("김철수");
        mypageId.setText("chulsoo1");
        mypageEmail.setText("chulsoo1@naver.com");
        mypagePhone.setText("010-1234-5678");
*/
        //final TextView textView = root.findViewById(R.id.text_mypage);
        /*mypageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }
}