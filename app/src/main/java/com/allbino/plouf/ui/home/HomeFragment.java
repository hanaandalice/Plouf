package com.allbino.plouf.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.allbino.plouf.R;

import static android.content.ContentValues.TAG;
import static com.allbino.plouf.R.*;

/*####################################################################################
 *형태 : Class
 * 모듈ID : HomeFragment
 * 설명 : Home UI
 * 물, 소변, 대변, 작은 물방울 이미지 onClick, longClick 이벤트 수행
 * 물방울 이미지 세팅 실행
 * */

public class HomeFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener{

    private HomeViewModel homeViewModel;
    public ImageView img_water;
    public ImageView img_water2;
    public ImageView img_waterGlide;

    public ImageView img_pee;
    public ImageView img_feces;
    public ImageView img_drink;
    public TextView tv_progress;
    public TextView tv_peeCnt;
    public TextView tv_fecesCnt;
    public TextView tv_coffee;
    public TextView tv_tea;
    private Context context;
    public TextView tv_waterState;
    private View root;
    private CharSequence[] dItems;
    public AlertDialog.Builder dDialog;
    public String dSelectItem;
    public Integer intSelectItem = 0;
    public String water, coffee, tea;
    public Integer waterNeed;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(layout.fragment_home, container, false);
        Log.d("pref", "onCreateView: waterNeed 전");
        context = container.getContext();

        initHome();
        Log.d("pref", "onCreateView: init 후");

        homeViewModel.getProgressTxt().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tv_progress.setText(s);
            }
        });


            tv_coffee.setText("커피 : "+homeViewModel.getCoffee()+"ml");
            tv_tea.setText("차 : "+homeViewModel.getTea()+"ml");



        tv_peeCnt.setText(homeViewModel.getPeeCnt().toString());
        tv_fecesCnt.setText(homeViewModel.getFecesCnt().toString());
        if (homeViewModel.waterNeed == 0) {
            tv_waterState.setText("체중과 컵용량을 설정 해주세요");
        } else {
            tv_waterState.setText(homeViewModel.getWaterState());  //마셔야 할 양, 마신 물 양 데이터 뷰모델에서 보내줘서 뷰모델에서 디비 작업
        }


        img_water.setOnClickListener(this);
        img_pee.setOnClickListener(this);
        img_feces.setOnClickListener(this);
        img_drink.setOnClickListener(this);

        img_drink.setOnLongClickListener((View.OnLongClickListener) this);
        img_pee.setOnLongClickListener((View.OnLongClickListener) this);
        img_feces.setOnLongClickListener((View.OnLongClickListener) this);
        img_water.setOnLongClickListener((View.OnLongClickListener) this);

        Log.d(TAG, "onCreateView: selectItem"+intSelectItem);

        return root;
    }

    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : HomeViewModel
     * 반환값 : 없음
     * 설명 : 물, 소변, 대변, 작은 물방울 이미지 onClick 이벤트
     */
    @Override
    public void onClick(View v) {
        if(homeViewModel.waterNeed != 0){
            switch (v.getId()) {
                case id.img_water :   //누르면 마신 물 기록 하고(뷰모델에서) 퍼센테이지 토스트 메시지로 보여주기
                    if (intSelectItem == 0 ) {
                        homeViewModel.addWater();
                        tv_waterState.setText(homeViewModel.getWaterState());
                        setImg_water();
                    } else if (intSelectItem == 1) {
                        img_waterGlide.setVisibility(View.VISIBLE);
                        img_waterGlide.setImageResource(drawable.glide_coffee);
                        homeViewModel.addCoffee();
                        tv_coffee.setText("커피 : "+homeViewModel.getCoffee()+"ml");
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, "setImg_water: thread water 후");
                                img_waterGlide.setVisibility(View.INVISIBLE);
                            }
                        },700);
                    } else if (intSelectItem == 2) {
                        img_waterGlide.setVisibility(View.VISIBLE);
                        img_waterGlide.setImageResource(drawable.glide_tea);
                        homeViewModel.addTea();
                        tv_tea.setText("차 : "+homeViewModel.getTea()+"ml");
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, "setImg_water: thread water 후");
                                img_waterGlide.setVisibility(View.INVISIBLE);
                            }
                        },700);

                    }

                    break;
                case id.img_pee :
                    homeViewModel.addPee();
                    tv_peeCnt.setText(homeViewModel.getPeeCnt().toString());
                    break;
                case id.img_feces :
                    homeViewModel.addFeces();
                    tv_fecesCnt.setText(homeViewModel.getFecesCnt().toString());
                    break;
                case id.img_drink :   //shortClick 음료 종류에 따라 다른 토스트 메시지
                    if (intSelectItem == 0 ) {
                        Toast.makeText(context, "물", Toast.LENGTH_SHORT).show();
                    } else if (intSelectItem == 1 ) {
                        Toast.makeText(context, "커피", Toast.LENGTH_SHORT).show();
                    } else if (intSelectItem == 2 ) {
                        Toast.makeText(context, "차", Toast.LENGTH_SHORT).show();
                    }
                    Log.d(TAG, "setOnClick: "+img_drink);
                    break;
            }
        }

    }

    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : HomeViewModel
     * 반환값 : boolean
     * 설명 : 물, 소변, 대변, 작은 물방울 이미지 LongClick 이벤트
     */
    @Override
    public boolean onLongClick(View v) {
        if(homeViewModel.waterNeed != 0){
            switch (v.getId()) {
                case id.img_water :   //롱 클릭 하면 잘못 입력한 물 취소.
                    if (intSelectItem == 0) {
                        homeViewModel.subWater();
                        tv_waterState.setText(homeViewModel.getWaterState());
                        setImg_water();
                        setWaterAC();
                    } else if (intSelectItem == 1) {
                        img_waterGlide.setVisibility(View.VISIBLE);
                        img_waterGlide.setImageResource(drawable.glide_coffee);
                        homeViewModel.subCoffee();
                        tv_coffee.setText("커피 : "+homeViewModel.getCoffee()+"ml");
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, "setImg_water: thread water 후");
                                img_waterGlide.setVisibility(View.INVISIBLE);
                            }
                        },700);
                    } else if (intSelectItem == 2) {
                        img_waterGlide.setVisibility(View.VISIBLE);
                        img_waterGlide.setImageResource(drawable.glide_tea);
                        homeViewModel.subTea();
                        tv_tea.setText("차 : "+homeViewModel.getTea()+"ml");
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, "setImg_water: thread water 후");
                                img_waterGlide.setVisibility(View.INVISIBLE);
                            }
                        },700);
                    }

                    break;
                case id.img_pee : //peeCount  - 1 하고 바로 뷰모델로 보내고 뷰모델에서 값 받아오기
                    homeViewModel.subPee();
                    tv_peeCnt.setText(homeViewModel.getPeeCnt().toString());
                    break;
                case id.img_feces :   //fecesCount -1 하고 바로 뷰모델로 보내고 뷰모델에서 값 받아오기
                    homeViewModel.subFeces();
                    tv_fecesCnt.setText(homeViewModel.getFecesCnt().toString());
                    break;
                case id.img_drink :   //LongClick에 dialogPopUp 넣고 popup 변경에 따라 drink 이미지 변경
                    dDialog = new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                    dDialog.setTitle("음료 종류를 선택하세요")
                            .setItems(dItems, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dSelectItem = dItems[which].toString();
//                                Toast.makeText(context,dItems[which], Toast.LENGTH_SHORT).show();
                                    setImg_drink(dSelectItem);
                                    Log.d(TAG, "onClick: int selectItem = "+intSelectItem);
                                    setIntSelectItem(which);
                                }
                            })
                            .setCancelable(true)
                            .show();
//                Log.d(TAG, "onLongClick: selectItem"+intSelectItem);
                    break;
            }
        }

//                        Log.d(TAG, "onLongClick: selectItem"+intSelectItem);

        return true;    //onClick 동시 실행 안됨
    }

    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : HomeFragment
     * 반환값 : 없음
     * 설명 : 변수 초기화. 필요 물 량 가지고 오기
     *      물방울 이미지 세팅 실행
     */
    public void initHome(){
        tv_progress = root.findViewById(id.tv_progress);
        tv_waterState = root.findViewById(id.tv_waterAmount);
        tv_peeCnt = root.findViewById(id.tv_peeCnt);
        tv_fecesCnt = root.findViewById(id.tv_fecesCnt);
        tv_tea = root.findViewById(id.tv_tea);
        tv_coffee = root.findViewById(id.tv_coffee);

        img_water = root.findViewById(id.img_water);
        img_water2 = root.findViewById(id.img_water2);
        img_waterGlide = root.findViewById(id.img_waterGlide);
        img_drink = root.findViewById(id.img_drink);
        img_feces = root.findViewById(id.img_feces);
        img_pee = root.findViewById(id.img_pee);

        img_water2.setVisibility(View.INVISIBLE);
        img_waterGlide.setVisibility(View.INVISIBLE);

        water = getString(string.drink_water_korean);
        coffee = getString(string.drink_coffee_korean);
        tea = getString(string.drink_tea_korean);

        dItems = new CharSequence[]{water, coffee, tea};
        dSelectItem = new String();

        if(homeViewModel.getWaterNeed(context)!= null){
            waterNeed = homeViewModel.getWaterNeed(context);
            homeViewModel.setWaterNeed(waterNeed);
            homeViewModel.setCup(homeViewModel.getCup(context));
        } else {
            homeViewModel.setWaterNeed(0);
        }
        setImg_water();
        setWaterAC();
    }


    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : HomeFragment
     * 반환값 : 없음
     * 설명 : 물 량에 따른 물방울 이미지 변경
     */
    public void setImg_water(){
        Float waterPer  = 0.0f;
        if(homeViewModel.getWaterNeed(context) != null) {
            waterPer = homeViewModel.getWaterPer();
        }
        Log.d(TAG, "setImg_water: "+waterPer);
        if(waterPer<20) {
//            img_water.setImageResource(R.drawable.hungry_0_30);
            img_water.setImageResource(drawable.hungry_0_30_light);
        } else if(waterPer>=20 && waterPer<50) {
//            img_water.setImageResource(R.drawable.normal_filled_30_50);
            img_water.setImageResource(drawable.normal_30_50_filled_light);
        } else if(waterPer>=50 && waterPer<70) {
//            img_water.setImageResource(R.drawable.pleased_filled_50_80);
            img_water.setImageResource(drawable.pleased_50_80_filled_light);
        } else if(waterPer>=70 && waterPer<90) {
//            img_water.setImageResource(R.drawable.excited_filled2_80);
            img_water.setImageResource(drawable.excited_80_filled_light);
        } else {

            if (waterPer <= 100){
                img_water.setImageResource(drawable.veryhappy_100_light);
            } else if (waterPer > 100 && waterPer < 120) {
                Log.d(TAG, "setImg_water: thread 전");
                Log.d(TAG, "setImg_water: thread");
                img_water.setImageResource(drawable.veryhappy_100_light);
                img_water2.setImageResource(drawable.over_100);
                img_water.setVisibility(View.INVISIBLE);
                img_water2.setVisibility(View.VISIBLE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "setImg_water: thread water 후");
                        img_water2.setVisibility(View.INVISIBLE);
                        img_water.setVisibility(View.VISIBLE);
                    }
                },700);

            } else if (waterPer >= 120 && waterPer < 140) {
                Log.d(TAG, "setImg_water: thread 전");
                Log.d(TAG, "setImg_water: thread");
                img_water.setImageResource(drawable.veryhappy_100_light);
                img_water2.setImageResource(drawable.wink_100);
                img_water.setVisibility(View.INVISIBLE);
                img_water2.setVisibility(View.VISIBLE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "setImg_water: thread water 후");
                        img_water2.setVisibility(View.INVISIBLE);
                        img_water.setVisibility(View.VISIBLE);
                    }
                },700);
            } else if (waterPer >=140 && waterPer < 160) {
                Log.d(TAG, "setImg_water: thread 전");
                Log.d(TAG, "setImg_water: thread");
                img_water.setImageResource(drawable.veryhappy_100_light);
                img_water2.setImageResource(drawable.heart_100);
                img_water.setVisibility(View.INVISIBLE);
                img_water2.setVisibility(View.VISIBLE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "setImg_water: thread water 후");
                        img_water2.setVisibility(View.INVISIBLE);
                        img_water.setVisibility(View.VISIBLE);
                    }
                },700);
            } else {
                Log.d(TAG, "setImg_water: thread 전");
                Log.d(TAG, "setImg_water: thread");
                img_water.setImageResource(drawable.veryhappy_100_light);
                img_water2.setImageResource(drawable.wow_100);
                img_water.setVisibility(View.INVISIBLE);
                img_water2.setVisibility(View.VISIBLE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "setImg_water: thread water 후");
                        img_water2.setVisibility(View.INVISIBLE);
                        img_water.setVisibility(View.VISIBLE);
                    }
                },700);
            }
        }
    }

    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : HomeFragment
     * 반환값 : 없음
     * 설명 : 음료 이미지 변경
     */
    public void setImg_drink(String dSelectItem) {
        switch (dSelectItem) {
            case "커피":
                img_drink.setImageResource(drawable.drink_coffee);
                break;
            case "차":
                img_drink.setImageResource(drawable.drink_tea);
                break;
            default:
                img_drink.setImageResource(drawable.drink_water);
                break;
        }
    }

    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : HomeFragment
     * 반환값 : context
     * 설명 : context 반환
     */
    public Context getContext(){
        return context;
    }

    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : HomeFragment
     * 반환값 : 없음
     * 설명 : 마신 물 량 퍼센트별 waterAC 설정
     */
    private void setWaterAC(){
        Float waterPer  = 0.0f;
        if(homeViewModel.getWaterNeed(context) != null) {
            waterPer = homeViewModel.getWaterPer();
        }
        if(waterPer>0) {
            if(waterPer<20) {
                homeViewModel.setWaterAC(1);
            } else if(waterPer>=20 && waterPer<40) {
                homeViewModel.setWaterAC(2);
            } else if(waterPer>=40 && waterPer<60) {
                homeViewModel.setWaterAC(3);
            } else if(waterPer>=60 && waterPer<100) {
                homeViewModel.setWaterAC(4);
            } else {
                homeViewModel.setWaterAC(5);
            }
        } else {
            homeViewModel.setWaterAC(0);
        }
    }


    /*-------------------------------------------------
     *형태 : Method
     * 소유자 : HomeFragment
     * 반환값 : 없음
     * 설명 : IntSelectItem을 세팅
     */
    private void setIntSelectItem(Integer which)
    {
        intSelectItem = which;
    }



}
