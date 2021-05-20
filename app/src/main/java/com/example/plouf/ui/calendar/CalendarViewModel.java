package com.example.plouf.ui.calendar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalendarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CalendarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("일 평균 소변 횟수 : 6 회         일 평균 대변 횟수 : 1 회"); //디비에서 평균데이터 받아와서 수정해주기.
    }

    public LiveData<String> getText() {
        return mText;
    }
}
