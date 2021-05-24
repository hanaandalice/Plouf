package com.example.plouf.ui.calendar;

import android.content.Context;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.plouf.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateLongClickListener;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.List;


/*####################################################################################
 *형태 : Class
 * 모듈ID : CalendarFragment
 * 설명 : Calendar UI
 * waterAc 사용해서 캘린더 점 있는 애들 점 찍기.
 * */
public class CalendarFragment extends Fragment  implements OnDateSelectedListener, OnMonthChangedListener, OnDateLongClickListener, OnChartValueSelectedListener {

    private CalendarViewModel calendarViewModel;
    public MaterialCalendarView cv_calendar;
    public TextView tv_dailyToilet;
    public TextView tv_chart;
    public TextView tv_tip;
    private Context context;
    public HorizontalBarChart chart;
    private String today;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        final TextView tv_avgToilet = root.findViewById(R.id.tv_avgToilet);
        calendarViewModel.getAvgText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tv_avgToilet.setText(s);
            }
        });
        tv_dailyToilet = root.findViewById(R.id.tv_dailyToilet);
        tv_dailyToilet.setVisibility(View.INVISIBLE);

        tv_chart = root.findViewById(R.id.tv_chart);
        tv_chart.setText("일일 음수량 그래프  ("+today+")");

        tv_tip = root.findViewById(R.id.tv_tip);
        tv_tip.setVisibility(View.INVISIBLE);

        context = container.getContext();

        cv_calendar  = root.findViewById(R.id.cv_calendar);
        today = CalendarDay.today().getDate().toString();


        new AddDecorate().execute();    //캘린더뷰에 waterAc 별로 별 찍기 실행

        chart = root.findViewById(R.id.waterChart);
        chart.setOnChartValueSelectedListener(this);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);
        chart.setMaxVisibleValueCount(3);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);

        XAxis xl = chart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(10f);

        YAxis yl = chart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f);

        YAxis yr = chart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f);

        chart.setFitBars(true);


        setChart(today);   //기본 차트 설정 : 오늘 일자


        cv_calendar.setOnDateChangedListener(this);
        cv_calendar.setOnDateLongClickListener(this);



        //캘린더 기본 선택 오늘 날짜


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
        setChart(date.getDate().toString());

    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

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


    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : CalendarFragment
     * 반환값 : 없음
     * 설명 : 차트 데이터 설정
     */
    private void setChart(String today) {
        ArrayList<BarEntry> datas = new ArrayList<>();
        ArrayList<Integer> temp = calendarViewModel.getData(today);
        tv_chart.setText("일일 음수량 그래프  ("+today+")");
        if(temp == null) {
            tv_dailyToilet.setVisibility(View.VISIBLE);
            tv_dailyToilet.setText("해당 일자의 데이터가 없습니다.");
            tv_chart.setText("해당 일자의 데이터가 없습니다.");
            chart.setVisibility(View.INVISIBLE);
            tv_tip.setVisibility(View.INVISIBLE);


        } else {
            chart.setVisibility(View.VISIBLE);
            tv_tip.setVisibility(View.VISIBLE);
            tv_tip.setText("커피는 섭취량의 2배 차는 1.5배의 수분을 배출시킵니다.");
            datas.add(new BarEntry(0, temp.get(0)));
            datas.add(new BarEntry(1, temp.get(1)));
            datas.add(new BarEntry(2, temp.get(2)));
            tv_dailyToilet.setVisibility(View.VISIBLE);
            tv_dailyToilet.setText("일일 소변 횟수 : " + temp.get(3) + " 회         일일 대변 횟수 : " + temp.get(4) + " 회");


            BarDataSet set1;


            if (chart.getData() != null &&
                    chart.getData().getDataSetCount() > 0) {
                set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
                set1.setValues(datas);
                chart.getData().notifyDataChanged();
                chart.notifyDataSetChanged();
            } else {
                set1 = new BarDataSet(datas, "파랑 : 물 | 노랑 : 커피 | 초록 : 차");

                set1.setDrawIcons(false);

                int color1 = ContextCompat.getColor(getContext(), android.R.color.holo_blue_light); //물
                int color2 = ContextCompat.getColor(getContext(), android.R.color.holo_orange_light);    //커피
                int color3 = ContextCompat.getColor(getContext(), android.R.color.holo_green_light);    //차

                List<Fill> gradientFills = new ArrayList<>();
                gradientFills.add(new Fill(color1));
                gradientFills.add(new Fill(color2));
                gradientFills.add(new Fill(color3));

                set1.setColors(color1, color2, color3);

                ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);

                BarData data = new BarData(dataSets);
                data.setValueTextSize(10f);
                data.setBarWidth(0.4f);

                chart.setData(data);
            }

        }
    }

}