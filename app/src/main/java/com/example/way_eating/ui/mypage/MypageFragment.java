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

import static com.example.way_eating.ui.home.HomeFragment.systemData;


public class MypageFragment extends Fragment {
    //이미지 리스트 생성
    List<Integer> galleryID = new ArrayList<Integer>();

    private MypageViewModel mypageViewModel;
    //현선 추가
    public TextView customerNameBig = null;
    public TextView customerName = null;
    public TextView customerPhoneNumber = null;
    public TextView customerEmail = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mypageViewModel =
                ViewModelProviders.of(this).get(MypageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mypage, container, false);

        //text값 넣기 - 기본 설정 by 지안
        customerNameBig = (TextView)root.findViewById(R.id.customerNameBig);
        customerName = (TextView) root.findViewById(R.id.customerName);
        customerPhoneNumber = (TextView) root.findViewById(R.id.customerPhoneNumber);
        customerEmail  = (TextView) root.findViewById(R.id.customerEmail);

        /*customerName.setText(getResources().getString(R.string.customerName));
        customerPhoneNumber.setText(getResources().getString(R.string.customerPhoneNumber));
        customerEmailAddress.setText(getResources().getString(R.string.customerEmailAddress));*/


        //systemData 대신 더미 클래스 만들어서 보여주기 by 현선
        /*if(customerNameBig == null) customerNameBig = (TextView)root.findViewById(R.id.customerNameBig);
        if(customerName == null) customerName = (TextView)root.findViewById(R.id.customerName);
        if(customerPhoneNumber == null) customerPhoneNumber = (TextView)root.findViewById(R.id.customerPhoneNumber);
        if(customerEmail == null) customerEmail = (TextView)root.findViewById(R.id.customerEmail);*/

        customerNameBig.setText(systemData.user.getName());
        customerName.setText(systemData.user.getName());
        customerPhoneNumber.setText(systemData.user.getPhoneNum());
        customerEmail.setText(systemData.user.getEmail());
        /*customerNameBig.setText(UserInfoTemp.name);
        customerName.setText(UserInfoTemp.name);
        customerPhoneNumber.setText(UserInfoTemp.phoneNum);
        customerEmail.setText(UserInfoTemp.email);*/
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


        return root;
    }
}

/*class UserInfoTemp{ //삭제할 예정
    public static Integer id = 0;
    public static String name = "김펭수";
    public static String sex = "M";
    public static Integer age = 10;
    public static String phoneNum = "01012345678";
    public static String email = "pengsu@sogang.ac.kr";
}*/