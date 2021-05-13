package com.example.plouf.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

//SharedPreferences 관리
//앱 잠금 설정 세팅값, 체중, 컵 용량, 권장 물 섭취량 저장하고 가져오기 수행
public class PreferencesManager{
    private Boolean lockSetting;
    private Integer weight;
    private Integer cup;
    private Integer waterNeed;
    private static String shared ="shared";

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(shared, Context.MODE_PRIVATE);
    }

    //get pref
    public Boolean getLockSetting(Context context) {
        SharedPreferences preferences = getPreferences(context);
        lockSetting = preferences.getBoolean("lockSetting", false);
        return lockSetting;
    }

    public Integer getWeight(Context context) {
        SharedPreferences preferences = getPreferences(context);
        Log.d("pref", "getWeight: prefM 진입");
        weight = preferences.getInt("weight", 0);
        return weight;
    }

    public Integer getCup(Context context) {
        Log.d("pref", "getCup: PreferencesManager");
        SharedPreferences preferences = getPreferences(context);
        cup = preferences.getInt("cup", 473);
        Log.d("pref", "getCup: 성공"+cup);
        return cup;
    }

    public Integer getWaterNeed(Context context) {
        SharedPreferences preferences = getPreferences(context);
        waterNeed = preferences.getInt("waterNeed", 0);
        return waterNeed;
    }

    //set pref
    public void setLockSetting(Context context, Boolean lockSetting) {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        this.lockSetting = lockSetting;
        editor.putBoolean("lockSetting", lockSetting);
        editor.commit();
    }

    public void setWeight(Context context, Integer weight) {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        this.weight = weight;
        waterNeed = weight * 30; // 단위 ml
        editor.putInt("weight", weight);
        editor.putInt("waterNeed", waterNeed);
        editor.commit();
    }

    public void setCup(Context context, Integer cup) {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        this.cup = cup;
        editor.putInt("cup", cup);
        editor.commit();

    }


}
