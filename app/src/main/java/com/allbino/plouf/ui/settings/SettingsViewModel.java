package com.allbino.plouf.ui.settings;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.allbino.plouf.data.PreferencesManager;

/*####################################################################################
 *형태 : Class
 * 모듈ID : SettingsViewModel
 * 설명 : SettingsFragment 데이터 관리
 * SharedPreferences 값 저장, 반환(컵 용량, 몸무게, 잠금 설정 여부)
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
     * 설명 : SharedPreferences 값 저장(컵 용량, 몸무게, 잠금 설정 여부)
     */
    public void setCup(Context context, Integer waterCup) {
        preferencesManager.setCup(context, waterCup);
    }
    public void setWeight(Context context, Integer weight) {
        preferencesManager.setWeight(context, weight);
    }
//    public void setLockSetting(Context context, Boolean lockSetting) {
//        preferencesManager.setLockSetting(context, lockSetting);
//    }
//
//    public void setPass(Context context, String pass) {
//        preferencesManager.setPass(context, pass);
//    }


    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : SettingsViewModel
     * 반환값 : Integer, Boolean
     * 설명 : SharedPreferences 저장된 값 반환(컵 용량, 몸무게, 잠금 설정 여부)
     */
    public Integer getCup(Context context) {
        return preferencesManager.getCup(context);
    }
    public Integer getWeight(Context context) {
        return preferencesManager.getWeight(context);   //단위 자르기
    }
//    public Boolean getLockSetting(Context context) {
//        return preferencesManager.getLockSetting(context);
//    }
//
//    public String getPass(Context context) {
//        return preferencesManager.getPass(context);
//    }
    public Integer getWaterNeed(Context context) { return preferencesManager.getWaterNeed(context);}

}