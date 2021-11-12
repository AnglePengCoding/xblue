package com.github.anglepengcoding.xblue.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 刘红鹏 on 2021/11/4.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 */

public class BlueUtils {

    //是否打开蓝牙
    public static boolean bluetoothEnable(Context context) {
        BluetoothManager bluetoothmanger = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothadapter = bluetoothmanger.getAdapter();
        if (bluetoothadapter == null) {
            return false;
        }
        if (bluetoothadapter.isEnabled()) {
            return true;
        } else {
            return false;
        }
    }

    //打开蓝牙
    public static void openBluetooth(Context context) {
        if (!bluetoothEnable(context)) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            ((Activity) context).startActivityForResult(intent, 1);
        }
    }
}
