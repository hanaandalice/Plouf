package com.example.plouf.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PD_01")
public class Pd {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "RECORD_DATE")
    private String date;

    @ColumnInfo(name = "WATER")
    private Integer water;

    @ColumnInfo(name = "COFFEE")
    private Integer coffee;

    @ColumnInfo(name = "TEA")
    private Integer tea;

    @ColumnInfo(name = "PEE_CNT")
    private Integer peeCnt;

    @ColumnInfo(name = "FECES_CNT")
    private  Integer fecesCnt;

    @ColumnInfo(name = "WATER_ACHIEVE")
    private Integer waterAchieve;

    @ColumnInfo(name = "ACHIEVE_CNT")
    private Integer achiveCnt;

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

    public Integer getWaterAchieve() {
        return waterAchieve;
    }

    public Integer getAchiveCnt() {
        return achiveCnt;
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

    public void setWaterAchieve(Integer waterAchieve) {
        this.waterAchieve = waterAchieve;
    }

    public void setAchiveCnt(Integer achiveCnt) {
        this.achiveCnt = achiveCnt;
    }
}
