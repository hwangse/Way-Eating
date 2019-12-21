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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.way_eating.R;
import com.example.way_eating.activity.InfoActivity;
import com.example.way_eating.activity.SearchRestaurantActivity;

import static com.example.way_eating.ui.home.HomeFragment.systemData;


public class ListFragment extends Fragment {
    public static String parameterToSearchRestaurantActivity;
    ViewPager viewPagerSuggest;
    ViewPager viewPagerPrefer;
    LinearLayout sliderDotsPanelSuggest;
    LinearLayout sliderDotsPanelPrefer;
    private int dotsCountSuggest;
    private int dotsCountPrefer;
    private ImageView[] dotsSuggest;
    private ImageView[] dotsPrefer;
    private ListViewModel listViewModel;

    int selectedPosition = 0;
    //음식점 버튼들
    private Button suggest1, suggest2, suggest3, suggest4;
    private Button prefer1, prefer2, prefer3, prefer4;
    private Button layoutPrefer;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listViewModel =
                ViewModelProviders.of(this).get(ListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_list, container, false);

        TextView suggestRestaurantName = (TextView) root.findViewById(R.id.suggestRestaurantName);
        TextView preferRestaurantList = (TextView) root.findViewById(R.id.preferRestaurantList);

        //text값 넣기
        suggestRestaurantName.setText(systemData.user.getName() + " 님께 추천하는 맛집");
        preferRestaurantList.setText(systemData.user.getName() + " 님이 선호하는 맛집");

        //이미지뷰어용 변수 설정
        viewPagerSuggest = (ViewPager) root.findViewById(R.id.viewPager_suggest);
        viewPagerPrefer = (ViewPager) root.findViewById(R.id.viewPager_prefer);
        sliderDotsPanelSuggest = (LinearLayout) root.findViewById(R.id.sliderDotsPanel_suggest);
        sliderDotsPanelPrefer = (LinearLayout) root.findViewById(R.id.sliderDotsPanel_prefer);
        ViewPagerAdapterSuggest viewPagerAdapterSuggest = new ViewPagerAdapterSuggest(getContext());
        ViewPagerAdapterPrefer viewPagerAdapterPrefer = new ViewPagerAdapterPrefer(getContext());
        viewPagerSuggest.setAdapter(viewPagerAdapterSuggest);
        viewPagerPrefer.setAdapter(viewPagerAdapterPrefer);
        dotsCountSuggest = viewPagerAdapterSuggest.getCount();
        dotsCountPrefer = viewPagerAdapterPrefer.getCount();
        dotsSuggest = new ImageView[dotsCountSuggest];
        dotsPrefer = new ImageView[dotsCountPrefer];

        //버튼들 설정
        prefer1 = (Button) root.findViewById(R.id.btnPrefer1);
        prefer2 = (Button) root.findViewById(R.id.btnPrefer2);
        prefer3 = (Button) root.findViewById(R.id.btnPrefer3);
        prefer4 = (Button) root.findViewById(R.id.btnPrefer4);
        suggest1 = (Button) root.findViewById(R.id.btnSuggest1);
        suggest2 = (Button) root.findViewById(R.id.btnSuggest2);
        suggest3 = (Button) root.findViewById(R.id.btnSuggest3);
        suggest4 = (Button) root.findViewById(R.id.btnSuggest4);

        prefer1.setText(systemData.stores.get(0).getName());
        prefer2.setText(systemData.stores.get(1).getName());
        prefer3.setText(systemData.stores.get(2).getName());
        prefer4.setText(systemData.stores.get(3).getName());
        suggest1.setText(systemData.stores.get(0).getName());
        suggest2.setText(systemData.stores.get(1).getName());
        suggest3.setText(systemData.stores.get(2).getName());
        suggest4.setText(systemData.stores.get(3).getName());

        /*************suggest(추천하는 맛집) 부분*****************/
        for(int i = 0; i < dotsCountSuggest; i++){
            dotsSuggest[i] = new ImageView(getContext());
            dotsSuggest[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotsPanelSuggest.addView(dotsSuggest[i], params);
        }
        dotsSuggest[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));

        //페이지 변화가 생겼을 때 호출되는 리스너
        viewPagerSuggest.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //state : 페이지의 상태 (IDLE, DRAGGING, SETTLING)
            @Override
            public void onPageScrollStateChanged(int state) {
            }

            //스크롤 효과가 나는 동안 호출되는 부분
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //페이지의 변화가 생겼을 때 선택된 페이지를 알려줌 (스크롤이 아닌 선택 결과!)
            @Override
            public void onPageSelected(int position) {
                //update indicator
                for(int i = 0; i< dotsCountSuggest; i++){
                    dotsSuggest[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));
                }
                dotsSuggest[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));
            }
        });
        ///////////////////////


        /****************prefer(선호하는 맛집) 부분***************/
        for(int i = 0; i < dotsCountPrefer; i++){
            dotsPrefer[i] = new ImageView(getContext());
            dotsPrefer[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);

            sliderDotsPanelPrefer.addView(dotsPrefer[i], params);
        }
        dotsPrefer[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));

        //페이지 변화가 생겼을 때 호출되는 리스너
        viewPagerPrefer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //state : 페이지의 상태 (IDLE, DRAGGING, SETTLING)
            @Override
            public void onPageScrollStateChanged(int state) {
            }

            //스크롤 효과가 나는 동안 호출되는 부분
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //페이지의 변화가 생겼을 때 선택된 페이지를 알려줌 (스크롤이 아닌 선택 결과!)
            @Override
            public void onPageSelected(int position) {
                selectedPosition = position;

                //update indicator
                for(int i = 0; i< dotsCountPrefer; i++){
                    dotsPrefer[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));
                }
                dotsPrefer[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));
            }
        });
        //////////////////////////

        //음식점 버튼 클릭 이벤트 처리
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.preferLayout:
                        Toast.makeText(getActivity(), "prefer", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btnSuggest1:
                    case R.id.btnPrefer1:   //음식점1
                        Intent intent1 = new Intent(getActivity(), InfoActivity.class);
                        intent1.putExtra("position", 0);
                        startActivity(intent1);
                        break;
                    case R.id.btnSuggest2:
                    case R.id.btnPrefer2:   //음식점2
                        Intent intent2 = new Intent(getActivity(), InfoActivity.class);
                        intent2.putExtra("position", 1);
                        startActivity(intent2);
                        break;
                    case R.id.btnSuggest3:
                    case R.id.btnPrefer3:   //음식점3
                        Intent intent3 = new Intent(getActivity(), InfoActivity.class);
                        intent3.putExtra("position", 2);
                        startActivity(intent3);
                        break;
                    case R.id.btnSuggest4:
                    case R.id.btnPrefer4:   //음식점4
                        Intent intent4 = new Intent(getActivity(), InfoActivity.class);
                        intent4.putExtra("position", 3);
                        startActivity(intent4);
                        break;
                }
            }
        };
        suggest1.setOnClickListener(listener);
        suggest2.setOnClickListener(listener);
        suggest3.setOnClickListener(listener);
        suggest4.setOnClickListener(listener);
        prefer1.setOnClickListener(listener);
        prefer2.setOnClickListener(listener);
        prefer3.setOnClickListener(listener);
        prefer4.setOnClickListener(listener);

        /*viewPagerPrefer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                intent.putExtra("position", selectedPosition);
                startActivity(intent);
            }
        });*/

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


