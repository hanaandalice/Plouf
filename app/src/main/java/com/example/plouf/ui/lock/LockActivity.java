package com.example.plouf.ui.lock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plouf.MainActivity;
import com.example.plouf.R;
import com.example.plouf.data.PreferencesManager;

import org.w3c.dom.Text;

public class LockActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        EditText et_password1 = findViewById(R.id.et_password1);
        EditText et_password2 = findViewById(R.id.et_password2);
        EditText et_password3 = findViewById(R.id.et_password3);
        EditText et_password4 = findViewById(R.id.et_password4);

        TextView tv_passInfo = findViewById(R.id.tv_passInfo);

        String[] pass = new String[4];
        StringBuilder stringBuilder = new StringBuilder();
        String passResult;

        //intent 값 받아서 패스워드 해제 모드, 설정 모드 설정.

        et_password1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pass[0] = et_password1.getText().toString();
                Log.d("DB", "onTextChanged: "+pass[0]);
                stringBuilder.append(pass[0]);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_password2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pass[1] = et_password2.getText().toString();
                Log.d("DB", "onTextChanged: "+pass[1]);
                stringBuilder.append(pass[1]);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_password3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pass[2] = et_password3.getText().toString();
                Log.d("DB", "onTextChanged: "+pass[2]);
                stringBuilder.append(pass[2]);

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_password4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pass[3] = et_password4.getText().toString();
                Log.d("DB", "onTextChanged: "+pass[3]);
                stringBuilder.append(pass[3]);
                Log.d("DB", "onTextChanged: "+stringBuilder);
                if("1234".equals(stringBuilder.toString())){
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    tv_passInfo.setText("패스워드가 일치하지 않습니다.");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch(requestCode) {
//            case AppLock.REQUEST_CODE_LOCK_CREATION :
//                if (requestCode == Activity.RESULT_OK){
//                    Toast.makeText(this, "Lock Created", Toast.LENGTH_SHORT).show();
//                }
//        }
//
//    }

    public void lockSetting() {
        //패스워드 설정.

    }



}
