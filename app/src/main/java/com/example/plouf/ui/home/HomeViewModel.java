package com.example.plouf.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.plouf.PdApplication;
import com.example.plouf.data.PdRepository;
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
    public Integer waterAcCnt;
    public Integer waterAmount;
    public Integer peeCnt;
    public Integer fecesCnt;
    public Integer waterNeed;
    public String waterState;
    public Integer cup;

    public String today;
    public PdRepository pdRepository;
    public PdApplication pdApplication;

    public HomeViewModel() {
        txtProgress = new MutableLiveData<>();
        txtWaterAmount = new MutableLiveData<>();
        waterState = new String();

        pdApplication = new PdApplication().getAppInstance();
        pdRepository = new PdRepository(pdApplication);


        today = CalendarDay.today().getDate().toString();
        checkDate(today);

        Log.d("DB", "HomeViewModel: "+today);    //2021-05-11 형태

        peeCnt = pdRepository.getIntData(today, "peeCnt");   //디비에서 받아오기
        fecesCnt = pdRepository.getIntData(today, "fecesCnt");
        waterAcCnt = pdRepository.getIntData(today, "acCnt");
        waterAmount = pdRepository.getIntData(today, "water");

        cup = 473;  //SharedPreferences에서 컵용량 받아오기
        waterNeed = 2400;  //SharedPreferences에서 몸무게 가져와서 마셔야할 물 양 계산해서 워터 need에
        waterState = waterAmount+"/"+waterNeed+"ml";

        txtProgress.setValue(waterAcCnt +" 일째 물 마시기 도전 중");

    }

    //get data
    public LiveData<String> getProgressTxt() { return txtProgress; }
    public Integer getWaterAmount() { return waterAmount;} //물 섭취량 보내기
    public Integer getWaterNeed() { return waterNeed;} //필요 물 량 보내기
    public String getWaterState() {  return waterState;} //물 총량 대비 마신 상태
    public float getWaterPer() {  return ((float)waterAmount/(float)waterNeed)*100;}

    public Integer getPeeCnt() { return peeCnt;}
    public Integer getFecesCnt() { return fecesCnt;}


    //add data
    public void addWater() {
        waterAmount+=cup;
        waterState = waterAmount+"/"+waterNeed+"ml";
        try{
//            Log.d("DB", "addWater: 전");
            pdRepository.addWater(today);
//            Log.d("DB", "addWater: 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCoffee() {   //fragment에서 입력모드(물, 녹차, 커피)확인 하고 실행해주기
        try{
            Log.d("DB", "addCoffee: 전");
            pdRepository.addCoffee(today);
            Log.d("DB", "addCoffee: 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addTea() {
        try{
            Log.d("DB", "addTea: 전");
            pdRepository.addTea(today);
            Log.d("DB", "addTea: 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addPee() {
        peeCnt++;
        try{
//            Log.d("DB", "addPee: 전");
            pdRepository.addPeeCnt(today);
//            Log.d("DB", "addPee: 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addFeces(){
        fecesCnt++;;
        try{
//            Log.d("DB", "addFeces: 전");
            pdRepository.addFecesCnt(today);
//            Log.d("DB", "addFeces: 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //subtract data
    public void subWater(){
        waterAmount-=cup;
        waterState = waterAmount+"/"+waterNeed+"ml";
        try{
//            Log.d("DB", "subWater: 전");
            pdRepository.subWater(today);
//            Log.d("DB", "subWater: 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void subPee(){
        peeCnt--;
        try{
//            Log.d("DB", "subpee: 전");
            pdRepository.subPeeCnt(today);
//            Log.d("DB", "subPee: 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void subFeces(){
        fecesCnt--;
        try{
//            Log.d("DB", "subFeces: 전");
            pdRepository.subFecesCnt(today);
//            Log.d("DB", "subFeces: 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //db에 저장
    }

    public void checkDate(String date) {
        try{
            if(pdRepository.checkDate(date) == true) {  //true이면 없는거임.
                pdRepository.insertInitDate(date);
//                Log.d("DB", "checkDate: insert완료"+date);
            }
//            Log.d("DB", "checkDate: ");
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

}