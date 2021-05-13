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

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    public Integer Cup;
    public Boolean lockSetting = false;
    private PreferencesManager preferencesManager;

    public SettingsViewModel() {
        preferencesManager = new PreferencesManager();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setCup(Context context, Integer waterCup){
        preferencesManager.setCup(context, waterCup);
    }

    public void setWeight(Context context, Integer weight){
        preferencesManager.setWeight(context, weight);
    }


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