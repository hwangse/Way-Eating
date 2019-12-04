package com.example.way_eating.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.way_eating.R;
import com.example.way_eating.event.ViewPagerAdapter;

import static com.example.way_eating.ui.home.HomeFragment.systemData;

public class InfoActivity extends Activity {
    ViewPager viewPager_selected;
    LinearLayout sliderDotspanel_selected;
    private int dotscount;
    private ImageView[] dots;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_info);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);   //ListFragment에서 선택된 페이지 받아옴
        //Toast.makeText(InfoActivity.this, Integer.toString(position), Toast.LENGTH_SHORT).show(); ///////////////

        TextView restaurantName = findViewById(R.id.restaurantName);
        TextView restaurantContactNumber =  findViewById(R.id.restaurantContactNumber);
        TextView restaurantLocation =  findViewById(R.id.restaurantLocation);
        TextView restaurantMenu =  findViewById(R.id.restaurantMenu);
        TextView restaurantSite = findViewById(R.id.restaurantSite);

        //음식점 상세 정보 설정
        restaurantName.setText(systemData.stores.get(position).getName());
        restaurantContactNumber.setText(systemData.stores.get(position).getPhoneNum());
        restaurantLocation.setText(systemData.stores.get(position).getAddress()); //store 클래스에 address가 없음
        restaurantMenu.setText(systemData.stores.get(position).menu.toString()); //store 클래스에 menu가 없음
        restaurantSite.setText(systemData.stores.get(position).getEmail()); //store 클래스에 email이 없음

        //이미지뷰어용
        viewPager_selected = (ViewPager) findViewById(R.id.viewPager_selected);
        sliderDotspanel_selected = (LinearLayout) findViewById(R.id.sliderDotspanel_selected);
        com.example.way_eating.event.ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager_selected.setAdapter(viewPagerAdapter);
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        // 대기 등록버튼 클릭시 -> 정보 입력 팝업을 띄워준다.
        btnRegister= findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener((View v)->{
            ////////////////////////////////이 부분 대기 등록하기 전에 유효성 검사할 수 있도록 수정하기(중복 대기 등록 여부)/////////////////////////////////
            //데이터 담아서 팝업(액티비티) 호출
            Intent intent2 = new Intent(this, RegisterInfoActivity.class);
            intent2.putExtra("position", position);
            startActivityForResult(intent2, 1);
        });

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel_selected.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager_selected.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}