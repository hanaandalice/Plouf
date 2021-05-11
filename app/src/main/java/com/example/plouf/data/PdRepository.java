package com.example.plouf.data;

import android.app.Application;

import java.util.List;

//여기서 pd db에 대한 접근, 데이터 추가, 제거, 등등 관리
public class PdRepository {
    private  PdDao pdDao;

    public PdRepository(Application application) {
        pdDao = AppDatabase.getInstance(application).pdDao();
    }

    public List<PdEntity> getAllPds(){
        List<PdEntity> pds = null;

        return pds;
    }
}
