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

import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private HomeViewModel homeViewModel;
    public String msgWater;
    public ImageView img_water;
    public ImageView img_pee;
    public ImageView img_feces;
    public ImageView img_drink;
    public TextView tv_progress;



    private Context context;
    public TextView tv_waterAmount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

//        initHome();

        //init home
        tv_progress = root.findViewById(R.id.tv_progress);
        img_water = root.findViewById(R.id.img_water);
        tv_waterAmount = root.findViewById(R.id.tv_waterAmount);
        img_drink = root.findViewById(R.id.img_drink);
        img_feces = root.findViewById(R.id.img_feces);
        img_pee = root.findViewById(R.id.img_pee);


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tv_progress.setText(s);
            }
        });
        tv_waterAmount.setText("마신 물 양");

        context = container.getContext();

        homeViewModel.getWaterAmount().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                msgWater = s;
                Log.d(TAG, "onChanged: "+msgWater);
            }
        });

        img_water.setOnClickListener(this);
        img_pee.setOnClickListener(this);
        img_feces.setOnClickListener(this);
        img_drink.setOnClickListener(this);


        return root;
    }

    //onclick event
    @Override
    public void onClick(View v) {
       switch (v.getId()){
            case R.id.img_water :
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
            case R.id.img_drink :
                Toast.makeText(context, "drink", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "setOnClick: "+img_drink);
                break;

        }

    }

//    public void initHome(){
//        tv_progress = root.findViewById(R.id.tv_progress);
//        img_water = root.findViewById(R.id.img_water);
//        tv_waterAmount = root.findViewById(R.id.tv_waterAmount);
//        img_drink = root.findViewById(R.id.img_drink);
//
//    }
}