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
    public ImageView img_water;
    public ImageView img_pee;
    public ImageView img_feces;
    public ImageView img_drink;
    public TextView tv_progress;
    public TextView tv_peeCnt;
    public TextView tv_fecesCnt;
    private Context context;
    public TextView tv_waterState;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        context = container.getContext();

        initHome();


        homeViewModel.getProgressTxt().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tv_progress.setText(s);
            }
        });

        //db에서 숫자 받아와서 변경. view 모델에서 데이터 받아오기
//        tv_peeCnt.setText("5");
//        tv_fecesCnt.setText("1");
//        Log.d("homefrag", "onCreateView: "+homeViewModel.getPeeCount());

        tv_peeCnt.setText(homeViewModel.getPeeCount().toString());
        tv_fecesCnt.setText(homeViewModel.getFecesCount().toString());
        tv_waterState.setText(homeViewModel.getWaterState());  //마셔야 할 양, 마신 물 양 데이터 뷰모델에서 보내줘서 뷰모델에서 디비 작업



        img_water.setOnClickListener(this);
        img_pee.setOnClickListener(this);
        img_feces.setOnClickListener(this);
        img_drink.setOnClickListener(this);

        img_drink.setOnLongClickListener((View.OnLongClickListener) this);
        img_pee.setOnLongClickListener((View.OnLongClickListener) this);
        img_feces.setOnLongClickListener((View.OnLongClickListener) this);
        img_water.setOnLongClickListener((View.OnLongClickListener) this);

        // TODO : 물 양에 따른 물방울 이미지 변화 파트 구현

        return root;
    }

    //onclick event
    @Override
    public void onClick(View v) {
       switch (v.getId()){
            case R.id.img_water :   //누르면 마신 물 기록 하고(뷰모델에서) 퍼센테이지 토스트 메시지로 보여주기
                homeViewModel.addWater();
                tv_waterState.setText(homeViewModel.getWaterState());
                Toast.makeText(context, homeViewModel.getWaterAmount().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_pee :
                homeViewModel.addPee();
                tv_peeCnt.setText(homeViewModel.getPeeCount().toString());
                Log.d(TAG, "setOnClick: "+img_pee);
                break;
            case R.id.img_feces :
                homeViewModel.addFeces();
                tv_fecesCnt.setText(homeViewModel.getFecesCount().toString());
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
            case R.id.img_water :   //롱 클릭 하면 잘못 입력한 물 취소.
                homeViewModel.subWater();
                tv_waterState.setText(homeViewModel.getWaterState());
                Toast.makeText(context, homeViewModel.getWaterAmount().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_pee : //peeCount  - 1 하고 바로 뷰모델로 보내고 뷰모델에서 값 받아오기
                homeViewModel.subPee();
                tv_peeCnt.setText(homeViewModel.getPeeCount().toString());
                break;
            case R.id.img_feces :   //fecesCount -1 하고 바로 뷰모델로 보내고 뷰모델에서 값 받아오기
                homeViewModel.subFeces();
                tv_fecesCnt.setText(homeViewModel.getFecesCount().toString());
                break;
            case R.id.img_drink :   //LongClick에 dialogPopUp 넣고 popup 변경에 따라 이미지 변경

                Toast.makeText(context, "Longdrink", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;    //onClick 동시 실행 안됨
    }

    // 초기화
    public void initHome(){
        tv_progress = root.findViewById(R.id.tv_progress);
        tv_waterState = root.findViewById(R.id.tv_waterAmount);
        tv_peeCnt = root.findViewById(R.id.tv_peeCnt);
        tv_fecesCnt = root.findViewById(R.id.tv_fecesCnt);

        img_water = root.findViewById(R.id.img_water);
        img_drink = root.findViewById(R.id.img_drink);
        img_feces = root.findViewById(R.id.img_feces);
        img_pee = root.findViewById(R.id.img_pee);
    }
}