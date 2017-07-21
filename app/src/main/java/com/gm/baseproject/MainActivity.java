package com.gm.baseproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gm.baseproject.util.HttpNetUtil;

public class MainActivity extends AppCompatActivity implements HttpNetUtil.Networkreceiver {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.txt_hw).setOnClickListener((v) -> {
                    System.out.println(v.getId());
                    System.out.println(v.getLayerType());
                }
        );
    }

    @Override
    public void onConnected(boolean collect) {
        System.out.println("&&&&&&&&&&&&&&"+collect);
    }
}
