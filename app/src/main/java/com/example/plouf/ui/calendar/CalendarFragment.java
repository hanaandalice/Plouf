package com.example.plouf.ui.calendar;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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


        new AddDecorate().execute();    //캘린더뷰에 waterAc 별로 별 찍기




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

        }

        //TODO : 디비에서 해당 월 날짜 정보 가져와서 캘린더 리스트에 add 하고 EventDecorater에 추가하는 부분 비동기 식으로 클래스 작성하기
        @Override
        protected Boolean doInBackground(Void... voids) {
            ArrayList<CalendarDay> calendarDayList = new ArrayList<>();
            ArrayList<String> tempCalendarDayList = new ArrayList<>();
            calendarDayList.add(CalendarDay.today());
//            for(String s : tempCalendarDayList){//왜 안되나요?
//                Log.d("DB", "doInBackground: for 문 tempcalendar");
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                Calendar calendar = Calendar.getInstance();
//                try {
//                    Date date = formatter.parse(s);
//                    calendar.setTime(date);
//                    CalendarDay day = CalendarDay.today();
//                    calendarDayList.add(day);
//                    Log.d("DB", "doInBackground: day"+day);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }

            calendarDayList.add(CalendarDay.from(2021,5,16)); // 이런식으로 년 일월 하나하나 다 넣어야 함.
            //TODO :  for 문으로 day만 가져와서 for문 돌리면서 넣기


            calendarDayList.add(CalendarDay.from(2020, 11, 25));
            EventDecorator eventDecorator = new EventDecorator(calendarDayList, getContext(), 1);
            cv_calendar.addDecorator(eventDecorator);
            calendarDayList.clear();    //하면 지워짐. 한 종류 끝나면 clear 하고 add 하면 됨.
            calendarDayList.add(CalendarDay.from(2021, 5, 13));
            calendarDayList.add(CalendarDay.from(2021,5,1));
            cv_calendar.addDecorators(new EventDecorator( calendarDayList, getContext(), 3));
            calendarDayList.clear();
            calendarDayList.add(CalendarDay.from(2021, 5, 2));
            calendarDayList.add(CalendarDay.from(2021,5,3));
            cv_calendar.addDecorators(new EventDecorator(calendarDayList, getContext(), 5));
            tempCalendarDayList = calendarViewModel.getCalendarDayList(Integer.toString(CalendarDay.today().getMonth()), 5);    //이방법 시간이 너무 오래걸림 렉걸린다.
            Log.d("DB", "doInBackground: 리스트  "+tempCalendarDayList);
            //dayList 에서 day만 가지고 와서 쓰기. Int 형 어레이로 가져와서 바로 포문에서 돌리면서 넣을 수 있도록 하기.

            return null;
        }
    }

}