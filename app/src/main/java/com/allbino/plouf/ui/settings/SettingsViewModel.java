package com.allbino.plouf.ui.settings;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.allbino.plouf.data.PreferencesManager;

/*####################################################################################
 *형태 : Class
 * 모듈ID : SettingsViewModel
 * 설명 : SettingsFragment 데이터 관리
 * SharedPreferences 값 저장, 반환(컵 용량, 몸무게)
 * */

public class SettingsViewModel extends ViewModel {

    private PreferencesManager preferencesManager;

    public SettingsViewModel() {
        preferencesManager = new PreferencesManager();
    }


    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : SettingsViewModel
     * 반환값 : 없음
     * 설명 : SharedPreferences 값 저장(컵 용량, 몸무게)
     */
    public void setWaterCup(Context context, Integer waterCup) {
        preferencesManager.setWaterCup(context, waterCup);
    }

    public void setCoffeCup(Context context, Integer coffeeCup) {
        preferencesManager.setCoffeeCup(context, coffeeCup);
    }

    public void setTeaCup(Context context, Integer teaCup) {
        preferencesManager.setTeaCup(context, teaCup);
    }

    public void setWeight(Context context, Integer weight) {
        preferencesManager.setWeight(context, weight);
    }




    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : SettingsViewModel
     * 반환값 : Integer, Boolean
     * 설명 : SharedPreferences 저장된 값 반환(컵 용량, 몸무게)
     */
    public Integer getWaterCup(Context context) {
        return preferencesManager.getWaterCup(context);
    }
    public Integer getCoffeeCup(Context context) { return preferencesManager.getCoffeeCup(context); }
    public Integer getTeaCup(Context context) {
        return preferencesManager.getTeaCup(context);
    }


    public Integer getWeight(Context context) {
        return preferencesManager.getWeight(context);   //단위 자르기
    }

    public Integer getWaterNeed(Context context) { return preferencesManager.getWaterNeed(context);}

}