package com.example.plouf.ui.calendar;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.paging.LimitOffsetDataSource;

import com.example.plouf.PdApplication;
import com.example.plouf.data.PdRepository;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.List;

/*####################################################################################
 *형태 : Class
 * 모듈ID : CalendarViewModel
 * 설명 : 일 평균 소변 횟수 결과 텍스트 세팅
 * waterAc 케이스별 캘린더 데이 디비에서 가지고 와서 반환
 * */

public class CalendarViewModel extends ViewModel {

    private MutableLiveData<String> avgText;
    private MutableLiveData<String> dailyText;
    ArrayList<CalendarDay> calendarDayList;
    PdRepository pdRepository;
    PdApplication pdApplication;
    String today;
    String todayMonth;


    public CalendarViewModel() {
        avgText = new MutableLiveData<>();
        dailyText = new MutableLiveData<>();
        calendarDayList = new ArrayList<>();
        pdApplication = new PdApplication().getAppInstance();
        pdRepository = new PdRepository(pdApplication);
        today = CalendarDay.today().getDate().toString();
        todayMonth = Integer.toString(CalendarDay.today().getMonth());


        avgText.setValue("일 평균 소변 횟수 : ? 회         일 평균 대변 횟수 : ? 회");
        setToiletAvgResult();//디비에서 평균데이터 받아와서 수정해주기.
        dailyText.setValue("일일 소변 횟수 : ? 회         일일 대변 횟수 : ? 회");

    }

    public LiveData<String> getAvgText() {
        return avgText;
    }
    public LiveData<String> getDailyText() { return  dailyText;}


    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : GetCalendarDayList
     * 반환값 : ArrayList<CalendarDay>
     * 설명 : 디비에서 CalendarDay 리스트 가져오기
     */
    public ArrayList<String> getCalendarDayList(Integer todayMonth, Integer waterAc) {// 비동기식으로 작성하기
        ArrayList<String> calendarDays = new ArrayList<>();
        try {
            Log.d("DB", "getCalendarDayList: "+todayMonth+" "+waterAc);
            calendarDays = (ArrayList<String>) pdRepository.getCalendarDay(todayMonth, waterAc);
            Log.d("DB", "getCalendarDayList: 완");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendarDays;
    }


    public ArrayList<Integer> getData(String date) {
        //TODO : 캘린더에서 선택한  날짜 받아와서 그날의 물, 커피, 차 그래프로 보여주고(Fragment) 그날의 소변 대변 상태도 보여주기
        ArrayList<Integer> data = new ArrayList<>();
        if(pdRepository.getWater(date) != null) {
            data.add(pdRepository.getWater(date));
            data.add(pdRepository.getCoffee(date));
            data.add(pdRepository.getTea(date));
            data.add(pdRepository.getPeeCnt(date));
            data.add(pdRepository.getFecesCnt(date));

            return data;
        }
        return null;
    }



    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : setToiletAvgResult
     * 반환값 : 없음
     * 설명 : 평균 소변, 대변 횟수 디비에서 가지고 와서 텍스트 세팅.
     */
    private void setToiletAvgResult() {
        Integer peeAvg,  feceAvg;
        String toiletAvgResult;
        try {
            peeAvg =  pdRepository.getPeeAvg(today);    //null이라는데 왜?
            Log.d("DB", "setToiletAvgResult: "+peeAvg);
            feceAvg = pdRepository.getFecesAvg(today);  //왜 feces 평균이 아니라 pee 평균 그것도 그날 것만?.
            Log.d("DB", "setToiletAvgResult: feces"+feceAvg);
            toiletAvgResult = "일 평균 소변 횟수 : "+peeAvg+" 회\t 일 평균 대변 횟수 : "+feceAvg+" 회";
            avgText.setValue(toiletAvgResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
