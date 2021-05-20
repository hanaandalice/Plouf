package com.example.plouf.ui.calendar;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.example.plouf.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

public class EventDecorator implements DayViewDecorator {
    private final int color;
    private final HashSet<CalendarDay> dates;
    private Context context;
    private Integer dotNum;

    public EventDecorator(int color, Collection<CalendarDay> dates, Context context, Integer waterAc) {
        this.color = color;
        this.dates = new HashSet<>(dates);
        this.context = context;
        dotNum = waterAc;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

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
                drawable = ContextCompat.getDrawable(context, R.drawable.temp_waterac_5);
                view.setBackgroundDrawable(drawable);
                break;
        }

    }
}
