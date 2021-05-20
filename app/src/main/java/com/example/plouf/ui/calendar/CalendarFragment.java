package com.example.plouf.ui.calendar;

import android.content.AsyncQueryHandler;
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

/*####################################################################################
 *형태 : Class
 * 모듈ID : CalendarFragment
 * 설명 : Calendar UI
 * 캘린더 점 있는 애들 점 찍기.(waterAc 사용해서
 * */

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
        final TextView textView = root.findViewById(R.id.tv_calendar);
        calendarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        context = container.getContext();

        cv_calendar  = root.findViewById(R.id.cv_calendar);

        ArrayList<CalendarDay> calendarDayList = new ArrayList<>();
        //TODO : 디비에서 해당 월 날짜 정보 가져와서 캘린더 리스트에 add 하고 EventDecorater에 추가하는 부분 비동기 식으로 클래스 작성하기
        calendarDayList.add(CalendarDay.today());
        calendarDayList.add(CalendarDay.from(2020, 11, 25));


        EventDecorator eventDecorator = new EventDecorator(android.R.color.darker_gray, calendarDayList, getContext(), 1);
        cv_calendar.addDecorator(eventDecorator);


        CalendarDay day = CalendarDay.today();
        eventDecorator.shouldDecorate(day);


        cv_calendar.setOnDateChangedListener(this);
        cv_calendar.setOnDateLongClickListener(this);


        return root;
    }


    @Override
    public void onDateLongClick(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date) {
        Toast.makeText(context, "This is "+date.getDate().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        Toast.makeText(context, date.getDate().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

    }

    //디비에서 해당 월 날짜 정보 가져와서 캘린더 리스트에 add 하고 EventDecorater에 추가하는 부분 비동기 식으로 클래스 작성하기
    private class AddDecorate extends AsyncTask<Void, Void, Boolean> {

        AddDecorate() {
            ArrayList<CalendarDay> calendarDayList = new ArrayList<>();
            //TODO : 디비에서 해당 월 날짜 정보 가져와서 캘린더 리스트에 add 하고 EventDecorater에 추가하는 부분 비동기 식으로 클래스 작성하기
            calendarDayList.add(CalendarDay.today());
            calendarDayList.add(CalendarDay.from(2020, 11, 25));EventDecorator eventDecorator = new EventDecorator(android.R.color.darker_gray, calendarDayList, getContext(), 1);
            cv_calendar.addDecorator(eventDecorator);
        }


        @Override
        protected Boolean doInBackground(Void... voids) {



            return null;
        }
    }

}