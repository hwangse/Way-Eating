package com.example.way_eating.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.way_eating.R;
import com.example.way_eating.data.User;
import com.example.way_eating.event.BackPressCloseHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    static public User userInMain;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        //LogInActivity로부터 user 데이터 수신 -> home fragment로 넘겨주기
        Intent intent = getIntent();
        userInMain = (User) intent.getSerializableExtra("user");
        //Toast.makeText(MainActivity.this, userInMain.getName() + " 님 안녕하세요!", Toast.LENGTH_SHORT).show();

        backPressCloseHandler = new BackPressCloseHandler(this);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_map, R.id.navigation_list, R.id.navigation_status, R.id.navigation_mypage, R.id.navigation_info)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }

    //뒤로 버튼 눌렸을 때 이벤트 처리
    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
