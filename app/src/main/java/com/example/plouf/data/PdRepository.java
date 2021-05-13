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
    private static Integer UPDATE_MODE_ADD = 1;
    private static Integer UPDATE_MODE_SUB = 0;



    public PdRepository(Application application) {
        pdDao = AppDatabase.getInstance(application).pdDao();
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


    //getData
    public Integer getWater(String date) {
        try{
            return new GetIntData(pdDao, "water", date).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getPeeCnt(String date) {
        try{
            return new GetIntData(pdDao, "peeCnt", date).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getFecesCnt(String date) {
        try{
            return new GetIntData(pdDao, "fecesCnt", date).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;    }


    public Integer getWaterAc(String date) {
        try{
            return new GetIntData(pdDao, "waterAc", date).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getAcCnt(String date) {
        try{
            return new GetIntData(pdDao, "acCnt", date).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getPeeAvg(String date) {
        try{
            return new GetIntData(pdDao, "pddAvg", date).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getFecesAvg(String date) {
        try{
            return new GetIntData(pdDao, "peeCnt", date).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }



    public void insertTest() {
        pdDao.insertTest();
    }

    public void deleteByDate(String date) {
        pdDao.deleteByDate(date);
    }   //TODO : delete 기능 구현..?


    //초기 데이터 삽입. home에서 제일 먼저 date 있나 확인 하고 없으면 실행 시키기
    public void insertInitDate(String date) {
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

    // Update data : add, sub
    public void addWater(String date) {
        try{
            Log.d("DB", "updateWater: 전");
            new UpdateIntData(pdDao, date,"water", UPDATE_MODE_ADD).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("DB", "updateWater: 완");

    }

    public void subWater(String date) {
        try{
            Log.d("DB", "updateWater: 전");
            new UpdateIntData(pdDao, date,"water", UPDATE_MODE_SUB).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("DB", "updateWater: 완");

    }

    public void addCoffee(String date) {
        try{
            Log.d("DB", "updateCoffee: 전");
            new UpdateIntData(pdDao, date,"coffee", UPDATE_MODE_ADD).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("DB", "updateCoffee: 완");
    }

    public void subCoffee(String date) {
        try{
            Log.d("DB", "updateCoffee: 전");
            new UpdateIntData(pdDao, date,"coffee", UPDATE_MODE_SUB).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("DB", "updateCoffee: 완");
    }

    public void addTea(String date) {
        try{
            Log.d("DB", "updateTea: 전");
            new UpdateIntData(pdDao, date,"tea", UPDATE_MODE_ADD).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("DB", "updateTea: 완");
    }

    public void subTea(String date) {
        try{
            Log.d("DB", "updateTea: 전");
            new UpdateIntData(pdDao, date,"tea", UPDATE_MODE_SUB).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("DB", "updateTea: 완");
    }


    public void addPeeCnt(String date) {
        try{
            Log.d("DB", "updatePeeCnt: 전");
            new UpdateIntData(pdDao, date,"peeCnt", UPDATE_MODE_ADD).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("DB", "updatePeeCnt: 완");
    }


    public void subPeeCnt(String date) {
        try{
            Log.d("DB", "updatePeeCnt: 전");
            new UpdateIntData(pdDao, date,"peeCnt", UPDATE_MODE_SUB).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("DB", "updatePeeCnt: 완");
    }

    public void addFecesCnt(String date) {
        try{
            Log.d("DB", "updateFecesCnt: 전");
            new UpdateIntData(pdDao, date,"fecesCnt",UPDATE_MODE_ADD).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("DB", "updateFecesCnt: 완");
    }

    public void subFecesCnt(String date) {
        try{
            Log.d("DB", "updateFecesCnt: 전");
            new UpdateIntData(pdDao, date,"fecesCnt",UPDATE_MODE_SUB).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("DB", "updateFecesCnt: 완");
    }


    //AsyncTask
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

    //그날의 기록 있는지 체크
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
//            Log.d("DB", "InsertInitDate: 입장");
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Integer pastAcCnt = 0;
            if(pdAsyncTaskDao.getWaterAc() == null || pdAsyncTaskDao.getWaterAc() == 0){    //null이거나 0이면 past 는 0
                pastAcCnt = 0;
            } else if(pdAsyncTaskDao.getWaterAc() == 1){
                pastAcCnt = pdAsyncTaskDao.getAcCnt();
            }

//            Log.d("DB", "doInBackground: 1");
//            Integer pastAcCnt = 1;
            pdEntity.setAcCnt(pastAcCnt+1);
//            Log.d("DB", "doInBackground: 2");
            pdAsyncTaskDao.insert(pdEntity);
            return  true;
        }
    }

    //int 타입 데이터 가져오기
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
                case "waterAc" :
                    return pdAsyncTaskDao.getWaterAc();
                case "acCnt" :
                    return pdAsyncTaskDao.getAcCnt();
                case "peeAvg" :
                    return pdAsyncTaskDao.getPeeAvg();
                case "fecesAvg":
                    return pdAsyncTaskDao.getFecesAvg();
            }

            return null;
        }
    }

    //Int type data 업데이트
    public static class UpdateIntData extends AsyncTask<Void, Void, Boolean> {
        private PdDao pdAsyncTaskDao;
        String columnName;
        PdEntity pdEntity;
        String date;
        Integer mode;
        Integer cup;
        Integer data;

        UpdateIntData(PdDao pdAsyncTaskDao, String date, String columnName, Integer mode) {
            this.pdAsyncTaskDao = pdAsyncTaskDao;
            this.columnName = columnName;
            this.date = date;
            this.mode = mode;
            cup = 473;
            data = 0;
            //TODO : CUP sharedPreferences에서 가져오기
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                pdEntity = pdAsyncTaskDao.getPdByDate(date);//이게 제대로 안됨
                Log.d("DB", "doInBackground: pdEntity load");
                switch (columnName) {
                    case "water" :
                        data = pdEntity.getWater();
                        if(mode == UPDATE_MODE_ADD){
                            pdEntity.setWater(data+cup);
                        } else if(mode == UPDATE_MODE_SUB) {
                            pdEntity.setWater(data-cup);
                        }
                        pdAsyncTaskDao.update(pdEntity);
                        break;
                    case "coffee" :
                        data = pdEntity.getCoffee();
                        if(mode == UPDATE_MODE_ADD){
                            pdEntity.setCoffee(data+cup);
                        } else if(mode == UPDATE_MODE_SUB) {
                            pdEntity.setCoffee(data-cup);
                        }
                        pdAsyncTaskDao.update(pdEntity);
                        break;
                    case "tea" :
                        data = pdEntity.getTea();
                        if(mode == UPDATE_MODE_ADD){
                            pdEntity.setTea(data+cup);
                        } else if(mode == UPDATE_MODE_SUB) {
                            pdEntity.setTea(data-cup);
                        }
                        pdAsyncTaskDao.update(pdEntity);
                        break;
                    case "peeCnt" :
                        data = pdEntity.getPeeCnt();
                        if(mode == UPDATE_MODE_ADD){
                            pdEntity.setPeeCnt(++data);
                        } else if(mode == UPDATE_MODE_SUB) {
                            pdEntity.setPeeCnt(--data);
                        }
                        pdAsyncTaskDao.update(pdEntity);
                        break;
                    case "fecesCnt" :
                        data = pdEntity.getFecesCnt();
                        if(mode == UPDATE_MODE_ADD){
                            pdEntity.setFecesCnt(++data);
                        } else if(mode == UPDATE_MODE_SUB) {
                            pdEntity.setFecesCnt(--data);
                        }
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
