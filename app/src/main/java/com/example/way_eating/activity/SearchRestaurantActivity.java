package com.example.way_eating.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.way_eating.R;
import com.example.way_eating.Store;
import com.example.way_eating.ui.list.ListFragment;
import com.example.way_eating.ui.list.SearchListAdapter;

import java.util.ArrayList;

import static com.example.way_eating.ui.home.HomeFragment.systemData;

public class SearchRestaurantActivity extends Activity {
    ListView list;
    ArrayList<Store> storeArr;
    SearchListAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list2);

        storeArr = new ArrayList<Store>();
        for(int i=0;i<systemData.stores.size();i++){
            if(systemData.stores.get(i).getName().contains(ListFragment.parameterToSearchRestaurantActivity)) {
                storeArr.add(new Store(systemData.stores.get(i).getId(),
                        systemData.stores.get(i).getName(),
                        systemData.stores.get(i).getType(),
                        systemData.stores.get(i).getEmail(),
                        systemData.stores.get(i).getPhoneNum(),
                        systemData.stores.get(i).waitTimeLunch(),
                        systemData.stores.get(i).waitTimeDinner()));
            }
        }

        list = findViewById(R.id.listSearchRestaurantView);
        if(storeArr.size() == 0){
            list.setVisibility(View.INVISIBLE);
            TextView tempTextView = findViewById(R.id.textView);
            tempTextView.setVisibility(View.VISIBLE);
        }
        else {
            TextView tempTextView = findViewById(R.id.textView);
            tempTextView.setVisibility(View.INVISIBLE);
            mAdapter = new SearchListAdapter(this, R.layout.searchlist_itemlist, storeArr);
            list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            list.setAdapter(mAdapter);
        }
    }
}

