package com.example.plouf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plouf.data.AppDatabase;
import com.example.plouf.data.PreferencesManager;
import com.example.plouf.ui.lock.LockActivity;
import com.guardanis.applock.AppLock;
import com.guardanis.applock.activities.LockCreationActivity;
import com.guardanis.applock.activities.UnlockActivity;
import com.guardanis.applock.dialogs.LockCreationDialogBuilder;
import com.guardanis.applock.dialogs.UnlockDialogBuilder;

import static com.guardanis.applock.AppLock.REQUEST_CODE_LOCK_CREATION;
import static com.guardanis.applock.AppLock.REQUEST_CODE_UNLOCK;


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
        Intent intentLock = new Intent(this, LockActivity.class);

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
            startActivity(intentLock);  //여기

            finish();
        } else {
            startActivity(intentMain);
            finish();
        }


        //TODO :  잠금 화면으로 갈 것인지 MainActivity로 갈것인지 분기 수행

        //TODO : Lock xml 따로 없이 걍 예제 gif 처럼 되는 방법 없냐.. 귀찮음..
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case AppLock.REQUEST_CODE_UNLOCK :
                if(resultCode == Activity.RESULT_OK){
                    clearLocks();
                }
        }

    }

    public void dialogFlowClicked(View view) {
        if(!AppLock.isEnrolled(this)) {
            showActivityCreateLockFlow();
            return;
        }
        showActivityUnLockFlow();
    }

    private void showActivityCreateLockFlow() {
        Intent intent = new Intent(this, LockCreationActivity.class).putExtra(UnlockActivity.INTENT_ALLOW_UNLOCKED_EXIT, false);
        startActivityForResult(intent, REQUEST_CODE_LOCK_CREATION);
    }

    private void showActivityUnLockFlow() {
        Intent intent = new Intent(this, LockActivity.class).putExtra(UnlockActivity.INTENT_ALLOW_UNLOCKED_EXIT, false);
        startActivityForResult(intent, REQUEST_CODE_UNLOCK);
    }

    public void lockedActivityClicked(View view) {
        Intent intent = new Intent(this, LockActivity.class);
        startActivity(intent);
    }


    private void clearLocks() {
        showIndiactorMessate("Unlock Success!");
        AppLock.getInstance(getApplicationContext()).invalidateEnrollments();
    }

    private void showIndiactorMessate(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
