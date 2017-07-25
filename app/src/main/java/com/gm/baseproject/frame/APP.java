package com.gm.baseproject.frame;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.stetho.Stetho;
import com.gm.baseproject.BuildConfig;

/**
 * 项目名称：BaseProject
 * 类描述：
 * 创建人：zhanggangmin
 * 创建时间：2017/7/20 17:55
 * 修改人：zhanggangmin
 * 修改时间：2017/7/20 17:55
 * 修改备注：
 */
public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
//            路由日志调试功能
            // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
    }

}
