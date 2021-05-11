package com.example.plouf.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    public Integer waterCup;

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("앱 잠금 설정");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setWaterCup(Integer waterCup){this.waterCup = waterCup;}




}