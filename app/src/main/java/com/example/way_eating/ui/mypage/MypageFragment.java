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

        //systemData 대신 더미 클래스 만들어서 보여주기
        if(t == null)  t = (TextView)root.findViewById(R.id.textView);
        if(nameTextView2 == null) nameTextView2 = (TextView)root.findViewById(R.id.nameTextView2);
        if(mobilePhoneView == null) mobilePhoneView = (TextView)root.findViewById(R.id.mobilePhoneView);
        if(emailView == null) emailView = (TextView)root.findViewById(R.id.emailView);


        t.setText(UserInfoTemp.name);
        nameTextView2.setText(UserInfoTemp.name);
        mobilePhoneView.setText(UserInfoTemp.phoneNum);
        emailView.setText(UserInfoTemp.email);
        /*
        if(HomeFragment.systemData != null){
            t.setText(HomeFragment.systemData.user.getName());
            nameTextView2.setText(HomeFragment.systemData.user.getName());
            mobilePhoneView.setText(HomeFragment.systemData.user.getPhoneNum());
            emailView.setText(HomeFragment.systemData.user.getEmail());
        }


        //현선 User 추가
        Log.v("태그","user");
        if(systemData.user == null) {
            Log.v("태그","user == null");
            GetUser getUser = new GetUser(new GetUser.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    Log.v("태그","output : "+output);
                    if (output != null) {
                        try {
                            Log.v("태그","output != null");
                            JSONObject jsonObject = new JSONObject(output);
                            //systemData.user=new User();
                            JSONObject jsonObj = jsonObject.getJSONObject(0 + "");
                            Integer tmpId = jsonObj.getInt("id");
                            String tmpName = jsonObj.getString("name");
                            String tmpSex = jsonObj.getString("type");
                            Integer tmpAge = jsonObj.getInt("email");
                            String tmpEmail = jsonObj.getString("phone");
                            String tmpPhone = jsonObj.getString("timeLunch");
                            //Integer tmpDinner=jsonObj.getInt("timeDinner");f
                            //systemData.stores.add(new Store(tmpId,tmpName,tmpType,tmpEmail,tmpPhone,tmpLunch,tmpDinner));
                            systemData.user = new User(tmpId, tmpName, tmpSex, tmpAge, tmpPhone, tmpEmail);
                            //tvData.setText(systemData.stores.get(i).name);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Error : 서버 접속 불가", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            getUser.execute();
        }
         */


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

class UserInfoTemp{ //삭제할 예정
    public static Integer id = 0;
    public static String name = "김펭수";
    public static String sex = "M";
    public static Integer age = 10;
    public static String phoneNum = "01012345678";
    public static String email = "pengsu@sogang.ac.kr";
}