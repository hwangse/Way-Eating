package com.example.way_eating.event;

import android.app.Activity;
import android.widget.Toast;

public class BackPressCloseHandler {
    private long pressedTime = 0;

    private Activity activity;

    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        //back 키를 누른지 1초가 지나지 않았다면
        if (System.currentTimeMillis() > pressedTime + 1000) {
            pressedTime = System.currentTimeMillis();
            Toast.makeText(activity, "뒤로 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
        //back 키를 누르고 1초가 지났다면
        else {
            activity.finish();
        }
    }
}
