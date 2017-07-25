package com.gm.baseproject;

import android.os.Bundle;
import android.widget.Toast;

import com.gm.baseproject.entity.Login;
import com.gm.baseproject.frame.BaseActivity;
import com.gm.baseproject.frame.retrofit.NetCallback;
import com.gm.baseproject.frame.retrofit.ZNCKService;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "superAdmin");
        map.put("password", "123456");
        findViewById(R.id.txt_hw).setOnClickListener((v) -> {
                    ZNCKService.INSTANCE.getZNCkApi().login(map).enqueue(new NetCallback<Login>().GetCallback());
                }
        );
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void login(Login result) {
        Toast.makeText(this, result.getUserId(), Toast.LENGTH_SHORT).show();
    }

}
