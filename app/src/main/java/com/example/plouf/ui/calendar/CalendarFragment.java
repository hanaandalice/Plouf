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
 * waterAc 사용해서 캘린더 점 있는 애들 점 찍기.
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


        new AddDecorate().execute();    //캘린더뷰에 waterAc 별로 별 찍기 실행



        cv_calendar.setOnDateChangedListener(this);
        cv_calendar.setOnDateLongClickListener(this);


        return root;
    }


    @Override
    public void onDateLongClick(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date) {
        Toast.makeText(context, "This is "+date.getDate().toString(), Toast.LENGTH_SHORT).show();
    }


    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : CalendarFragment
     * 반환값 : 없음
     * 설명 : 날짜 클릭 이벤트
     */
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        Toast.makeText(context, date.getDate().toString(), Toast.LENGTH_SHORT).show();
        //TODO : 여기에 그래프 가져와서 그래프 보여주는 부분 작성.


    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

    }



    /*####################################################################################
     *형태 : Class
     * 모듈ID : AddDecorate
     * 설명 : 해당 월 날짜에 해당하는 waterAc 별로 캘린더 리스트에 add 하고 EventDecorator 추가 하여 캘린더에 별점 보여즘
     * */
    private class AddDecorate extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            ArrayList<CalendarDay> calendarDayList = new ArrayList<>();
            ArrayList<String> tempCalendarDayList = new ArrayList<>();
            calendarDayList.add(CalendarDay.today());

            for(int waterAc = 1; waterAc <6; waterAc++) {
                tempCalendarDayList = calendarViewModel.getCalendarDayList(CalendarDay.today().getMonth(), waterAc);
                Log.d("DB", "doInBackground: 리스트1  "+tempCalendarDayList);
                for(String day : tempCalendarDayList) {
                    //캘린더 데이 리스트에 넣기
                    String days[] = day.split("-");
                    Integer d = Integer.parseInt(days[2]);
                    calendarDayList.add(CalendarDay.from(CalendarDay.today().getYear(), CalendarDay.today().getMonth(), d)); //형식
                }
                cv_calendar.addDecorators(new EventDecorator(calendarDayList, getContext(), waterAc));
                calendarDayList.clear();
            }

            return null;
        }
    }

}