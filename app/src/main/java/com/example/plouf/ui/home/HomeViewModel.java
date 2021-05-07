package com.example.plouf.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
/*
* ViewModel UI 관련 데이터 보관, 관리
* */
public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    public MutableLiveData<String> txtWaterAmount;
    public Integer waterCnt;
    public Integer waterAmount;



    public HomeViewModel() {
        mText = new MutableLiveData<>();
        txtWaterAmount = new MutableLiveData<>();



        waterCnt = 4;   //디비에서 물 연속 성취일수 받아오기 PD_01.ACHIEVE_CNT
        mText.setValue(waterCnt+" 일째 물 마시기 도전 중");

        waterAmount = 1300; //디비에서 물 양 받아오기 PD_01.WATER
        txtWaterAmount.setValue(waterAmount+" ml 마셨습니다.");
    }

    public LiveData<String> getText() { return mText; }

    public LiveData<String> getWaterAmountTxt(){ return txtWaterAmount;}

    public Integer getWaterAmount(){ return waterAmount;} //물 섭취량 보내기
}