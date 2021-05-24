package com.example.plouf.ui.lock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.example.plouf.R;
import com.example.plouf.data.PreferencesManager;
import com.guardanis.applock.AppLock;
import com.guardanis.applock.activities.LockableAppCompatActivity;

public class LockActivity extends LockableAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        EditText et_password1 = findViewById(R.id.et_password1);
        EditText et_password2 = findViewById(R.id.et_password2);
        EditText et_password3 = findViewById(R.id.et_password3);
        EditText et_password4 = findViewById(R.id.et_password4);

        String pass = new String();
        pass = et_password1.getText().toString()+et_password2.getText().toString()+et_password3.getText().toString()+et_password4.getText().toString();

        PreferencesManager preferencesManager= new PreferencesManager();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case AppLock.REQUEST_CODE_LOCK_CREATION :
                if (requestCode == Activity.RESULT_OK){
                    Toast.makeText(this, "Lock Created", Toast.LENGTH_SHORT).show();
                }
        }
    }


}
