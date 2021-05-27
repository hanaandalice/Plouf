package com.example.plouf.ui.history;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.example.plouf.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;


/*####################################################################################
 *형태 : Class
 * 모듈ID : EventDecorator
 * 설명 : dotNum 별로 다른 drawable 뷰에 데코레이트 해줌
 * */
public class EventDecorator implements DayViewDecorator {
    private final HashSet<CalendarDay> dates;
    private Context context;
    private Integer dotNum;

    public EventDecorator( Collection<CalendarDay> dates, Context context, Integer waterAc) {
        this.dates = new HashSet<>(dates);
        this.context = context;
        dotNum = waterAc;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }


    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : EventDecorator
     * 반환값 : 없음
     * 설명 : dotNum 별로 drawable 세팅.
     */
    @Override
    public void decorate(DayViewFacade view) {
        Drawable drawable;
        switch (dotNum) {
            case 1 :
                drawable = ContextCompat.getDrawable(context, R.drawable.temp_waterac_1);
                view.setBackgroundDrawable(drawable);
                break;
            case 2 :
                drawable = ContextCompat.getDrawable(context, R.drawable.temp_waterac_2);
                view.setBackgroundDrawable(drawable);
                break;
            case 3 :
                drawable = ContextCompat.getDrawable(context, R.drawable.temp_waterac_3);
                view.setBackgroundDrawable(drawable);
                break;
            case 4:
                drawable = ContextCompat.getDrawable(context, R.drawable.temp_waterac_4);
                view.setBackgroundDrawable(drawable);
                break;

            case 5 :
                drawable = ContextCompat.getDrawable(context, R.drawable.temp_waterac_5);    //TODO : 별 5개 이미지는 두개가 연결되는 느낌으로 만들기.
                view.setBackgroundDrawable(drawable);
                break;
        }

    }
}
