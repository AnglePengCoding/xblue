package com.github.anglepengcoding.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;


/**
 * Created by 刘红鹏 on 2021/11/30.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 */

public class WebActivity extends Activity {
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(App.getAppContext());
        setContentView(webView);
        webView.loadUrl("https://github.com/AnglePengCoding/xblue");
    }
}
