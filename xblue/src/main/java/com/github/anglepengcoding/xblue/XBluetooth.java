package com.github.anglepengcoding.xblue;

import android.app.Activity;
import android.content.Context;

import com.github.anglepengcoding.xblue.bluetooth.BluetoothManger;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IBlueTooth;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IBlueToothPairState;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IBluetState;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IHistoryBlueTooth;
import com.github.anglepengcoding.xblue.utils.BlueUtils;
import com.github.anglepengcoding.xblue.wifi.IWiFiSupplicantState;
import com.github.anglepengcoding.xblue.wifi.IWifiTooth;
import com.github.anglepengcoding.xblue.wifi.WiFiManger;

/**
 * Created by 刘红鹏 on 2021/11/5.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 */

public class XBluetooth {

    public static class Builder {

        private final BluetoothManger bluetoothManger = new BluetoothManger();

        private final WiFiManger wiFiManger = new WiFiManger();

        public Builder() {
        }

        public Builder(Activity activity) {
            this.bluetoothManger.activity = activity;
        }

        /**
         * @param scan 蓝牙是否开启扫描
         * @return
         */
        public Builder scanBlueTooth(boolean scan, int scanMillis) {
            if (scan) {
                bluetoothManger.scanBlueTooth(scanMillis);
            }
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
         * @param activity
         * @return
         */
        public Builder unregisterReceiver(Activity activity) {
            this.bluetoothManger.unregisterReceiver(activity);
            return this;
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
        public Builder openWifi(Activity activity) {
            this.wiFiManger.openWifi(activity);
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
         * @param wifiTooth
         * @return
         */
        public Builder scanWiFiData(IWifiTooth wifiTooth,IWiFiSupplicantState wiFiSupplicantState) {
            this.wiFiManger.scanWiFiData(wifiTooth,wiFiSupplicantState);
            return this;
        }

    }
}
