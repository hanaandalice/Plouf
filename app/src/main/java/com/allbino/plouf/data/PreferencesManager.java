package com.allbino.plouf.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/*####################################################################################
 *형태 : Class
 * 모듈ID : PreferencesManager
 * 설명 : SharedPreferences 관리
 * 앱 잠금 설정 세팅값, 체중, 컵 용량, 권장 물 섭취량, password 저장하고 가져오기 수행
 * */
public class PreferencesManager{
    private Integer weight;
    private Integer waterCup;
    private Integer coffeeCup;
    private Integer teaCup;

    private Integer waterNeed;
    private static String shared ="shared";

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(shared, Context.MODE_PRIVATE);
    }


    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : PreferencesManager
     * 반환값 : Boolean, Integer
     * 설명 :  몸무게, 컵 용량(물, 커피, 차) SharedPreferences 값 반환 수행
     */


    public Integer getWeight(Context context) {
        SharedPreferences preferences = getPreferences(context);
        Log.d("pref", "getWeight: prefM 진입");
        weight = preferences.getInt("weight", 0);
        return weight;
    }

    public Integer getWaterCup(Context context) {
        Log.d("pref", "getCup: PreferencesManager");
        SharedPreferences preferences = getPreferences(context);
        waterCup = preferences.getInt("waterCup", 473);
        Log.d("pref", "getCup: 성공"+waterCup);
        return waterCup;
    }

    public Integer getCoffeeCup(Context context) {
        Log.d("pref", "getCup: PreferencesManager");
        SharedPreferences preferences = getPreferences(context);
        coffeeCup = preferences.getInt("coffeeCup", 473);
        Log.d("pref", "getCup: 성공"+coffeeCup);
        return coffeeCup;
    }

    public Integer getTeaCup(Context context) {
        Log.d("pref", "getCup: PreferencesManager");
        SharedPreferences preferences = getPreferences(context);
        teaCup = preferences.getInt("teaCup", 473);
        Log.d("pref", "getCup: 성공"+teaCup);
        return teaCup;
    }

    public Integer getWaterNeed(Context context) {
        SharedPreferences preferences = getPreferences(context);
        waterNeed = preferences.getInt("waterNeed", 0);
        return waterNeed;
    }





    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : PreferencesManager
     * 반환값 : Boolean, Integer
     * 설명 :  몸무게, 컵 용량 SharedPreferences 값 세팅 수행
     */

    public void setWeight(Context context, Integer weight) {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        this.weight = weight;
        waterNeed = weight * 30; // 단위 ml
        editor.putInt("weight", weight);
        editor.putInt("waterNeed", waterNeed);
        editor.commit();
    }

    public void setWaterCup(Context context, Integer cup) {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        this.waterCup = cup;
        editor.putInt("waterCup", cup);
        editor.commit();

    }

    public void setCoffeeCup(Context context, Integer cup) {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        this.coffeeCup = cup;
        editor.putInt("coffeeCup", cup);
        editor.commit();

    }

    public void setTeaCup(Context context, Integer cup) {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        this.waterCup = cup;
        editor.putInt("teaCup", cup);
        editor.commit();

    }


}
