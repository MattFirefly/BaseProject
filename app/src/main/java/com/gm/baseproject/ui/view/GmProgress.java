package com.gm.baseproject.ui.view;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * 项目名称：ProgressDialogDemo
 * 类描述：
 * 创建人：zhanggangmin
 * 创建时间：2017/4/29 14:15
 * 修改人：zhanggangmin
 * 修改时间：2017/4/29 14:15
 * 修改备注：
 */
public class GmProgress {

    private ProgressDialog progressDialog;

    public GmProgress(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCanceledOnTouchOutside(true);
        }
    }

    public void show() {
        if (progressDialog == null) return;
        if (!progressDialog.isShowing()) {
            show("加载中请稍后");
        }
    }

    public void show(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void setMessage(String message ){
        progressDialog.setMessage(message);
    }

    public void dismiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
