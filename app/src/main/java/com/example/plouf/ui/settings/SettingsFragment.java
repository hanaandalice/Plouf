package com.example.plouf.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.plouf.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;


/*####################################################################################
 *형태 : Class
 * 모듈ID : SettingsFragment
 * 설명 : Settings UI
 * SharedPreferences 값 저장 요청(컵 용량, 몸무게, 잠금 설정 여부)
 * SharedPrefeences 저장할 값 입력받음
 * */

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    Context context;
    EditText et_waterCup;
    EditText et_weight;
    TextView tv_settingResult;
    ChipGroup chipGroup;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        context = root.getContext();


        et_waterCup = root.findViewById(R.id.et_waterCup);
        et_weight = root.findViewById(R.id.et_weight);
        tv_settingResult = root.findViewById(R.id.tv_settingResult);
        tv_settingResult.setVisibility(View.INVISIBLE);

        chipGroup = root.findViewById(R.id.chip_group);

        if(settingsViewModel.getCup(context) != null){
            et_waterCup.setText(settingsViewModel.getCup(context).toString());
            et_waterCup.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(et_waterCup.getText().toString().length() != 0) {
                        settingsViewModel.setCup(context, Integer.parseInt(et_waterCup.getText().toString()));
                        Integer waterNeed = settingsViewModel.getWaterNeed(context);
                        tv_settingResult.setText("권장 물 섭취량을 마시기 위해서는 하루 "+(waterNeed/Integer.parseInt(et_waterCup.getText().toString())+1)+"컵을 마셔야 합니다.");
                        tv_settingResult.setVisibility(View.VISIBLE);
                    } else {
                        tv_settingResult.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        } else {
            settingsViewModel.setCup(context,473);
            et_waterCup.setText(settingsViewModel.getCup(context).toString());
        }


       if(settingsViewModel.getWeight(context) != null){
           et_weight.setText(settingsViewModel.getWeight(context).toString());
           tv_settingResult.setVisibility(View.INVISIBLE);
           et_weight.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               }
               @Override
               public void onTextChanged(CharSequence s, int start, int before, int count) {
                   if(et_weight.getText().toString().length() != 0) {
                       Integer waterNeed = Integer.parseInt(et_weight.getText().toString());
                       waterNeed = waterNeed*30;   //wacher 사용 하기..
                       tv_settingResult.setText(et_weight.getText().toString()+"kg의 하루 권장 물 섭취량은 "+waterNeed.toString()+"ml 입니다.");
                       tv_settingResult.setVisibility(View.VISIBLE);
                       settingsViewModel.setWeight(context, Integer.parseInt(et_weight.getText().toString()));
                   } else {
                       tv_settingResult.setVisibility(View.INVISIBLE);
                   }
               }

               @Override
               public void afterTextChanged(Editable s) {
               }
           });

       } else {
           settingsViewModel.setWeight(context, 0);
           et_waterCup.setText(settingsViewModel.getWeight(context).toString());
       }

       chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(ChipGroup group, int checkedId) {
               Chip chip = group.findViewById(checkedId);
               Log.d("chip", "onCheckedChanged: chip"+chip.getText().toString());
               String selectedCup = chip.getText().toString();
               Integer waterNeed;
               switch (selectedCup) {
                   case "종이컵(소)" :
                       settingsViewModel.setCup(context, 180);
                       et_waterCup.setText(settingsViewModel.getCup(context).toString());
                       waterNeed = settingsViewModel.getWaterNeed(context);
                       tv_settingResult.setText("권장 물 섭취량을 마시기 위해서는 하루 "+(waterNeed/Integer.parseInt(et_waterCup.getText().toString())+1)+"컵을 마셔야 합니다.");
                       tv_settingResult.setVisibility(View.VISIBLE);
                       break;
                   case "종이컵(대)" :
                       settingsViewModel.setCup(context, 360);
                       et_waterCup.setText(settingsViewModel.getCup(context).toString());
                       waterNeed = settingsViewModel.getWaterNeed(context);
                       tv_settingResult.setText("권장 물 섭취량을 마시기 위해서는 하루 "+(waterNeed/Integer.parseInt(et_waterCup.getText().toString())+1)+"컵을 마셔야 합니다.");
                       tv_settingResult.setVisibility(View.VISIBLE);
                       break;
                   case "스몰 사이즈" :
                       settingsViewModel.setCup(context, 240);
                       et_waterCup.setText(settingsViewModel.getCup(context).toString());
                       waterNeed = settingsViewModel.getWaterNeed(context);
                       tv_settingResult.setText("권장 물 섭취량을 마시기 위해서는 하루 "+(waterNeed/Integer.parseInt(et_waterCup.getText().toString())+1)+"컵을 마셔야 합니다.");
                       tv_settingResult.setVisibility(View.VISIBLE);
                       break;
                   case "레귤러 사이즈" :
                       settingsViewModel.setCup(context, 300);
                       et_waterCup.setText(settingsViewModel.getCup(context).toString());
                       waterNeed = settingsViewModel.getWaterNeed(context);
                       tv_settingResult.setText("권장 물 섭취량을 마시기 위해서는 하루 "+(waterNeed/Integer.parseInt(et_waterCup.getText().toString())+1)+"컵을 마셔야 합니다.");
                       tv_settingResult.setVisibility(View.VISIBLE);
                       break;
                   case "톨 사이즈" :
                       settingsViewModel.setCup(context, 255);
                       et_waterCup.setText(settingsViewModel.getCup(context).toString());
                       waterNeed = settingsViewModel.getWaterNeed(context);
                       tv_settingResult.setText("권장 물 섭취량을 마시기 위해서는 하루 "+(waterNeed/Integer.parseInt(et_waterCup.getText().toString())+1)+"컵을 마셔야 합니다.");
                       tv_settingResult.setVisibility(View.VISIBLE);
                       break;
                   case "그란데 사이즈" :
                       settingsViewModel.setCup(context, 473);
                       et_waterCup.setText(settingsViewModel.getCup(context).toString());
                       waterNeed = settingsViewModel.getWaterNeed(context);
                       tv_settingResult.setText("권장 물 섭취량을 마시기 위해서는 하루 "+(waterNeed/Integer.parseInt(et_waterCup.getText().toString())+1)+"컵을 마셔야 합니다.");
                       tv_settingResult.setVisibility(View.VISIBLE);
                       break;
                   case "벤티 사이즈" :
                       settingsViewModel.setCup(context, 591);
                       et_waterCup.setText(settingsViewModel.getCup(context).toString());
                       waterNeed = settingsViewModel.getWaterNeed(context);
                       tv_settingResult.setText("권장 물 섭취량을 마시기 위해서는 하루 "+(waterNeed/Integer.parseInt(et_waterCup.getText().toString())+1)+"컵을 마셔야 합니다.");
                       tv_settingResult.setVisibility(View.VISIBLE);
                       break;
                   case "리터" :
                       settingsViewModel.setCup(context, 1000);
                       et_waterCup.setText(settingsViewModel.getCup(context).toString());
                       waterNeed = settingsViewModel.getWaterNeed(context);
                       tv_settingResult.setText("권장 물 섭취량을 마시기 위해서는 하루 "+(waterNeed/Integer.parseInt(et_waterCup.getText().toString())+1)+"컵을 마셔야 합니다.");
                       tv_settingResult.setVisibility(View.VISIBLE);
                       break;

               }

           }
       });

//       chipGroup.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//
//           }
//       });


        return root;
    }

    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : SettingsFragment
     * 반환값 : 없음
     * 설명 : SharedPreferences 값 저장 요청(컵 용량, 몸무게, 잠금 설정 여부)
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        settingsViewModel.setCup(context, Integer.parseInt(et_waterCup.getText().toString()));
        settingsViewModel.setWeight(context, Integer.parseInt(et_weight.getText().toString()));


    }



}