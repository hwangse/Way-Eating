package com.example.way_eating.ui.list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.way_eating.R;
import com.example.way_eating.data.Store;
import com.example.way_eating.activity.InfoActivity;

import java.util.ArrayList;

public class SearchListAdapter extends BaseAdapter {
    Context context;
    int layoutId;
    ArrayList<Store> storeArr;
    LayoutInflater Inflater;

    public SearchListAdapter(Context _context, int _layoutId, ArrayList<Store> _storeArr) {
        context = _context;
        layoutId = _layoutId;
        storeArr = _storeArr;
        Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return storeArr.size();
    }

    @Override
    public String getItem(int position) {
        return storeArr.get(position).name;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if (convertView == null) {
            convertView = Inflater.inflate(layoutId, parent, false);
        }
        //ImageView leftImg = (ImageView)convertView.findViewById(R.id.leftimage);
        //leftImg.setImageBitmap(storeArr.get(position).myImg);
        //DB 정보 추가해서 디버깅해봐야될듯 -> position?
        TextView searchRestaurantName = (TextView)convertView.findViewById(R.id.searchRestaurantName);
        searchRestaurantName.setText(storeArr.get(0).name);
        TextView searchRestaurantPhone = (TextView)convertView.findViewById(R.id.searchRestaurantPhone);
        searchRestaurantPhone.setText(storeArr.get(0).getPhoneNum());

        //'도꼭지' 검색하면 나오는 리스트에서 INFO 버튼
        Button btnInfo = (Button)convertView.findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, InfoActivity.class);
                intent1.putExtra("position", storeArr.get(0).getId() - 1);
                context.startActivity(intent1);
            }
        });


        return convertView;
    }

}
