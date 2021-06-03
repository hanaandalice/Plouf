package com.allbino.plouf.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.allbino.plouf.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;


/*####################################################################################
 *형태 : Class
 * 모듈ID : SettingsFragment
 * 설명 : Settings UI
 * SharedPreferences 값 저장 요청(컵 용량, 몸무게, 잠금 설정 여부)
 * SharedPreferences 저장할 값 입력받음
 * */

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    Context context;
    EditText et_waterCup;
    EditText et_weight;
    TextView tv_settingResult;
    ChipGroup chipGroup;
    Spinner spinner;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        context = root.getContext();


        et_waterCup = root.findViewById(R.id.et_waterCup);
        et_weight = root.findViewById(R.id.et_weight);
        tv_settingResult = root.findViewById(R.id.tv_settingResult);
        spinner = root.findViewById(R.id.spinner_drink);
        chipGroup = root.findViewById(R.id.chip_group);


        //spinner 세팅
        String[] drinks = {getString(R.string.drink_water_korean), getString(R.string.drink_coffee_korean), getString(R.string.drink_tea_korean)};
        ArrayAdapter mAdapter = new ArrayAdapter(root.getContext(), android.R.layout.simple_spinner_dropdown_item, drinks);
        spinner.setAdapter(mAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0 :
                        et_waterCup.setText(settingsViewModel.getWaterCup(context).toString());  //물 컵 용량보여주기
                        break;
                    case 1 :
                        et_waterCup.setText(settingsViewModel.getCoffeeCup(context).toString()); //커피컵 용량
                        break;
                    case 2 :
                        et_waterCup.setText(settingsViewModel.getTeaCup(context).toString()); //차 컵 용량

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (settingsViewModel.getWaterCup(context) != null){
            et_waterCup.setText(settingsViewModel.getWaterCup(context).toString());
            tv_settingResult.setVisibility(View.VISIBLE);

            if (settingsViewModel.getWaterNeed(context) != 0) {
                Integer waterNeed = settingsViewModel.getWaterNeed(context);
                tv_settingResult.setText("권장 물 섭취량을 마시기 위해서는 하루 "+(waterNeed/Integer.parseInt(et_waterCup.getText().toString())+1)+"컵을 마셔야 합니다.");
            } else {
                tv_settingResult.setVisibility(View.INVISIBLE);
            }


            et_waterCup.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (et_waterCup.getText().toString().length() != 0) {
                        if (spinner.getSelectedItemPosition() == 0) {
                            settingsViewModel.setWaterCup(context, Integer.parseInt(et_waterCup.getText().toString()));
                            Integer waterNeed = settingsViewModel.getWaterNeed(context);
                            Integer waterCup = settingsViewModel.getWaterCup(context);
                            tv_settingResult.setText("권장 물 섭취량을 마시기 위해서는\n" +
                                    " 하루 "+(waterNeed/waterCup+1)+"컵을 마셔야 합니다.");
                            tv_settingResult.setVisibility(View.VISIBLE);
                        } else if (spinner.getSelectedItemPosition() == 1) {
                            settingsViewModel.setCoffeCup(context, Integer.parseInt(et_waterCup.getText().toString()));
                        } else {
                            settingsViewModel.setTeaCup(context, Integer.parseInt(et_waterCup.getText().toString()));

                        }
                    } else {
                        tv_settingResult.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        } else {
            settingsViewModel.setWaterCup(context,473);
            et_waterCup.setText(settingsViewModel.getWaterCup(context).toString());
            if (settingsViewModel.getWaterNeed(context) != 0) {
                Integer waterNeed = settingsViewModel.getWaterNeed(context);
                Integer waterCup = settingsViewModel.getWaterCup(context);
                tv_settingResult.setText("권장 물 섭취량을 마시기 위해서는\n" +
                        " 하루 "+(waterNeed/waterCup+1)+"컵을 마셔야 합니다.");
            } else {
                tv_settingResult.setVisibility(View.INVISIBLE);
            }
        }

        //체중 설정
       if(settingsViewModel.getWeight(context) != null){
           et_weight.setText(settingsViewModel.getWeight(context).toString());
//           tv_settingResult.setVisibility(View.INVISIBLE);
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

       Chip past = chipGroup.findViewById(chipGroup.getCheckedChipId());
       chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(ChipGroup group, int checkedId) {
               Chip chip = group.findViewById(checkedId);
               if(past != chip){
                   Log.d("chip", "onCheckedChanged: chip"+chip.getText().toString());
                   String selectedCup = chip.getText().toString();
                   Integer waterNeed;
                   Integer waterCup;
                   switch (selectedCup) {
                       case "종이컵(소)" :
                           if (et_waterCup.getText().toString().equals(180)) {
                               break;
                           } else {
                               if (spinner.getSelectedItemPosition() == 0) {
                                   settingsViewModel.setWaterCup(context, 180);
                                   et_waterCup.setText(settingsViewModel.getWaterCup(context).toString());
                                   waterNeed = settingsViewModel.getWaterNeed(context);
                                   waterCup = settingsViewModel.getWaterCup(context);
                                   tv_settingResult.setText("권장 물 섭취량을 마시기 위해서는\n" +
                                           " 하루 "+(waterNeed/waterCup+1)+"컵을 마셔야 합니다.");
                                   tv_settingResult.setVisibility(View.VISIBLE);
                               } else if (spinner.getSelectedItemPosition() == 1) {
                                   settingsViewModel.setCoffeCup(context, 180);
                                   et_waterCup.setText(settingsViewModel.getCoffeeCup(context).toString());
                               } else {
                                   settingsViewModel.setTeaCup(context, 180);
                                   et_waterCup.setText(settingsViewModel.getTeaCup(context).toString());
                               }
                               break;
                           }
                       case "종이컵(대)" :
                           if (et_waterCup.getText().toString().equals(360)) {
                               break;
                           } else {
                               if (spinner.getSelectedItemPosition() == 0) {
                                   settingsViewModel.setWaterCup(context, 360);
                                   et_waterCup.setText(settingsViewModel.getWaterCup(context).toString());
                                   waterNeed = settingsViewModel.getWaterNeed(context);
                                   waterCup = settingsViewModel.getWaterCup(context);
                                   tv_settingResult.setText("권장 물 섭취량을 마시기 위해서는\n" +
                                           " 하루 "+(waterNeed/waterCup+1)+"컵을 마셔야 합니다.");
                                   tv_settingResult.setVisibility(View.VISIBLE);
                               } else if (spinner.getSelectedItemPosition() == 1) {
                                   settingsViewModel.setCoffeCup(context, 360);
                                   et_waterCup.setText(settingsViewModel.getCoffeeCup(context).toString());
                               } else {
                                   settingsViewModel.setTeaCup(context, 360);
                                   et_waterCup.setText(settingsViewModel.getTeaCup(context).toString());
                               }
                               break;
                           }
                       case "스몰 사이즈" :
                           if (et_waterCup.getText().toString().equals(240)) {
                               break;
                           } else {
                               if (spinner.getSelectedItemPosition() == 0) {
                                   settingsViewModel.setWaterCup(context, 240);
                                   et_waterCup.setText(settingsViewModel.getWaterCup(context).toString());
                                   waterNeed = settingsViewModel.getWaterNeed(context);
                                   waterCup = settingsViewModel.getWaterCup(context);
                                   tv_settingResult.setText("권장 물 섭취량을 마시기 위해서는\n" +
                                           " 하루 "+(waterNeed/waterCup+1)+"컵을 마셔야 합니다.");
                                   tv_settingResult.setVisibility(View.VISIBLE);
                               } else if (spinner.getSelectedItemPosition() == 1) {
                                   settingsViewModel.setCoffeCup(context, 240);
                                   et_waterCup.setText(settingsViewModel.getCoffeeCup(context).toString());
                               } else {
                                   settingsViewModel.setTeaCup(context, 240);
                                   et_waterCup.setText(settingsViewModel.getTeaCup(context).toString());
                               }
                               break;
                           }
                       case "레귤러 사이즈" :
                           if (et_waterCup.getText().toString().equals(300)) {
                               break;
                           } else {
                               if (spinner.getSelectedItemPosition() == 0) {
                                   settingsViewModel.setWaterCup(context, 300);
                                   et_waterCup.setText(settingsViewModel.getWaterCup(context).toString());
                                   waterNeed = settingsViewModel.getWaterNeed(context);
                                   waterCup = settingsViewModel.getWaterCup(context);
                                   tv_settingResult.setText("권장 물 섭취량을 마시기 위해서는\n" +
                                           " 하루 "+(waterNeed/waterCup+1)+"컵을 마셔야 합니다.");
                                   tv_settingResult.setVisibility(View.VISIBLE);
                               } else if (spinner.getSelectedItemPosition() == 1) {
                                   settingsViewModel.setCoffeCup(context, 300);
                                   et_waterCup.setText(settingsViewModel.getCoffeeCup(context).toString());
                               } else {
                                   settingsViewModel.setTeaCup(context, 300);
                                   et_waterCup.setText(settingsViewModel.getTeaCup(context).toString());
                               }
                               break;
                           }
                       case "톨 사이즈" :
                           if (et_waterCup.getText().toString().equals(255)) {
                               break;
                           } else {
                               if (spinner.getSelectedItemPosition() == 0) {
                                   settingsViewModel.setWaterCup(context, 255);
                                   et_waterCup.setText(settingsViewModel.getWaterCup(context).toString());
                                   waterNeed = settingsViewModel.getWaterNeed(context);
                                   waterCup = settingsViewModel.getWaterCup(context);
                                   tv_settingResult.setText("권장 물 섭취량을 마시기 위해서는\n" +
                                           " 하루 "+(waterNeed/waterCup+1)+"컵을 마셔야 합니다.");
                                   tv_settingResult.setVisibility(View.VISIBLE);
                               } else if (spinner.getSelectedItemPosition() == 1) {
                                   settingsViewModel.setCoffeCup(context, 255);
                                   et_waterCup.setText(settingsViewModel.getCoffeeCup(context).toString());
                               } else {
                                   settingsViewModel.setTeaCup(context, 255);
                                   et_waterCup.setText(settingsViewModel.getTeaCup(context).toString());
                               }
                               break;
                           }
                       case "그란데 사이즈" :
                           if (et_waterCup.getText().toString().equals(473)) {
                               break;
                           } else {
                               if (spinner.getSelectedItemPosition() == 0) {
                                   settingsViewModel.setWaterCup(context, 473);
                                   et_waterCup.setText(settingsViewModel.getWaterCup(context).toString());
                                   waterNeed = settingsViewModel.getWaterNeed(context);
                                   waterCup = settingsViewModel.getWaterCup(context);
                                   tv_settingResult.setText("권장 물 섭취량을 마시기 위해서는\n" +
                                           " 하루 "+(waterNeed/waterCup+1)+"컵을 마셔야 합니다.");
                                   tv_settingResult.setVisibility(View.VISIBLE);
                               } else if (spinner.getSelectedItemPosition() == 1) {
                                   settingsViewModel.setCoffeCup(context, 473);
                                   et_waterCup.setText(settingsViewModel.getCoffeeCup(context).toString());
                               } else {
                                   settingsViewModel.setTeaCup(context, 473);
                                   et_waterCup.setText(settingsViewModel.getTeaCup(context).toString());
                               }
                               break;
                           }
                       case "벤티 사이즈" :
                           if (et_waterCup.getText().toString().equals(591)) {
                               break;
                           } else {
                               if (spinner.getSelectedItemPosition() == 0) {
                                   settingsViewModel.setWaterCup(context, 591);
                                   et_waterCup.setText(settingsViewModel.getWaterCup(context).toString());
                                   waterNeed = settingsViewModel.getWaterNeed(context);
                                   waterCup = settingsViewModel.getWaterCup(context);
                                   tv_settingResult.setText("권장 물 섭취량을 마시기 위해서는\n" +
                                           " 하루 "+(waterNeed/waterCup+1)+"컵을 마셔야 합니다.");
                                   tv_settingResult.setVisibility(View.VISIBLE);
                               } else if (spinner.getSelectedItemPosition() == 1) {
                                   settingsViewModel.setCoffeCup(context, 591);
                                   et_waterCup.setText(settingsViewModel.getCoffeeCup(context).toString());
                               } else {
                                   settingsViewModel.setTeaCup(context, 591);
                                   et_waterCup.setText(settingsViewModel.getTeaCup(context).toString());
                               }
                               break;
                           }
                       case "리터" :
                           if (et_waterCup.getText().toString().equals(1000)) {
                               break;
                           } else {
                               if (spinner.getSelectedItemPosition() == 0) {
                                   settingsViewModel.setWaterCup(context, 1000);
                                   et_waterCup.setText(settingsViewModel.getWaterCup(context).toString());
                                   waterNeed = settingsViewModel.getWaterNeed(context);
                                   waterCup = settingsViewModel.getWaterCup(context);
                                   tv_settingResult.setText("권장 물 섭취량을 마시기 위해서는\n" +
                                           " 하루 "+(waterNeed/waterCup+1)+"컵을 마셔야 합니다.");
                                   tv_settingResult.setVisibility(View.VISIBLE);
                               } else if (spinner.getSelectedItemPosition() == 1) {
                                   settingsViewModel.setCoffeCup(context, 1000);
                                   et_waterCup.setText(settingsViewModel.getCoffeeCup(context).toString());
                               } else {
                                   settingsViewModel.setTeaCup(context, 1000);
                                   et_waterCup.setText(settingsViewModel.getTeaCup(context).toString());
                               }
                               break;
                           }
                   }
               }


           }
       });





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
        settingsViewModel.setWaterCup(context, Integer.parseInt(et_waterCup.getText().toString()));
        settingsViewModel.setWeight(context, Integer.parseInt(et_weight.getText().toString()));


    }



}