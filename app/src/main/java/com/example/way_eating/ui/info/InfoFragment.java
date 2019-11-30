package com.example.way_eating.ui.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.way_eating.R;
import com.example.way_eating.ui.info.InfoViewModel;
import com.example.way_eating.ui.info.ViewPagerAdapter;
/////////////**************************** 이 프래그먼트 안쓰이는듯???******************************/////////////////////////////
public class InfoFragment extends Fragment {
    ViewPager viewPager_selected;
    LinearLayout sliderDotspanel_selected;
    private int dotscount;
    private ImageView[] dots;
    private InfoViewModel listViewModel;
    private InfoViewModel infoViewModel;
    private Button btnRegister;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        infoViewModel =
                ViewModelProviders.of(this).get(InfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_info, container, false);

        //text값 넣기
        TextView restaurantName = (TextView) root.findViewById(R.id.restaurantName);
        TextView restaurantContactNumber = (TextView) root.findViewById(R.id.restaurantContactNumber);
        TextView restaurantLocation = (TextView) root.findViewById(R.id.restaurantLocation);
        TextView restaurantMenu = (TextView) root.findViewById(R.id.restaurantMenu);
        TextView restaurantSite = (TextView) root.findViewById(R.id.restaurantSite);

        restaurantName.setText(getResources().getString(R.string.restaurantName));
        restaurantContactNumber.setText(getResources().getString(R.string.restaurantContactNumber));
        restaurantLocation.setText(getResources().getString(R.string.restaurantLocation));
        restaurantMenu.setText(getResources().getString(R.string.restaurantMenu));
        restaurantSite.setText(getResources().getString(R.string.restaurantSite));

        //이미지뷰어용
        viewPager_selected = (ViewPager) root.findViewById(R.id.viewPager_selected);
        sliderDotspanel_selected = (LinearLayout) root.findViewById(R.id.sliderDotspanel_selected);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext());
        viewPager_selected.setAdapter(viewPagerAdapter);
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

//        // 대기 등록버튼 클릭시 -> 정보 입력 팝업을 띄워준다.
//        btnRegister= root.findViewById(R.id.btnRegister);
//        btnRegister.setOnClickListener((View v)->{
//            ////////////////////////////////이 부분 대기 등록하기 전에 유효성 검사할 수 있도록 수정하기(중복 대기 등록 여부)/////////////////////////////////
//            //데이터 담아서 팝업(액티비티) 호출
//            Intent intent = new Intent(getActivity(), RegisterInfoActivity.class);
//            intent.putExtra("data", "Test Popup");
//            startActivityForResult(intent, 1);
//            Toast.makeText(getActivity(),"whatalkjflkdjf",Toast.LENGTH_SHORT).show();
//        });

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel_selected.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));

        viewPager_selected.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return root;
    }
}