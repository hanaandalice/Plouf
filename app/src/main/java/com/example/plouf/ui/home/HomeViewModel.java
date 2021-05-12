package com.example.plouf.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.plouf.MainActivity;
import com.example.plouf.data.AppDatabase;
import com.example.plouf.data.PdDao;
import com.prolificinteractive.materialcalendarview.CalendarDay;

/*
* ViewModel UI 관련 데이터 보관, 관리
* */

//‘// ---------------------------------------------------------------------------------------
//        ‘// 형태	: Object 종류 (Event, Function, Method, Property …)
//        ‘// 소유자	: ObjectID (Object 소속위치)
//        ‘// 반환값	: ADODB.Connection (String, Long, Iteger, Double, Object, Boolean, …)
//        '// 최초생성일	: 2017/03/21 18:35:22 (최초작성자)
//        ‘// 최종수정일	: 2017/03/24 19:23:41 (수정자)
//        '// 설명	: Return DBConnectiion of current session
//        '---------------------------------------------------------------------------------------

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> txtProgress;
    public MutableLiveData<String> txtWaterAmount;
    public Integer waterCnt;
    public Integer waterAmount;
    public Integer peeCount;
    public Integer fecesCount;
    public Integer waterNeed;
    public String waterState;
    public Integer cup;
//    public float waterPer;
//    public CalendarFragment calendarFragment;

    AppDatabase appDatabase;
    public CalendarDay calendarDay;


    public MutableLiveData<Integer> livePee;
    public MutableLiveData<Integer> livefeces;
    private AppDatabase db;

    public HomeViewModel() {
//        db = MainActivity.db;
        txtProgress = new MutableLiveData<>();
        txtWaterAmount = new MutableLiveData<>();
        waterState = new String();
        peeCount = 6;   //디비에서 받아오기
        calendarDay = CalendarDay.today();
//        peeCount = db.pdDao().getPeeCnt(calendarDay.getDate().toString());   //repository로 데이터에 접근

//        Log.d("DB", "HomeViewModel: "+peeCount);




        Log.d("DB", "HomeViewModel: "+calendarDay.getDate().toString());    //2021-05-11 형태

        fecesCount = 2; //디비에서 대변 받아오기
        waterCnt = 4;   //디비에서 물 연속 성취일수 받아오기 PD_01.ACHIEVE_CNT
        cup = 473;  //SharedPreferences에서 컵용량 받아오기
        waterNeed = 2400;  //SharedPreferences에서 몸무게 가져와서 마셔야할 물 양 계산해서 워터 need에
        waterAmount = 0; //디비에서 물 양 받아오기 PD_01.WATER
        waterState = waterAmount+"/"+waterNeed+"ml";



        livePee = new MutableLiveData<>();
        livefeces = new MutableLiveData<>();

        livePee.setValue(peeCount);
        livefeces.setValue(fecesCount);


        txtProgress.setValue(waterCnt+" 일째 물 마시기 도전 중");
//        txtWaterAmount.setValue(waterAmount+" ml 마셨습니다.");

//        txtProg = waterCnt+" 1일째 물 마시기 도전 중";


    }

    //get data
    public LiveData<String> getProgressTxt() {
        return txtProgress; }

//    public LiveData<String> getWaterAmountTxt() { return txtWaterAmount;}

    public Integer getWaterAmount() { return waterAmount;} //물 섭취량 보내기
    public Integer getWaterNeed() { return waterNeed;} //필요 물 량 보내기
    public String getWaterState() {  return waterState;} //물 총량 대비 마신 상태
    public float getWaterPer() {  return ((float)waterAmount/(float)waterNeed)*100;}

    public Integer getPeeCount() { return peeCount;}
    public Integer getFecesCount() { return fecesCount;}


    public LiveData<Integer> getLivePee() { return livePee; }
    public LiveData<Integer> getLiveFeces() { return livefeces; }





    //add data
    public void addWater() {
        waterAmount+=cup;
        waterState = waterAmount+"/"+waterNeed+"ml";
        //db에 물 섭취량 저장
    }

    public void addPee() {
        peeCount++;
        //db에 peecount 저장
    }

    public void addFeces(){
        fecesCount++;;
        //db에 fecesCount 저장
    }

    //subtract data
    public void subWater(){
        waterAmount-=cup;
        waterState = waterAmount+"/"+waterNeed+"ml";
        //db에 저장
    }
    public void subPee(){
        peeCount--;
        //db에 저장
    }
    public void subFeces(){
        fecesCount--;
        //db에 저장
    }

}