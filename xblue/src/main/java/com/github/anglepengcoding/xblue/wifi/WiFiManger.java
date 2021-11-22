package com.github.anglepengcoding.xblue.wifi;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.Objects;

/**
 * Created by 刘红鹏 on 2021/11/15.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 * 无线管理类
 */

//<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"></uses-permission>
//<uses-permission android:name="android.permission.CHANGE_WIFI_STATE"></uses-permission>
//<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
//<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
//<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
//<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
public class WiFiManger {

    private WifiManager mWifiManager;
    private WifiInfo mWifiInfo;
    private WifiClient wifiScan = new WifiScan();
    public IWifiTooth wifiTooth;


    private void initWifiManager(Activity context) {
        mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        Objects.requireNonNull(mWifiManager);
        mWifiInfo = mWifiManager.getConnectionInfo();
        wifiScan.setWifiManager(mWifiManager);
    }


    public void openWifi(Activity activity) {
        initWifiManager(activity);
        wifiScan.openWifi(activity);
    }

    public void closeWifi() {
        wifiScan.closeWifi();
    }


    public void scanWiFiData(IWifiTooth wifiTooth,IWiFiSupplicantState wiFiSupplicantState){
        wifiScan.scanWiFiData(wifiTooth,wiFiSupplicantState);
    }

}
