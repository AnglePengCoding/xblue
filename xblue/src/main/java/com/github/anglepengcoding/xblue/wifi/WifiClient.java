package com.github.anglepengcoding.xblue.wifi;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * Created by 刘红鹏 on 2021/11/15.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 */

public interface WifiClient {

    void openWifi(Activity activity );

    void closeWifi();

    void setWifiManager(WifiManager wifiManager);


    void scanWiFiData(IWifiTooth wifiTooth,IWiFiSupplicantState wiFiSupplicantState);
}
