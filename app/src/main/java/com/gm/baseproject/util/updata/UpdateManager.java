package com.gm.baseproject.util.updata;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.gm.baseproject.util.preference.PreferenceConstants;
import com.gm.baseproject.util.preference.PreferenceUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 项目名称：intelligent-warehouse-rfidhandler-inventroy
 * 类描述：
 * 创建人：zhanggangmin
 * 创建时间：2017/5/7 14:16
 * 修改人：zhanggangmin
 * 修改时间：2017/5/7 14:16
 * 修改备注：
 */
public class UpdateManager {
    private Activity context;
    private TelephonyManager tm;
    private PackageInfo info; // apk信息


    public UpdateManager(Context context) throws PackageManager.NameNotFoundException {
        this.context = (Activity) context;
        info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
    }

    public void checkVersion() {
        EventBus.getDefault().register(this);
        // TODO: 2017/7/20 这里需要开启网络
//        Network.getInstance().versionUpdate(UpdateManager.this.getClass().getSimpleName());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void UwbDebug(Version version) {
        String versionName = info.versionName;
        if (!versionName.equals(version.getVersion())) {
            if (!canDownloadState()) {
                Toast.makeText(context, "下载服务不用,请您启用", Toast.LENGTH_SHORT).show();
                showDownloadSetting();
                return;
            }
            ApkUpdata apkUpdata = ApkUpdata.getInstance(context);
            long downloadId = PreferenceUtils.getPrefLong(context, PreferenceConstants.DOWNLOADID, -1L);
            if (downloadId != -1L) {
                int status = apkUpdata.getDownloadStatus(downloadId);//查看当前id的状态
                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                    Uri uri = apkUpdata.getDownloadUri(downloadId);//获取文件路径
                    if (uri != null) {
                        PackageInfo apkInfo = UpdataUtil.getApkInfo(context, uri.getPath());//获取apk信息
                        if (apkInfo != null) {
                            if (apkInfo.packageName.equals(info.packageName)) {//这里的条件噶为code大小判断的话就可以实现更新最新
                                if (!version.getVersion().equals(apkInfo.versionName)) {//如果不是最新的 重新下载
                                    apkUpdata.getDownloadManager().remove(downloadId);
                                    PreferenceUtils.setPrefLong(context, PreferenceConstants.DOWNLOADID, -1L);//清空
                                    //下载
                                    apkUpdata.start(version.getDownloadUrl(), version.getFileName());
                                } else {
                                    //打开
                                    UpdataUtil.startInstall(context, uri);
                                }
                                return;
                            }
                        }
                    }
                } else if (status == DownloadManager.STATUS_FAILED) {
                    //下载
                } else {
                    //下载
                }
            } else {
                //下载
            }
            apkUpdata.start(version.getDownloadUrl(), version.getFileName());
        }
    }


    /**
     * 启动下载服务设置
     */
    private void showDownloadSetting() {
        String packageName = "com.android.providers.downloads";
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        if (intentAvailable(intent)) {
            context.startActivity(intent);
        }
    }

    /**
     * @param intent
     * @return
     */
    private boolean intentAvailable(Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }


    /**
     * @return 判断下载服务是否可用
     */
    private boolean canDownloadState() {
        try {
            int state = context.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");

            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public void cancel() {
        EventBus.getDefault().unregister(this);
        //todo 这里要关闭网络 考虑写成接口
//        if (VolleyManager.newInstance() != null) {
//            VolleyManager.newInstance().cancel(UpdateManager.this.getClass().getSimpleName());
//        }
    }
}
