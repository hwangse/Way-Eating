package com.example.way_eating.ui;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/** 앱의 텍스트에 폰트를 적용하기 위한 커스텀 어플리케이션 클래스 */
public class ApplicationBase extends Application {
    @Override public void onCreate() {
        super.onCreate();
        // 폰트 정의
        Typekit.getInstance()
                // textStyle="normal"일 때 적용
                .addNormal(Typekit.createFromAsset(this, "uto_regular.ttf"))
                // textStyle="bold"일 때 적용
                .addBold(Typekit.createFromAsset(this, "uto_bold.ttf"))
                // app:font="custom1"일 때 적용
                .addCustom1(Typekit.createFromAsset(this, "uto_light.ttf"));
    }
}