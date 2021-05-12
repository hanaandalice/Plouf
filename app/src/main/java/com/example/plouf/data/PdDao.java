package com.example.plouf.data;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;


@Dao
public interface PdDao {
   @Insert()
   void insert(PdEntity pdEntity);

   @Delete
   void delete(PdEntity pdEntity);

   @Update
   void update(PdEntity pdEntity);

   //pd 데이터 전체 가져오기
   @Query("SELECT * FROM PD_01 ORDER BY RECORD_DATE")
   LiveData<List<PdEntity>> loadAllPd();

   //날짜에 따른 물 섭취량
   @Query("SELECT WATER FROM PD_01 WHERE RECORD_DATE = :date")
   Integer getWater(String date);

   //날짜에 따른 소변 횟수
   @Query("SELECT PEE_CNT FROM PD_01 WHERE RECORD_DATE = :date")
   Integer getPeeCnt(String date);

   //날짜에 따른 대변 횟수
   @Query("SELECT FECES_CNT FROM PD_01 WHERE RECORD_DATE = :date")
   Integer getFecesCnt(String date);

   //소변 전체 평균
   @Query("SELECT AVG(PEE_CNT) FROM PD_01")
   Integer getPeeAvg();

   //대변 전체 평균
   @Query("SELECT AVG(FECES_CNT) FROM PD_01")
   Integer getFecesAvg();

   //테스트 데이터 삽입
   @Query("INSERT INTO PD_01 VALUES('2021-05-11',120,475,50,7,2,1,5)")
   void insertTest();

   @Query("DELETE FROM PD_01 WHERE RECORD_DATE = :date")
   void deleteByDate(String date);

   @Query("SELECT * FROM PD_01 WHERE RECORD_DATE = :date")
   PdEntity getPdByDate(String date);

   //가장 최근의 acheive_cnt 불러오기
   @Query("SELECT ACHIEVE_CNT FROM PD_01 ORDER BY RECORD_DATE DESC LIMIT 1")
   Integer getAcCnt();

   @Query("SELECT WATER_ACHIEVE FROM PD_01 ORDER BY RECORD_DATE DESC LIMIT 1")
   Integer getWaterAc();

}
