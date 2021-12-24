package com.github.anglepengcoding.xblue;

import android.app.Activity;
import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.anglepengcoding.xblue.bluetooth.BluetoothManger;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IBlueTooth;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IBlueToothPairState;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IBluetState;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IHistoryBlueTooth;
import com.github.anglepengcoding.xblue.usb.IAppointUsbData;
import com.github.anglepengcoding.xblue.usb.IUsbTooth;
import com.github.anglepengcoding.xblue.usb.UsbManger;
import com.github.anglepengcoding.xblue.utils.BlueUtils;
import com.github.anglepengcoding.xblue.wifi.IWiFiSupplicantState;
import com.github.anglepengcoding.xblue.wifi.IWifiTooth;
import com.github.anglepengcoding.xblue.wifi.WiFiManger;

import static android.content.ContentValues.TAG;

/**
 * Created by 刘红鹏 on 2021/11/5.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 */

public class XBluetooth {

    public static class Builder {

        private final BluetoothManger bluetoothManger = new BluetoothManger();
        private final UsbManger usbManger = new UsbManger();
        private final WiFiManger wiFiManger = new WiFiManger();
        private Context mContext;

        public Builder(Context context) {
            this. mContext= context;
        }

        public Builder scanBlueTooth(int scanMillis) {
            if (mContext==null){
                Log.e(TAG, "scanBlueTooth: 请先初始化Builder构造方法" );
                return this;
            }
            bluetoothManger.scanBlueTooth(scanMillis,mContext);
            return this;
        }


        /**
         * @param printBoolean 只显示打印机的蓝牙
         * @return
         */
        public Builder scanIsPrint(boolean printBoolean) {
            this.bluetoothManger.printBoolean = printBoolean;
            return this;
        }


        /**
         * 扫描得到的结果
         *
         * @param callBack
         * @return
         */
        public Builder scanCallBack(IBlueTooth callBack) {
            this.bluetoothManger.iBlueTooth = callBack;
            return this;
        }

        /**
         * 连接蓝牙地址
         *
         * @param address
         * @return
         */
        public Builder connect(String address) {
            if (!BlueUtils.checkBluetoothAddress(address)) {
                throw new IllegalArgumentException(address + " is not a valid Bluetooth address");
            }
            this.bluetoothManger.address = address;
            return this;
        }

        /**
         * 取消扫描
         *
         * @return
         */
        public Builder cancel() {
            this.bluetoothManger.cancelSearchBluetooth();
            return this;
        }

        /**
         * 已经配对的蓝牙设备
         *
         * @param callBack
         * @return
         */
        public Builder scanHistoryCallBack(IHistoryBlueTooth callBack) {
            this.bluetoothManger.historyBlueTooth = callBack;
            return this;
        }

        /**
         * 销毁广播
         *
         * @return
         */
        public void unregisterReceiver() {
            this.bluetoothManger.unregisterReceiver();
        }

        /**
         * 手机蓝牙连接状态监听
         *
         * @param bluetState
         * @return
         */
        public Builder blueToothState(IBluetState bluetState) {
            this.bluetoothManger.bluetState = bluetState;
            return this;
        }

        /**
         * 蓝牙配对状态
         *
         * @param blueToothPairState
         * @return
         */
        public Builder blueToothPairState(IBlueToothPairState blueToothPairState) {
            this.bluetoothManger.blueToothPairState = blueToothPairState;
            return this;
        }

        /**
         * 打开wifi
         */
        public Builder openWifi() {
            if (mContext==null){
                Log.e(TAG, "scanBlueTooth: 请先初始化Builder构造方法" );
                return this;
            }
            this.wiFiManger.openWifi(mContext);
            return this;
        }

        /**
         * 关闭wifi
         */
        public Builder closeWifi() {
            this.wiFiManger.closeWifi();
            return this;
        }


        /**
         * 扫描的wifi结果
         *
         * @param wifiTooth
         * @return
         */
        public Builder scanWiFiData(IWifiTooth wifiTooth, IWiFiSupplicantState wiFiSupplicantState) {
            this.wiFiManger.scanWiFiData(wifiTooth, wiFiSupplicantState);
            return this;
        }


        /**
         * @param usbData 数据
         * @return
         */
        public Builder scanUsb(IUsbTooth usbData) {
            if (mContext==null){
                Log.e(TAG, "scanBlueTooth: 请先初始化Builder构造方法" );
                return this;
            }
            this.usbManger.scanUsb(usbData,mContext);
            return this;
        }

        /**
         * @param vendorId       厂商ID
         * @param productId      产品ID
         * @param appointUsbData usb 数据
         * @return
         */
        public Builder appointScanUsb(int vendorId, int productId, IAppointUsbData appointUsbData) {
            this.usbManger.appointScanUsb(vendorId, productId, appointUsbData);
            return this;
        }


        /**
         * 请求USB权限
         *
         * @param device
         * @return
         */
        public Builder requestPermission(@NonNull UsbDevice device) {
            this.usbManger.requestPermission(device);
            return this;
        }

        /**
         * 关闭usb
         */
        public Builder closeUsb() {
            this.usbManger.closeUsb();
            return this;
        }

    }
}
