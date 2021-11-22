package com.github.anglepengcoding.xblue.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static android.content.ContentValues.TAG;


/**
 * Created by 刘红鹏 on 2021/11/4.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 */

public class BlueUtils {

    private static final int ADDRESS_LENGTH = 17;

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


    public static boolean checkBluetoothAddress(String address) {
        if (address == null || address.length() != ADDRESS_LENGTH) {
            return false;
        }
        for (int i = 0; i < ADDRESS_LENGTH; i++) {
            char c = address.charAt(i);
            switch (i % 3) {
                case 0:
                case 1:
                    if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'F')) {
                        // hex character, OK
                        break;
                    }
                    return false;
                case 2:
                    if (c == ':') {
                        break;  // OK
                    }
                    return false;
            }
        }
        return true;
    }

    public static boolean isBluetoothEnabled() {
        boolean ret = false;
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter != null && adapter.isEnabled() == true) {
            ret = true;
        }
        return ret;
    }
}
