package com.example.plouf.ui.calendar;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.plouf.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateLongClickListener;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.threeten.bp.LocalDate;

//TODO : 캘린더 기능 넣기(날짜 선택시 클릭 이벤트 만들기, 점으로 이벤트 표시
//TODO : 디비에 그 날짜에 별 몇개인지도 저장 해야할듯.

public class CalendarFragment extends Fragment  implements OnDateSelectedListener, OnMonthChangedListener, OnDateLongClickListener {

    private CalendarViewModel calendarViewModel;
    public MaterialCalendarView cv_calendar;
    private Context context;
    public EventDecorator eventDecorator;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        final TextView textView = root.findViewById(R.id.text_calendar);
        calendarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        context = container.getContext();

        cv_calendar  = root.findViewById(R.id.cv_calendar);

        ArrayList<CalendarDay> calendarDayList = new ArrayList<>();
        calendarDayList.add(CalendarDay.today());
        calendarDayList.add(CalendarDay.from(2020, 11, 25));

//        Toast.makeText(context, cv_calendar.getSelectedDate().toString(), Toast.LENGTH_SHORT).show();
        EventDecorator eventDecorator = new EventDecorator(android.R.color.darker_gray, calendarDayList);
        cv_calendar.addDecorator(eventDecorator);


        CalendarDay day = CalendarDay.today();
        eventDecorator.shouldDecorate(day);
        if(cv_calendar.getSelectedDate() !=null ){
            onDateSelected(cv_calendar,cv_calendar.getSelectedDate(),true);
        }



        return root;
    }


    @Override
    public void onDateLongClick(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date) {
        Toast.makeText(context, date.getDate().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        Toast.makeText(context, date.getDate().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

    }
}