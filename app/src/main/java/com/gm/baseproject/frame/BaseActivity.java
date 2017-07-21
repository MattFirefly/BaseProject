package com.gm.baseproject.frame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gm.baseproject.ui.view.GmProgress;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

/**
 * Created by zhanggangmin on 16/7/27.
 */
public class BaseActivity extends AppCompatActivity {
    GmProgress progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progress = new GmProgress(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void err(String result) {
        progress.dismiss();
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void err(HashMap result) {
        progress.dismiss();
        Toast.makeText(this, result.get("errorMsg").toString(), Toast.LENGTH_SHORT).show();
        if ("11019".equals(result.get("errorCode").toString())) {
            ARouter.getInstance().build("/test/login").navigation();
        }
    }
}
