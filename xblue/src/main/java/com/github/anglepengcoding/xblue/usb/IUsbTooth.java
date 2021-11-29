package com.github.anglepengcoding.xblue.usb;

import android.hardware.usb.UsbDevice;

import java.util.List;

/**
 * Created by 刘红鹏 on 2021/11/29.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 */

public interface IUsbTooth {

    void scanUsbData(List<UsbDevice> usbDevices);

}
