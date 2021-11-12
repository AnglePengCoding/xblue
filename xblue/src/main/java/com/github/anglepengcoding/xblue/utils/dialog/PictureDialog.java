package com.github.anglepengcoding.xblue.utils.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.anglepengcoding.xblue.R;


public class PictureDialog extends Dialog {
    public Context context;
    public String msg;
    TextView tv;

    public PictureDialog(Context context) {
        super(context, R.style.loading_dialog);
        this.context = context;
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setWindowAnimations(R.style.DialogWindowStyle);

        // 解决dialog背景四个直角有黑边问题
        window.setBackgroundDrawableResource(android.R.color.transparent);// 背景透明
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏（注：内容区域全屏，有状态显示）

    }


    public PictureDialog(Context context,String msg) {
        super(context, R.style.loading_dialog);
        this.context = context;
        this.msg=msg;
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setWindowAnimations(R.style.DialogWindowStyle);

        // 解决dialog背景四个直角有黑边问题
        window.setBackgroundDrawableResource(android.R.color.transparent);// 背景透明
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏（注：内容区域全屏，有状态显示）

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog);
        tv=findViewById(R.id.tv);
        if (!TextUtils.isEmpty(msg)){
            tv.setText(msg);
        }
    }

}