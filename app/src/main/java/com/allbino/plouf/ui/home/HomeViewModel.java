package com.allbino.plouf.ui.home;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.allbino.plouf.PdApplication;
import com.allbino.plouf.data.PdRepository;
import com.allbino.plouf.data.PreferencesManager;
import com.prolificinteractive.materialcalendarview.CalendarDay;



/*####################################################################################
 *형태 : Class
 * 모듈ID : HomeViewModel
 * 설명 :  HomeFragment 데이터 관리
 * 오늘 날짜 데이터 디비에 있는지 확인
 * 물, 화장실 관련 데이터 증가, 감소
 * 필요 물량, 물 총량대비 마신 상태 초기화, cup 용량 초기화
 * */

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> txtProgress;
    public Integer acCnt;
    public Integer waterAmount;
    public Integer coffee;
    public Integer tea;
    public Integer peeCnt;
    public Integer fecesCnt;
    public Integer waterNeed;
    public String waterState;
    public Integer waterCup;
    public Integer coffeeCup;
    public Integer teaCup;


    public String today;
    public PdRepository pdRepository;
    public PdApplication pdApplication;
    private PreferencesManager preferencesManager;


    public HomeViewModel() {
        txtProgress = new MutableLiveData<>();
        waterState = new String();

        pdApplication = new PdApplication().getAppInstance();
        pdRepository = new PdRepository(pdApplication);
        preferencesManager = new PreferencesManager();

        today = CalendarDay.today().getDate().toString();
//        today = "2021-05-30";
        checkDate(today);

        Log.d("DB", "HomeViewModel: "+today);    //2021-05-11 형태

        peeCnt = pdRepository.getPeeCnt(today);
        fecesCnt = pdRepository.getFecesCnt(today);//디비에서 받아오기
        acCnt = pdRepository.getAcCnt(today);
        waterAmount = pdRepository.getWater(today);
        coffee = pdRepository.getCoffee(today);
        tea = pdRepository.getTea(today);


        Log.d("pref", "HomeViewModel: amount 초기화후"+waterAmount);

        waterCup = 473;  //SharedPreferences에서 컵용량 받아오기
        coffeeCup = 473;  //SharedPreferences에서 컵용량 받아오기
        teaCup = 473;  //SharedPreferences에서 컵용량 받아오기

        waterNeed = 0;
        waterState = waterAmount+"/"+waterNeed+"ml";

        txtProgress.setValue(acCnt +" 일째 물 마시기 도전 중");

    }


    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : HomeViewModel
     * 반환값 : LiveData<String>, Integer, String, float
     * 설명 : 진행상황, 물 섭취량, 필요 물 량, 물 총량 대비 마신 상태, 물 섭취 퍼센트, 소변 횟수, 대변 횟수, 컵 용량을 반환함.
     */
    public LiveData<String> getProgressTxt() { return txtProgress; }
    public Integer getWaterAmount() { return waterAmount;} //물 섭취량 보내기
//    public Integer getWaterNeed() { return waterNeed;} //필요 물 량 보내기
    public String getWaterState() {  return waterState;} //물 총량 대비 마신 상태
    public float getWaterPer() {
//        Log.d("pref", "getWaterPer: waterNeed"+waterNeed);
        return ((float)waterAmount/(float)waterNeed)*100;
    }
    public Integer getPeeCnt() { return peeCnt;}
    public Integer getFecesCnt() { return fecesCnt;}
    public Integer getWaterNeed(Context context) { return  preferencesManager.getWaterNeed(context); }
    public Integer getWaterCup(Context context) { return  preferencesManager.getWaterCup(context); }
    public Integer getCoffeeCup(Context context) { return  preferencesManager.getCoffeeCup(context); }
    public Integer getTeaCup(Context context) { return  preferencesManager.getTeaCup(context); }
    public Integer getCoffee() { return coffee;}
    public Integer getTea() { return tea;}



    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : HomeViewModel
     * 반환값 : 없음
     * 설명 : 필요 물량, 물 총량대비 마신 상태 초기화, cup 용량 초기화
     */
    public void setWaterNeed(Integer waterNeed) {
        this.waterNeed = waterNeed;
        waterState = waterAmount+"/"+waterNeed+"ml";
    }
    public void setWaterCup(Integer cup) { this.waterCup = cup; }
    public void setCoffeeCup(Integer cup) { this.coffeeCup = cup; }
    public void setTeaCup(Integer cup) { this.teaCup = cup; }





    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : HomeViewModel
     * 반환값 : 없음
     * 설명 : 마신 물량 컵용량만큼 더하고 총량대비 마신상태 초기화.
     *        디비에 오늘 날짜의 마신 물 량 저장.
     */
    public void addWater() {
        waterAmount+=waterCup;
        waterState = waterAmount+"/"+waterNeed+"ml";
        try{
//            Log.d("DB", "addWater: 전");
            pdRepository.addWater(today, waterCup);
//            Log.d("DB", "addWater: 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : HomeViewModel
     * 반환값 : 없음
     * 설명 : 마신 커피량 컵용량만큼 더함.
     *        디비에 오늘 날짜의 마신 커피 량 저장.
     */
    public void addCoffee() {
        coffee += coffeeCup;
        try{
            Log.d("DB", "addCoffee: 전");
            pdRepository.addCoffee(today, coffeeCup);
            Log.d("DB", "addCoffee: 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : HomeViewModel
     * 반환값 : 없음
     * 설명 : 마신 차 량 컵용량만큼 더함.
     *        디비에 오늘 날짜의 마신 차 량 저장.
     */
    public void addTea() {
        tea += teaCup;
        try{
            Log.d("DB", "addTea: 전");
            pdRepository.addTea(today, teaCup);
            Log.d("DB", "addTea: 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : HomeViewModel
     * 반환값 : 없음
     * 설명 : 소변횟수 1 증가.
     *        디비에 오늘 날짜의 소변 횟수 저장.
     */
    public void addPee() {
        peeCnt++;
        try{
            pdRepository.addPeeCnt(today);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : HomeViewModel
     * 반환값 : 없음
     * 설명 : 대변 횟수 1 증가.
     *        디비에 오늘 날짜의 대변 횟수 저장.
     */
    public void addFeces(){
        fecesCnt++;;
        try{
            pdRepository.addFecesCnt(today);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //subtract data
    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : HomeViewModel
     * 반환값 : 없음
     * 설명 : 마신 물 량 컵 용량 만큼 감소.
     *        디비에 오늘 날짜의 마신 물 량 저장.
     */
    public void subWater() {
        if(waterAmount > 0 && waterAmount >= waterCup) {
            waterAmount-=waterCup;
            waterState = waterAmount+"/"+waterNeed+"ml";
            try{
                pdRepository.subWater(today, waterCup);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ( waterAmount > 0) {
            try{
                pdRepository.subWater(today, waterAmount);
            } catch (Exception e) {
                e.printStackTrace();
            }
            waterAmount = 0;
            waterState = waterAmount+"/"+waterNeed+"ml";
        }
    }

    public void subCoffee() {
        if(coffee > 0 && coffee >= coffeeCup) {
            coffee-=coffeeCup;
            try{
                pdRepository.subCoffee(today, coffeeCup);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (coffee > 0 && coffee < coffeeCup) {
            try{
                pdRepository.subCoffee(today, coffee);

            } catch (Exception e) {
                e.printStackTrace();
            }
            coffee = 0;
        }
    }

    public void subTea() {
        if(tea > 0 && tea >= teaCup) {
            tea-=teaCup;
            try{
                pdRepository.subTea(today, teaCup);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (tea > 0 && tea < teaCup) {
            try{
                pdRepository.subTea(today, tea);

            } catch (Exception e) {
                e.printStackTrace();
            }
            tea = 0;
        }
    }

    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : HomeViewModel
     * 반환값 : 없음
     * 설명 : 소변횟수 1 감소.
     *        디비에 오늘 날짜의 소변 횟수 저장.
     */
    public void subPee() {
        if(peeCnt > 0) {
            peeCnt--;
            try{
                pdRepository.subPeeCnt(today);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : HomeViewModel
     * 반환값 : 없음
     * 설명 : 대변횟수 1 감소.
     *        디비에 오늘 날짜의 대변 횟수 저장.
     */
    public void subFeces() {
        if(fecesCnt > 0) {
            fecesCnt--;
            try{
//            Log.d("DB", "subFeces: 전");
                pdRepository.subFecesCnt(today);
//            Log.d("DB", "subFeces: 완료");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : HomeViewModel
     * 반환값 : 없음
     * 설명 : 오늘날짜의 데이터 디비에 있나 확인하고 없으면 초기 데이터 저장.
     */
    public void checkDate(String date) {
        try{
            if(pdRepository.checkDate(date) == true) {  //true이면 없는거임.
                pdRepository.insertInitDate(date);
//                Log.d("DB", "checkDate: insert완료"+date);
            }
//            Log.d("DB", "checkDate: ");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : HomeViewModel
     * 반환값 : 없음
     * 설명 : 오늘날짜의 waterAC 디비 저장.
     */
    public void setWaterAC(Integer waterAc) {
        try{
            pdRepository.setWaterAc(today, waterAc);
            Log.d("DB", "setWaterAC: homeView "+waterAc);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}