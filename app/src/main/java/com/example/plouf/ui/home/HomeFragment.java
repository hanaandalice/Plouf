package com.example.plouf.ui.home;

import android.content.Context;
import android.os.Bundle;
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

import com.example.plouf.R;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener{

    private HomeViewModel homeViewModel;
    public String msgWater;
    public ImageView img_water;
    public ImageView img_pee;
    public ImageView img_feces;
    public ImageView img_drink;
    public TextView tv_progress;
    public TextView tv_peeCnt;
    public TextView tv_fecesCnt;
    private Context context;
    public TextView tv_waterAmount;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        initHome();


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tv_progress.setText(s);
            }
        });

        //db에서 숫자 받아와서 변경. view 모델에서 데이터 받아오기
        tv_peeCnt.setText("5");
        tv_fecesCnt.setText("1");


        context = container.getContext();

        homeViewModel.getWaterAmountTxt().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                msgWater = s;
                Log.d(TAG, "onChanged: "+msgWater);
            }
        });


        tv_waterAmount.setText(homeViewModel.getWaterAmount() +"/2400ml");  //마셔야 할 양, 마신 물 양 데이터 프래그먼트에서 불러오고 프래그먼트로 보내줘서 프레그먼트에서 디비 작업


        img_water.setOnClickListener(this);
        img_pee.setOnClickListener(this);
        img_feces.setOnClickListener(this);
        img_drink.setOnClickListener(this);

        img_drink.setOnLongClickListener((View.OnLongClickListener) this);


        return root;
    }

    //onclick event
    @Override
    public void onClick(View v) {
       switch (v.getId()){
            case R.id.img_water :   //누르면 마신 물 기록 하고 퍼센테이지 토스트 메시지로 보여주기
                Toast.makeText(context, msgWater, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "setOnClick: "+msgWater);
                break;
            case R.id.img_pee :
                Toast.makeText(context, "pee", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "setOnClick: "+img_pee);
                break;
            case R.id.img_feces :
                Toast.makeText(context, "feces", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "setOnClick: "+img_feces);
                break;
            case R.id.img_drink :   //shortClick 음료 종류에 따라 다른 토스트 메시지
                Toast.makeText(context, "drink", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "setOnClick: "+img_drink);
                break;

        }

    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()){
            case R.id.img_drink :   //LongClick에 dialogPopUp 넣고 popup 변경에 따라 이미지 변경


                return true;
            case R.id.img_pee : //peeCount  - 1 하고 바로 뷰모델로 보내고 뷰모델에서 값 받아오기

                return true;

            case R.id.img_feces :   //fecesCount -1 하고 바로 뷰모델로 보내고 뷰모델에서 값 받아오기
                return true;
        }
        return false;
    }

    // 초기화
    public void initHome(){
        tv_progress = root.findViewById(R.id.tv_progress);
        tv_waterAmount = root.findViewById(R.id.tv_waterAmount);
        tv_peeCnt = root.findViewById(R.id.tv_peeCnt);
        tv_fecesCnt = root.findViewById(R.id.tv_fecesCnt);

        img_water = root.findViewById(R.id.img_water);
        img_drink = root.findViewById(R.id.img_drink);
        img_feces = root.findViewById(R.id.img_feces);
        img_pee = root.findViewById(R.id.img_pee);

    }
}