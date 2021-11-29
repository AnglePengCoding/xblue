package com.github.anglepengcoding.xblue.usb;

import android.content.Context;
import android.hardware.usb.UsbDevice;

/**
 * Created by 刘红鹏 on 2021/11/29.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 */

public interface IUsbClient {

    void getUsbService(Context context);

    void scanUsb(IUsbTooth usbData);

    void appointScanUsb(int vendorId, int productId,IAppointUsbData appointUsbData);

    void closeUsb();

    void requestPermission(UsbDevice device);
}
