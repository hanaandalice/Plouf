package com.example.plouf.ui.lock;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plouf.MainActivity;
import com.example.plouf.R;
import com.example.plouf.data.PreferencesManager;
import com.example.plouf.ui.settings.SettingsFragment;

public class LockSettingActivity extends AppCompatActivity {
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
        StringBuilder stringTemp = new StringBuilder();
        String temp = null;


        et_password1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pass[0] = et_password1.getText().toString();
                Log.d("DB", "onTextChanged: "+pass[0]);
                if(stringTemp.toString().length() < 5) {
                    stringTemp.append(pass[0]);
                } else {
                    stringBuilder.append(pass[0]);
                }
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
                if(stringTemp.toString().length() < 5) {
                    stringTemp.append(pass[1]);
                } else {
                    stringBuilder.append(pass[1]);
                }
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
                if(stringTemp.toString().length() < 5) {
                    stringTemp.append(pass[2]);
                } else {
                    stringBuilder.append(pass[2]);
                }

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
                if(stringTemp.toString().length() < 5) {
                    stringTemp.append(pass[3]);
                } else {
                    stringBuilder.append(pass[3]);
                }
                Log.d("DB", "onTextChanged: "+stringBuilder);
                if(stringTemp != null) {
                    if(stringBuilder != null && stringTemp.equals(stringBuilder.toString())){  //무한 루프 걸림.. 왜..??
                        PreferencesManager preferencesManager = new PreferencesManager();
                        preferencesManager.setPass(getBaseContext(), stringTemp.toString());
                        tv_passInfo.setText("패스워드가 설정되었습니다.");
                        Intent intent = new Intent(getBaseContext(), SettingsFragment.class);
                        startActivity(intent);
                        finish();
                    } else if(stringBuilder != null) {
                        tv_passInfo.setText("패스워드가 일치하지 않습니다.");
                        stringTemp.setLength(0);    //초기화
                        stringBuilder.setLength(0);

                        et_password1.setText(null);
                        et_password2.setText(null);
                        et_password3.setText(null);
                        et_password4.setText(null);
                    } else {
                        tv_passInfo.setText("한번 더 입력하세요.");
                        et_password1.setText(null);
                        et_password2.setText(null);
                        et_password3.setText(null);
                        et_password4.setText(null);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
