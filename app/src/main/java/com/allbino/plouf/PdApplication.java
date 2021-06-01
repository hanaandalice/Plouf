package com.allbino.plouf;

import android.app.Application;

/*####################################################################################
 *형태 : Class
 * 모듈ID : PdApplication
 * 설명 : Application Instances 반환
 * */
public class PdApplication extends Application {
    PdApplication appInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
    }

    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : PdApplication
     * 반환값 : LiveData<String>, Integer, String, float
     * 설명 : Application Instances 반환(DB 사용시 PdRepository에서 필요)
     */
    public PdApplication getAppInstance() {
        return appInstance;
    }
}
