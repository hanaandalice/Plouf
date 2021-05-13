package com.example.plouf.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.plouf.R;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    Context context;
    EditText et_waterCup;
    EditText et_weight;
    Switch switch_lock;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        final TextView textView = root.findViewById(R.id.tv_lockSetting);
        context = root.getContext();


        et_waterCup = root.findViewById(R.id.et_waterCup);
        et_weight = root.findViewById(R.id.et_weight);
        switch_lock = root.findViewById(R.id.switch_lock);

        switch_lock.setChecked(settingsViewModel.getLockSetting(context));



        if(settingsViewModel.getCup(context) != null){
            et_waterCup.setText(settingsViewModel.getCup(context).toString());
        } else {
            settingsViewModel.setCup(context,0);
            et_waterCup.setText(settingsViewModel.getCup(context).toString());
        }


       if(settingsViewModel.getWeight(context) != null){
           et_weight.setText(settingsViewModel.getWeight(context).toString());
       } else {
           settingsViewModel.setWeight(context, 0);
           et_waterCup.setText(settingsViewModel.getWeight(context).toString());
       }

       switch_lock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked) {
                   settingsViewModel.setLockSetting(context, isChecked);
               } else {
                   settingsViewModel.setLockSetting(context, isChecked);
               }
           }
       });


        return root;
    }

//물컵 세팅

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        settingsViewModel.setCup(context, Integer.parseInt(et_waterCup.getText().toString()));
        settingsViewModel.setWeight(context, Integer.parseInt(et_weight.getText().toString()));

    }
}