package com.github.anglepengcoding.xblue.usb;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.github.anglepengcoding.xblue.usb.UsbScan.USBReceiver.ACTION_USB_PERMISSION;

/**
 * Created by 刘红鹏 on 2021/11/29.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 */

public class UsbScan implements IUsbClient {

    private UsbManager usbManager;
    private USBReceiver usbReceiver;
    private Context mContext;
    private PendingIntent mPermissionIntent;

    @Override
    public void getUsbService(Context context) {
        this.mContext = context;
        usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        usbReceiver = new USBReceiver();
        registerReceiver(mContext);
    }

    @Override
    public void scanUsb(IUsbTooth usbData) {
        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        while (deviceIterator.hasNext()) {
            UsbDevice device = deviceIterator.next();
            if (usbData != null) {
                List<UsbDevice> usbDeviceList = new ArrayList<>();
                usbDeviceList.add(device);
                usbData.scanUsbData(usbDeviceList);
            }
        }

    }

    @Override
    public void appointScanUsb(int vendorId, int productId, IAppointUsbData appointUsbData) {
        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        while (deviceIterator.hasNext()) {
            UsbDevice device = deviceIterator.next();
            if (device.getVendorId() == vendorId && device.getProductId() == productId) {
                if (hasPermission(device)) {
                    appointUsbData.scanUsbData(device);
                } else {
                    Toast.makeText(mContext, "该USB设备没有权限", Toast.LENGTH_SHORT).show();
                }
            }
        }
        Toast.makeText(mContext, "没有对应的设备", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeUsb() {
        if (usbReceiver != null) {
            mContext.unregisterReceiver(usbReceiver);
        }
    }


    public class USBReceiver extends BroadcastReceiver {
        public static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                // 获取权限结果的广播
                synchronized (this) {
                    UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (device != null) {
                        if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                            Log.e(TAG, "获取权限成功：" + device.getDeviceName());
                        } else {
                            Log.e(TAG, "获取权限失败：" + device.getDeviceName());
                        }
                    }
                }
            } else if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {

            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {

            }
        }
    }

    /**
     * 注册广播
     */
    public void registerReceiver(Context context) {
        mPermissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        context.registerReceiver(usbReceiver, filter);
    }


    /**
     * 判断对应 USB 设备是否有权限
     */
    public boolean hasPermission(UsbDevice device) {
        return usbManager.hasPermission(device);
    }


    /**
     * 请求获取指定 USB 设备的权限
     */
    public void requestPermission(UsbDevice device) {
        try {
            if (usbManager.hasPermission(device)) {
                Log.e(TAG, "已经获取到权限");
            } else {
                if (mPermissionIntent != null) {
                    usbManager.requestPermission(device, mPermissionIntent);
                    Log.e(TAG, "请求USB权限");
                    Toast.makeText(mContext, "请求USB权限", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "请注册USB广播");
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "usbManager 为 null，请先获取usb数据");
            e.printStackTrace();
        }
    }


}
