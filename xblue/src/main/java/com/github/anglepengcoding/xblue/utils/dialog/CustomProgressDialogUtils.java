package com.github.anglepengcoding.xblue.utils.dialog;

import android.app.Activity;
import android.content.Context;


public class CustomProgressDialogUtils {

    private static CustomProgressDialogUtils customProgressDialogUtils;

    private PictureDialog mProgressDialog;

    public PictureDialog getPictureDialog(){
        return  mProgressDialog;
    }
    /**
     * 单一实例
     */
    public static CustomProgressDialogUtils getInstance() {
        if (customProgressDialogUtils == null) {
            synchronized (CustomProgressDialogUtils.class) {
                if (customProgressDialogUtils == null) {
                    customProgressDialogUtils = new CustomProgressDialogUtils();
                }
            }

        }
        return customProgressDialogUtils;
    }

    /**
     * 显示ProgressDialog
     */
    public void showProgress(Context context) {
        mProgressDialog = new PictureDialog(context);

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }else {
            mProgressDialog.show();
        }
    }

    public void showProgress(Context context, String msg) {
        mProgressDialog = new PictureDialog(context);
        if (mProgressDialog != null && !mProgressDialog.isShowing() && !((Activity) context).isFinishing()) {
            mProgressDialog.show();
        }
    }


    /**
     * 取消ProgressDialog
     */
    public void dismissProgress() {
        if (mProgressDialog == null || (!mProgressDialog.isShowing())) {
            return;
        }
        mProgressDialog.dismiss();
        mProgressDialog = null;

    }


}
