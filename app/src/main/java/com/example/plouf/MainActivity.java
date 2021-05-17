package com.example.plouf;

import android.os.Bundle;
import android.util.Log;

import com.example.plouf.data.AppDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

/*####################################################################################
 *형태 : Class
 * 모듈ID : MainActivity
 * 설명 : NavigationView Control
 * */
// TODO : 옆으로 슬라이드 했을때 메뉴 변경되게 만들기
public class MainActivity extends AppCompatActivity {


    public static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.CustomActionBarTheme);
        super.onCreate(savedInstanceState);

        db = AppDatabase.getInstance(getApplicationContext());
        Log.d("DB", "onCreate: db getInstance");


        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_calendar, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}