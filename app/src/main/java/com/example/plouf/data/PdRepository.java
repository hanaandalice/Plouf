package com.example.plouf.data;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;

//여기서 pd db에 대한 접근, 데이터 추가, 제거, 등등 관리
public class PdRepository {
    private  PdDao pdDao;
    PdEntity pdEntity;


    public PdRepository(Application application) {
        pdDao = AppDatabase.getInstance(application).pdDao();
        pdEntity = new PdEntity();
        Log.d("DB", "PdRepository: 초기화");
    }

    public List<PdEntity> getAllPds() {
        List<PdEntity> pds = null;
        try{
            pds = new GetAllPdsAsyncTask(pdDao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return pds;
    }

    public boolean checkDate(String date) {
        boolean result = false;
        try{
            result = new GetPdByDate(pdDao, date).execute().get();
            return result;
        } catch(ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
        return false;
    }

    public void insert(PdEntity pdEntity) {
        pdDao.insert(pdEntity);
    }

    public void delete(PdEntity pdEntity) {
        pdDao.delete(pdEntity);
    }

    public void update(PdEntity pdEntity) {
        update(pdEntity);
    }

    public Integer getWater(String date) {
        return pdDao.getWater(date);
    }

    public Integer getPeeCnt(String date) {
        return pdDao.getPeeCnt(date);
    }

    public Integer getFecesCnt(String date) {
        return pdDao.getFecesCnt(date);
    }

    public Integer getPeeAvg() {
        return pdDao.getPeeAvg();
    }

    public Integer getFecesAvg() {
        return pdDao.getFecesAvg();
    }



    public void insertTest() {
        pdDao.insertTest();
    }

    public void deleteByDate(String date) {
        pdDao.deleteByDate(date);
    }


    //초기 데이터 삽입. home에서 제일 먼저 date 있나 확인 하고 없으면 실행 시키기
    public void insertInitDate(String date) {   //AsyncTask로 구현 하기
        Log.d("DB", "insertInitDate: 진입");
        pdEntity.setDate(date);
        pdEntity.setWater(0);
        pdEntity.setCoffee(0);
        pdEntity.setTea(0);
        pdEntity.setPeeCnt(0);
        pdEntity.setFecesCnt(0);
        pdEntity.setWaterAc(0);

        Log.d("DB", "insertInitDate: try 전");

        try{
            Log.d("DB", "insertInitDate: 전");
            new InsertInitDate(pdDao, pdEntity).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("DB", "insertInitDate: 완"+pdEntity);
    }

    public void updateWater(String date) {
        pdEntity = pdDao.getPdByDate(date);
        Integer water = pdEntity.getWater();
        Integer cup=473;    // cup sharedPreferences에서 가져오기
        pdEntity.setWater(water+cup);
        pdDao.update(pdEntity);
    }

    public void updateCoffee(String date) {
        pdEntity = pdDao.getPdByDate(date);
        Integer coffee = pdEntity.getCoffee();
        Integer cup = 473;   //cup SharedPreferences에서 가져오기
        pdEntity.setTea(coffee+cup);
        pdDao.update(pdEntity);
    }

    public void updateTea(String date) {
        pdEntity = pdDao.getPdByDate(date);
        Integer tea = pdEntity.getTea();
        Integer cup = 473;   //cup SharedPreferences에서 가져오기
        pdEntity.setTea(tea+cup);
        pdDao.update(pdEntity);
    }

    public void updatePeeCnt(String date) {
        pdEntity = pdDao.getPdByDate(date);
        Integer peeCnt = pdEntity.getPeeCnt();
        pdEntity.setTea(peeCnt++);
        pdDao.update(pdEntity);
    }

    public void updateFecesCnt(String date) {
        pdEntity = pdDao.getPdByDate(date);
        Integer fecesCnt = pdEntity.getFecesCnt();
        pdEntity.setTea(fecesCnt++);
        pdDao.update(pdEntity);
    }

    private static class GetAllPdsAsyncTask extends AsyncTask<Void, Void, List<PdEntity>> {
        private PdDao pdAsyncTaskDao;

        GetAllPdsAsyncTask(PdDao pdAsyncTaskDao) {
            this.pdAsyncTaskDao = pdAsyncTaskDao;
        }

        @Override
        protected List<PdEntity> doInBackground(Void... voids) {
            return (List<PdEntity>) pdAsyncTaskDao.loadAllPd();
        }
    }

    public static class GetPdByDate extends AsyncTask<Void, Void, Boolean> {
        private PdDao pdAsyncTaskDao;
        private String date;

        GetPdByDate(PdDao pdAsyncTaskDao, String date) {
            this.pdAsyncTaskDao = pdAsyncTaskDao;
            this.date = date;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if(pdAsyncTaskDao.getPdByDate(date) == null) {
                return true;
            }
            return false;
        }
    }

    //초기 데이터 입력
    public static class InsertInitDate extends AsyncTask<Void, Void, Boolean> {
        private PdDao pdAsyncTaskDao;
        private PdEntity pdEntity;

        InsertInitDate(PdDao pdAsyncTaskDao, PdEntity pdEntity) {
            this.pdAsyncTaskDao = pdAsyncTaskDao;
            this.pdEntity = pdEntity;
            Log.d("DB", "InsertInitDate: 입장");
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Integer pastAcCnt = 0;
            if(pdAsyncTaskDao.getWaterAc() == null || pdAsyncTaskDao.getWaterAc() == 0){    //null이거나 0이면 past 는 0
                pastAcCnt = 0;
            } else if(pdAsyncTaskDao.getWaterAc() == 1){
                pastAcCnt = pdAsyncTaskDao.getAcCnt();
            }

            Log.d("DB", "doInBackground: 1");
//            Integer pastAcCnt = 1;
            pdEntity.setAcCnt(pastAcCnt+1);
            Log.d("DB", "doInBackground: 2");
            pdAsyncTaskDao.insert(pdEntity);
            return  true;
        }
    }

}
