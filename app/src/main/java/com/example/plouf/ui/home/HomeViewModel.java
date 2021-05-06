package com.example.plouf.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    public MutableLiveData<String> txtWaterAmount;
    public Integer waterCnt;
    public Integer waterAmount;



    public HomeViewModel() {
        waterCnt = 4;   //디비에서 물 연속 성취일수 받아오기 PD_01.ACHIEVE_CNT
        mText = new MutableLiveData<>();
        mText.setValue(waterCnt+" 일째 물 마시기 도전 중");

        waterAmount = 1300; //디비에서 물 양 받아오기 PD_01.WATER
        txtWaterAmount = new MutableLiveData<>();
        txtWaterAmount.setValue(waterAmount+" ml 마셨습니다.");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getWaterAmount(){ return txtWaterAmount;}
}