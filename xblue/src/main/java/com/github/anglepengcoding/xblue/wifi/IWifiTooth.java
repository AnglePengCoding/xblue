package com.github.anglepengcoding.xblue.wifi;

import android.net.wifi.ScanResult;

import java.util.List;

/**
 * Created by 刘红鹏 on 2021/11/22.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 */

public interface IWifiTooth {


    void wifiData(List<ScanResult> addDeviceList);


}
