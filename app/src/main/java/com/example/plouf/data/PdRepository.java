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
    GetIntData getIntData;


    public PdRepository(Application application) {
        pdDao = AppDatabase.getInstance(application).pdDao();
        pdEntity = new PdEntity();
        Log.d("DB", "PdRepository: 초기화");
    }

    public PdDao getPdDao(){
        return pdDao;
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
            result = new CheckPdByDate(pdDao, date).execute().get();
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

    public Integer getIntData(String date, String columnName) {
        try{
            return new GetIntData(pdDao, columnName, date).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
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

    // TODO : AsyncTask 식으로 다 만들기
    public void updateWater(String date) {
        try{
            Log.d("DB", "updateWater: 전");
            new UpdateIntData(pdDao, date,"water").execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("DB", "updateWater: 완");

    }

    public void updateCoffee(String date) {
        pdEntity = pdDao.getPdByDate(date);
        Integer cup = 473;   //cup SharedPreferences에서 가져오기
        Integer coffee = pdEntity.getCoffee();
        pdEntity.setTea(coffee+cup);
        pdDao.update(pdEntity);
    }

    public void updateTea(String date) {
        pdEntity = pdDao.getPdByDate(date);
        Integer cup = 473;   //cup SharedPreferences에서 가져오기
        Integer tea = pdEntity.getTea();
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

    public static class CheckPdByDate extends AsyncTask<Void, Void, Boolean> {
        private PdDao pdAsyncTaskDao;
        private String date;

        CheckPdByDate(PdDao pdAsyncTaskDao, String date) {
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

    public static class GetIntData extends AsyncTask<Void, Void, Integer> {
        private PdDao pdAsyncTaskDao;
        String columnName;
        String date;
        GetIntData(PdDao pdAsyncTaskDao, String columnName, String date) {
            this.pdAsyncTaskDao = pdAsyncTaskDao;
            this.columnName = columnName;
            this.date = date;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            switch (columnName) {
                case "water" :
                    return pdAsyncTaskDao.getWater(date);
                case "coffee" :
                    return pdAsyncTaskDao.getCoffee(date);
                case "tea" :
                    return pdAsyncTaskDao.getTea(date);
                case "peeCnt" :
                    return pdAsyncTaskDao.getPeeCnt(date);
                case "fecesCnt" :
                    return pdAsyncTaskDao.getFecesCnt(date);
                case "peeAvg" :
                    return pdAsyncTaskDao.getPeeAvg();
                case "fecesAvg":
                    return pdAsyncTaskDao.getFecesAvg();
            }

            return null;
        }
    }

    public static class UpdateIntData extends AsyncTask<Void, Void, Boolean> {
        private PdDao pdAsyncTaskDao;
        String columnName;
        PdEntity pdEntity;
        String date;
        Integer cup;
        Integer data;

        UpdateIntData(PdDao pdAsyncTaskDao, String date, String columnName) {
            this.pdAsyncTaskDao = pdAsyncTaskDao;
            this.columnName = columnName;
            cup = 473;
            data = 0;
            //TODO : CUP sharedPreferences에서 가져오기
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                this.pdEntity = pdAsyncTaskDao.getPdByDate(date);   //이게 제대로 안됨
                Log.d("DB", "doInBackground: pdEntity load");
                switch (columnName) {
                    case "water" :
                        data = pdEntity.getWater();
                        pdEntity.setWater(data+cup);
                        pdAsyncTaskDao.update(pdEntity);
                        break;
                    case "coffee" :
                        data = pdEntity.getCoffee();
                        pdEntity.setCoffee(data+cup);
                        pdAsyncTaskDao.update(pdEntity);
                        break;
                    case "tea" :
                        data = pdEntity.getTea();
                        pdEntity.setTea(data+cup);
                        pdAsyncTaskDao.update(pdEntity);
                        break;
                    case "peeCnt" :
                        data = pdEntity.getPeeCnt();
                        pdEntity.setPeeCnt(++data);
                        pdAsyncTaskDao.update(pdEntity);
                        break;
                    case "fecesCnt" :
                        data = pdEntity.getFecesCnt();
                        pdEntity.setFecesCnt(++data);
                        pdAsyncTaskDao.update(pdEntity);
                        break;
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

}
