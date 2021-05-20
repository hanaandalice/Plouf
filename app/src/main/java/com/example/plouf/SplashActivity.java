package com.example.plouf;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plouf.data.AppDatabase;
import com.example.plouf.data.PreferencesManager;

/*####################################################################################
 *형태 : Class
 * 모듈ID : LaunchActivity
 * 설명 : 잠금 화면으로 갈 것인지 MainActivity로 갈것인지 분기 수행
 * */
public class SplashActivity extends AppCompatActivity {
//    public static AppDatabase db;
    PreferencesManager preferencesManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imageView = (ImageView) findViewById(R.id.img_launch);

//        db = AppDatabase.getInstance(getApplicationContext());
        Intent intentMain = new Intent(this, MainActivity.class);
//
//        if(db != null){
//           try{
//               Thread.sleep(1000);
//           } catch (Exception e){
//               e.printStackTrace();
//           }
//            startActivity(intent);
//            finish();
//        }

        preferencesManager = new PreferencesManager();
        if(preferencesManager.getLockSetting(getApplicationContext()) == true){

        } else {
            startActivity(intentMain);
            finish();
        }

        //TODO :  잠금 화면으로 갈 것인지 MainActivity로 갈것인지 분기 수행
    }

}