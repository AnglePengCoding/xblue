package com.github.anglepengcoding.xblue.usb;

import android.app.Activity;
import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by 刘红鹏 on 2021/11/23.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 * USB管理类
 */

public class UsbManger {

    private IUsbClient usebClient = new UsbScan();
    public Context mContext;

    public void scanUsb(IUsbTooth usbData, Context context) {
        this.mContext = context;
        try {
            usebClient.getUsbService(mContext);
            usebClient.scanUsb(usbData);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "scanUsb: scanUsb 实例化失败,请先调用  openUsb()函数");
        }
    }


    public void appointScanUsb(int vendorId, int productId, IAppointUsbData appointUsbData) {
        usebClient.appointScanUsb(vendorId, productId, appointUsbData);
    }

    public void requestPermission(UsbDevice device) {
        usebClient.requestPermission(device);
    }

    public void closeUsb() {
        usebClient.closeUsb();
    }
}
