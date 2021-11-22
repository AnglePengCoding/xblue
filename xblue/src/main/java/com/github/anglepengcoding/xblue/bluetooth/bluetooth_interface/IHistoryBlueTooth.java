package com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface;

import android.bluetooth.BluetoothDevice;

import java.util.List;

/**
 * Created by 刘红鹏 on 2021/11/7.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 */

public interface IHistoryBlueTooth {

    void historyData(List<BluetoothDevice> historyDeviceList);
}
