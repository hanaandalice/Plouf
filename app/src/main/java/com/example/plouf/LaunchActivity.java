package com.example.plouf;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plouf.data.AppDatabase;

//시작 액티비티

//백그라운드에서 디비 getInstance하고 오늘의 기록사항 디비에서 가져와서 데이터 HomeViewModel에 보내주기
public class LaunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppDatabase db;
        db = AppDatabase.getInstance(getApplicationContext());

//        Log.d(TAG, "onCreate: ");

//        try{
//            GetDbResource getDbResource = new GetDbResource(getApplicationContext());
//            getDbResource.execute();
//        }catch(Exception e){
//            e.printStackTrace();    //배포시 없애기
//        }
    }


//     static abstract class GetDbResource extends AsyncTask<void,void, void> {
//        AppDatabase db;
//        private Context mContext;
//
//        public GetDbResource (Context context){
//            mContext = context;
//        }
//
//
//         @Override
//         protected void doInBackground() {
//
//            try{
//                db = AppDatabase.getInstance(mContext);
//            }catch(Exception e){
//            }
//         }
//
//
//
//
//     }
}
