package com.example.plouf.ui.settings;

import android.content.Context;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.plouf.PdApplication;
import com.example.plouf.data.PreferencesManager;

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
    public void setCup(Context context, Integer waterCup){
        preferencesManager.setCup(context, waterCup);
    }
    public void setWeight(Context context, Integer weight){
        preferencesManager.setWeight(context, weight);
    }
    public void setLockSetting(Context context, Boolean lockSetting){
        preferencesManager.setLockSetting(context, lockSetting);
    }


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
        Log.d("pref", "getWeight: settings");
        return preferencesManager.getWeight(context);   //단위 자르기
    }
    public Boolean getLockSetting(Context context) {
        return preferencesManager.getLockSetting(context);
    }

}