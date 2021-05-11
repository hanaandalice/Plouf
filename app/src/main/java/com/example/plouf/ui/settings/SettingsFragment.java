package com.example.plouf.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.plouf.R;

public class SettingsFragment extends Fragment {
    //TODO: SharedPreference 로  몸무게, 컵 무게, 잠금 설정 여부 저장 하는 부분 만들기

    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        final TextView textView = root.findViewById(R.id.tv_lockSetting);
        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        EditText et_waterCup = root.findViewById(R.id.et_waterCup);
        EditText et_weight = root.findViewById(R.id.et_weight);

        et_waterCup.setText(et_waterCup.getText().toString());
        et_weight.setText(et_waterCup.getText().toString());

        return root;
    }

}