package com.example.way_eating.ui.mypage;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.way_eating.R;
import com.example.way_eating.network.GetUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MypageFragment extends Fragment {
    //이미지 리스트 생성
    List<Integer> galleryID = new ArrayList<Integer>();

    private MypageViewModel mypageViewModel;
    //현선 추가
    public TextView t = null;
    public TextView nameTextView2 = null;
    public TextView mobilePhoneView = null;
    public TextView emailView = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mypageViewModel =
                ViewModelProviders.of(this).get(MypageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mypage, container, false);

        if(t == null)  t = (TextView)root.findViewById(R.id.textView);
        if(nameTextView2 == null) nameTextView2 = (TextView)root.findViewById(R.id.nameTextView2);
        if(mobilePhoneView == null) mobilePhoneView = (TextView)root.findViewById(R.id.mobilePhoneView);
        if(emailView == null) emailView = (TextView)root.findViewById(R.id.emailView);

        if(TextUtils.isEmpty(t.getText())) {
            //Log.v("태그","t.getText() == null");
            GetUser getUser = new GetUser(new GetUser.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    try {
                        //Log.v("태그", "output:" + output);
                        JSONObject jsonObject = new JSONObject(output);
                        //Log.v("태그", "JSON 1");

                        JSONObject jsonObj = jsonObject.getJSONObject(0 + "");
                        //Log.v("태그", "JSON 2");
                        //Log.v("태그", "jsonObj:" + jsonObj);
                        String tmpName = jsonObj.getString("name");
                        //Log.v("태그", "tmpName:" + tmpName);

                        t.setText(tmpName);
                        nameTextView2.setText(tmpName);
                        mobilePhoneView.setText(tmpName); //폰번호로 바꿀 것
                        emailView.setText(tmpName); //이메일로 바꿀 것
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            getUser.execute();
        }

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