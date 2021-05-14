package com.example.plouf.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import kotlin.random.Random;

/*####################################################################################
 *형태 : Class
 * 모듈ID : PdEntity
 * 설명 : PdEntity 정의
 * */
@Entity(tableName = "PD_01")
public class PdEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "RECORD_DATE")
    private String date;


    @ColumnInfo(name = "WATER")
    private Integer water ;

    @ColumnInfo(name = "COFFEE")
    private Integer coffee;

    @ColumnInfo(name = "TEA")
    private Integer tea;

    @ColumnInfo(name = "PEE_CNT")
    private Integer peeCnt;

    @ColumnInfo(name = "FECES_CNT")
    private  Integer fecesCnt;

    @ColumnInfo(name = "WATER_ACHIEVE")
    private Integer waterAc;

    @ColumnInfo(name = "ACHIEVE_CNT")
    private Integer acCnt;

    @NonNull
    public String getDate() {
        return date;
    }

    public Integer getWater() {
        return water;
    }

    public Integer getCoffee() {
        return coffee;
    }

    public Integer getTea() {
        return tea;
    }

    public Integer getPeeCnt() {
        return peeCnt;
    }

    public Integer getFecesCnt() {
        return fecesCnt;
    }

    public Integer getWaterAc() {
        return waterAc;
    }

    public Integer getAcCnt() {
        return acCnt;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public void setWater(Integer water) {
        this.water = water;
    }

    public void setCoffee(Integer coffee) {
        this.coffee = coffee;
    }

    public void setTea(Integer tea) {
        this.tea = tea;
    }

    public void setPeeCnt(Integer peeCnt) {
        this.peeCnt = peeCnt;
    }

    public void setFecesCnt(Integer fecesCnt) {
        this.fecesCnt = fecesCnt;
    }

    public void setWaterAc(Integer waterAchieve) {
        this.waterAc = waterAchieve;
    }

    public void setAcCnt(Integer achiveCnt) {
        this.acCnt = achiveCnt;
    }
}
