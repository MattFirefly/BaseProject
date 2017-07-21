package com.gm.baseproject.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.gm.baseproject.receiver.NetWorkReceiver;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：BaseProject
 * 类描述：
 * 创建人：zhanggangmin
 * 创建时间：2017/7/20 18:15
 * 修改人：zhanggangmin
 * 修改时间：2017/7/20 18:15
 * 修改备注：
 */
public enum HttpNetUtil {

    INSTANCE;

    private List<Networkreceiver> networkreceivers;

    public interface Networkreceiver {

        void onConnected(boolean collect);
    }

    public void addNetWorkListener(Networkreceiver networkreceiver) {
        if (null == networkreceivers) {
            networkreceivers = new ArrayList<>();
        }
        networkreceivers.add(networkreceiver);
    }

    public void removeNetWorkListener(NetWorkReceiver listener) {
        if (networkreceivers != null) {
            networkreceivers.remove(listener);
        }
    }

    public void clearNetWorkListeners() {
        if (networkreceivers != null) {
            networkreceivers.clear();
        }
    }


    private boolean isConnected = true;

    /**
     * 获取是否连接
     */
    public boolean isConnected() {
        return isConnected;
    }

    private void setConnected(boolean connected) {
        isConnected = connected;
    }

    /**
     * 判断网络连接是否存在
     *
     * @param context
     */
    public void setConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            setConnected(false);

            if (networkreceivers != null) {
                for (int i = 0, z = networkreceivers.size(); i < z; i++) {
                    Networkreceiver listener = networkreceivers.get(i);
                    if (listener != null) {
                        listener.onConnected(false);
                    }
                }
            }
        }

        NetworkInfo info = manager.getActiveNetworkInfo();

        boolean connected = info != null && info.isConnected();
        setConnected(connected);

        if (networkreceivers != null) {
            for (int i = 0, z = networkreceivers.size(); i < z; i++) {
                Networkreceiver listener = networkreceivers.get(i);
                if (listener != null) {
                    listener.onConnected(connected);
                }
            }
        }
    }
}