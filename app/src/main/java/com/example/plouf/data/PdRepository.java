package com.example.plouf.data;

import android.app.Application;
import android.app.AsyncNotedAppOp;
import android.os.AsyncTask;
import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;



/*####################################################################################
 *형태 : Class
 * 모듈ID : PdRepository
 * 설명 : pd db에 대한 접근, 데이터 추가, 제거 관리
 * */

public class PdRepository {
    private  PdDao pdDao;
    PdEntity pdEntity;
    private static Integer UPDATE_MODE_ADD = 1;
    private static Integer UPDATE_MODE_SUB = 0;

    public PdRepository(Application application) {
        pdDao = AppDatabase.getInstance(application).pdDao();
        pdEntity = new PdEntity();
        Log.d("DB", "PdRepository: 초기화");
    }

    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : PdRepository
     * 반환값 : List<PdEntity>
     * 설명 : pd 데이터 전체 반환
     */
    public List<PdEntity> getAllPds() {
        List<PdEntity> pds = null;
        try{
            pds = new GetAllPdsAsyncTask(pdDao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return pds;
    }

    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : PdRepository
     * 반환값 : boolean
     * 설명 : 해당 날짜의 데이터가 있는지 체크 요청하고 결과 리턴
     */
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

    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : PdRepository
     * 반환값 : 없음
     * 설명 : pdEntity 값 삽입, 삭제, 업데이트
     */
    public void insert(PdEntity pdEntity) {
        pdDao.insert(pdEntity);
    }

    public void delete(PdEntity pdEntity) {
        pdDao.delete(pdEntity);
    }

    public void update(PdEntity pdEntity) {
        update(pdEntity);
    }




    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : PdRepository
     * 반환값 : Integer
     * 설명 : GetIntData 수행 요청
     */
    public Integer getWater(String date) {
        try{
            return new GetIntData(pdDao, "water", date).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getCoffee(String date) {
        try{
            return new GetIntData(pdDao, "coffee", date).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getTea(String date) {
        try{
            return new GetIntData(pdDao, "tea", date).execute().get();
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
            return new GetIntData(pdDao, "peeAvg", date).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getFecesAvg(String date) {
        try{
            return new GetIntData(pdDao, "fecesAvg", date).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getCalendarDay(String dateMonth, Integer waterAc) {
        try{
            Log.d("DB", "getCalendarDay: hey");
            return new GetCalendarDay(pdDao, dateMonth, waterAc).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d("DB", "getCalendarDay: catch");
            e.printStackTrace();
            return null;
        }
    }



    public void insertTest() {
        pdDao.insertTest();
    }

    public void deleteByDate(String date) {
        pdDao.deleteByDate(date);
    }   //TODO : delete 기능 구현..?


    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : PdRepository
     * 반환값 : 없음
     * 설명 : 초기 데이터 삽입
     */
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


    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : PdRepository
     * 반환값 : 없음
     * 설명 : update - 증가 수행
     */
    public void addWater(String date, Integer cup) {
        try{
            Log.d("DB", "updateWater: 전");
            new UpdateIntData(pdDao, date,"water", UPDATE_MODE_ADD, cup).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("DB", "updateWater: 완");

    }

    public void addCoffee(String date, Integer cup) {
        try{
            Log.d("DB", "updateCoffee: 전");
            new UpdateIntData(pdDao, date,"coffee", UPDATE_MODE_ADD, cup).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("DB", "updateCoffee: 완");
    }

    public void addTea(String date, Integer cup) {
        try{
            Log.d("DB", "updateTea: 전");
            new UpdateIntData(pdDao, date,"tea", UPDATE_MODE_ADD, cup).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("DB", "updateTea: 완");
    }

    public void addPeeCnt(String date, Integer cup) {
        try{
            Log.d("DB", "updatePeeCnt: 전");
            new UpdateIntData(pdDao, date,"peeCnt", UPDATE_MODE_ADD, cup).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("DB", "updatePeeCnt: 완");
    }

    public void addFecesCnt(String date, Integer cup) {
        try{
            Log.d("DB", "updateFecesCnt: 전");
            new UpdateIntData(pdDao, date,"fecesCnt",UPDATE_MODE_ADD, cup).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("DB", "updateFecesCnt: 완");
    }


    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : PdRepository
     * 반환값 : 없음
     * 설명 : update - 감소 수행
     */
    public void subWater(String date, Integer cup) {
        try{
            Log.d("DB", "updateWater: 전");
            new UpdateIntData(pdDao, date,"water", UPDATE_MODE_SUB, cup).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("DB", "updateWater: 완");

    }

    public void subCoffee(String date, Integer cup) {
        try{
            Log.d("DB", "updateCoffee: 전");
            new UpdateIntData(pdDao, date,"coffee", UPDATE_MODE_SUB, cup).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("DB", "updateCoffee: 완");
    }

    public void subTea(String date, Integer cup) {
        try{
            Log.d("DB", "updateTea: 전");
            new UpdateIntData(pdDao, date,"tea", UPDATE_MODE_SUB, cup).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("DB", "updateTea: 완");
    }

    public void subPeeCnt(String date, Integer cup) {
        try{
            Log.d("DB", "updatePeeCnt: 전");
            new UpdateIntData(pdDao, date,"peeCnt", UPDATE_MODE_SUB, cup).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("DB", "updatePeeCnt: 완");
    }

    public void subFecesCnt(String date, Integer cup) {
        try{
            Log.d("DB", "updateFecesCnt: 전");
            new UpdateIntData(pdDao, date,"fecesCnt",UPDATE_MODE_SUB, cup).execute().get();
        } catch(ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("DB", "updateFecesCnt: 완");
    }

    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : PdRepository
     * 반환값 : 없음
     * 설명 : 물 섭취 달성도 설정 요청
     */
    public void setWaterAc(String date, Integer waterAc) {
        try{
            new SetWaterAc(pdDao, date, waterAc).execute().get();
            Log.d("DB", "setWaterAc: 완");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }





    /*####################################################################################
     *형태 : Class
     * 모듈ID : GetAllPdsAsyncTask
     * 설명 : pd값 다 들고와서 리스트 형태로 반환
     * */
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

    /*####################################################################################
     *형태 : Class
     * 모듈ID : CheckPdByDate
     * 설명 : 해당 date의 기록 있는지 체크
     * */
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


    /*####################################################################################
     *형태 : Class
     * 모듈ID : InsertInitDate
     * 설명 : 초기 물 연속 달성일 데이터 입력
     * */
    private static class InsertInitDate extends AsyncTask<Void, Void, Boolean> {
        private PdDao pdAsyncTaskDao;
        private PdEntity pdEntity;

        InsertInitDate(PdDao pdAsyncTaskDao, PdEntity pdEntity) {
            this.pdAsyncTaskDao = pdAsyncTaskDao;
            this.pdEntity = pdEntity;
//            Log.d("DB", "InsertInitDate: 입장");
        }


        /*-------------------------------------------------
         *형태 : Method
         * 소유자 : InsertInitDate
         * 반환값 : Boolean
         * 설명 : 물 연속 달성일 세팅
         */
        @Override
        protected Boolean doInBackground(Void... voids) {
            Integer pastAcCnt = 1;
            Integer pastWaterAc = 0;

            pastWaterAc = pdAsyncTaskDao.getWaterAc();

            if(pastWaterAc == 5){
                pastAcCnt = pdAsyncTaskDao.getAcCnt();
                pdEntity.setAcCnt(pastAcCnt+1);
            } else {
                pdEntity.setAcCnt(pastAcCnt);
            }
            pdAsyncTaskDao.insert(pdEntity);
            return  true;
        }
    }


    /*####################################################################################
     *형태 : Class
     * 모듈ID : GetIntData
     * 설명 : int 타입 데이터 가져오기
     * */
    private static class GetIntData extends AsyncTask<Void, Void, Integer> {
        private PdDao pdAsyncTaskDao;
        String columnName;
        String date;
        GetIntData(PdDao pdAsyncTaskDao, String columnName, String date) {
            this.pdAsyncTaskDao = pdAsyncTaskDao;
            this.columnName = columnName;
            this.date = date;
        }

        /*-------------------------------------------------
         *형태 : Method
         * 소유자 : GetIntData
         * 반환값 : Integer
         * 설명 : 물, 커피, 차, 소변, 대변 케이스 별로 값 가져오기
         */
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

    /*####################################################################################
     *형태 : Class
     * 모듈ID : UpdateIntData
     * 설명 : Int type data 업데이트
     * */
    private static class UpdateIntData extends AsyncTask<Void, Void, Boolean> {
        private PdDao pdAsyncTaskDao;
        String columnName;
        PdEntity pdEntity;
        String date;
        Integer mode;
        Integer cup;
        Integer data;

        /*-------------------------------------------------
         *형태 : Method
         * 소유자 : UpdateIntData
         * 반환값 : 없음
         * 설명 : 변수 초기화
         */
        UpdateIntData(PdDao pdAsyncTaskDao, String date, String columnName, Integer mode, Integer cup) {
            this.pdAsyncTaskDao = pdAsyncTaskDao;
            this.columnName = columnName;
            this.date = date;
            this.mode = mode;
            this.cup = cup;
            data = 0;
            //TODO : CUP sharedPreferences에서 가져오기 걍 cup도 외부에서 받자.
        }

        /*-------------------------------------------------
         *형태 : Method
         * 소유자 : UpdateIntData
         * 반환값 : Boolean
         * 설명 : 물, 커피, 차, 소변, 대변 케이스 별로 값 가져오고 해당 날짜 엔티티 가져와서
         *        엔티티 설정 후 업데이트(증가, 감소)
         */
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



    /*####################################################################################
     *형태 : Class
     * 모듈ID : SetWaterAc
     * 설명 : 물 성취량 디비에 저장(Calendar에서 날짜별로 별 찍을때 사용)
     * */
    private class SetWaterAc extends AsyncTask<Void,Void, Boolean> {
        private PdDao pdAsyncTaskDao;
        private String date;
        private Integer waterAc;
        private PdEntity pdEntity;

        SetWaterAc(PdDao pdAsyncTaskDao, String date, Integer waterAc) {
            this.pdAsyncTaskDao = pdAsyncTaskDao;
            this.date = date;
            this.waterAc = waterAc;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            pdEntity = pdAsyncTaskDao.getPdByDate(date);
            pdEntity.setWaterAc(waterAc);
            pdAsyncTaskDao.update(pdEntity);
            Log.d("DB", "doInBackground: waterAC Set");

            return true;
        }
    }


    /*####################################################################################
     *형태 : Class
     * 모듈ID : GetCalendarDay
     * 설명 : 해당 월의 waterAc 별 캘린더 데이 갖고 와서 정리하고 반환
     * */
    private class GetCalendarDay extends AsyncTask<Void, Void, List<String>> {
        private PdDao pdAsyncTaskDao;
        private String dateMonth;
        private String waterAc;
        private List<String> calendarDays;

        GetCalendarDay(PdDao pdAsyncTaskDao, String dateMonth, Integer waterAc){
            this.pdAsyncTaskDao = pdAsyncTaskDao;
            this.dateMonth = dateMonth;
            this.waterAc = Integer.toString(waterAc);
            Log.d("DB", "GetCalendarDay: 진입");
        }


        @Override
        protected List<String> doInBackground(Void... voids) {
            Log.d("DB", "doInBackground: calendarDays 전");
            try{
                calendarDays = pdAsyncTaskDao.getDatebyWaterAc(waterAc, dateMonth);
                Log.d("DB", "doInBackground: calendarDays return"+calendarDays);
                return calendarDays;
            } catch(Exception e){
                Log.d("DB", "doInBackground: calendarDays catch");
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<String> calendarDays) {
            Log.d("DB", "onPostExecute: 결과 보내기");
            super.onPostExecute(calendarDays);
        }
    }

}
