package com.example.way_eating.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.way_eating.R;
import com.example.way_eating.activity.InfoActivity;
import com.example.way_eating.activity.SearchRestaurantActivity;


public class ListFragment extends Fragment {
    public static String parameterToSearchRestaurantActivity;
    ViewPager viewPager_nearby;
    ViewPager viewPager_suggest;
    LinearLayout sliderDotspanel_nearby;
    LinearLayout sliderDotspanel_suggest;
    private int dotscount_nearby;
    private int dotscount_suggest;
    private ImageView[] dots_nearby;
    private ImageView[] dots_suggest;
    private ListViewModel listViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listViewModel =
                ViewModelProviders.of(this).get(ListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_list, container, false);

        //text값 넣기
        TextView nearbyRestaurantName = (TextView) root.findViewById(R.id.nearbyRestaurantName);
        TextView suggestRestaurantList = (TextView) root.findViewById(R.id.suggestRestaurantList);

        nearbyRestaurantName.setText(getResources().getString(R.string.nearbyRestaurantName));
        suggestRestaurantList.setText(getResources().getString(R.string.customerName) + "님께 추천하는 주변 맛집");

        //이미지뷰어용
        viewPager_nearby = (ViewPager) root.findViewById(R.id.viewPager_nearby);
        viewPager_suggest = (ViewPager) root.findViewById(R.id.viewPager_suggest);
        sliderDotspanel_nearby = (LinearLayout) root.findViewById(R.id.sliderDotspanel_nearby);
        sliderDotspanel_suggest = (LinearLayout) root.findViewById(R.id.sliderDotspanel_suggest);
        ViewPagerAdapterNearby viewPagerAdapterNearby = new ViewPagerAdapterNearby(getContext());
        ViewPagerAdapterSuggest viewPagerAdapterSuggest = new ViewPagerAdapterSuggest(getContext());
        viewPager_nearby.setAdapter(viewPagerAdapterNearby);
        viewPager_suggest.setAdapter(viewPagerAdapterSuggest);
        dotscount_nearby = viewPagerAdapterNearby.getCount();
        dotscount_suggest = viewPagerAdapterSuggest.getCount();
        dots_nearby = new ImageView[dotscount_nearby];
        dots_suggest = new ImageView[dotscount_suggest];

        //nearby 사진
        for(int i = 0; i < dotscount_nearby; i++){

            dots_nearby[i] = new ImageView(getContext());
            dots_nearby[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel_nearby.addView(dots_nearby[i], params);
        }

        dots_nearby[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));

        viewPager_nearby.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount_nearby; i++){
                    dots_nearby[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));
                }

                dots_nearby[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //suggest 사진
        for(int i = 0; i < dotscount_suggest; i++){

            dots_suggest[i] = new ImageView(getContext());
            dots_suggest[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel_suggest.addView(dots_suggest[i], params);
        }

        dots_suggest[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));

        viewPager_suggest.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount_suggest; i++){
                    dots_suggest[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));
                }

                dots_suggest[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

/*
        viewPager_nearby.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), InfoActivity.class);
                startActivity(intent1);
            }
        });*/

        Button btnInfo = (Button) root.findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), InfoActivity.class);
                startActivity(intent1);
            }
        });

        //검색기능 by 현선
        SearchView sv = (SearchView) root.findViewById(R. id. listSearchRestaurant);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            //검색버튼을 눌렀을 때
            @Override
            public boolean onQueryTextSubmit(String query){
                parameterToSearchRestaurantActivity = query;
                Intent intent2 = new Intent(getActivity(), SearchRestaurantActivity.class);
                startActivity(intent2);
                return true;
            }

            //텍스트가 바뀔때마다 호출
            @Override
            public boolean onQueryTextChange(String newText){
                return true;
            }
        });

        return root;
    }
}


