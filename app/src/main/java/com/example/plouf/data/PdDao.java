package com.example.plouf.data;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;


@Dao
public interface PdDao {
   @Insert()
   void insert(Pd pd);

   @Delete
   void delete(Pd pd);

   @Update
   void update(Pd pd);

   @Query("SELECT * FROM PD_01 ORDER BY RECORD_DATE")
   LiveData<List<Pd>> loadAllPd();

   @Query("SELECT WATER FROM pd_01 WHERE RECORD_DATE = :date")
   LiveData<Integer> getWater(String date);

   @Query("SELECT PEE_CNT FROM PD_01 WHERE RECORD_DATE = :date")
   LiveData<Integer> getPeeCnt(String date);

   @Query("SELECT FECES_CNT FROM PD_01 WHERE RECORD_DATE = :date")
   LiveData <Integer> getFecesCnt(String date);



}
