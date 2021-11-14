package com.github.anglepengcoding.xblue;

import android.app.Activity;

/**
 * Created by 刘红鹏 on 2021/11/5.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 */

public class XBluetooth {


    public static class Builder {

        private final BluetoothManger manger = new BluetoothManger();


        public Builder(Activity activity) {
            this.manger.activity = activity;
        }

        /**
         * @param scan 蓝牙是否开启扫描
         * @return
         */
        public Builder scanBlueTooth(boolean scan, int scanMillis) {
            if (scan) {
                manger.scanBlueTooth(scanMillis);
            }
            return this;
        }


        /**
         * @param printBoolean 只显示打印机的蓝牙
         * @return
         */
        public Builder scanIsPrint(boolean printBoolean) {
            this.manger.printBoolean = printBoolean;
            return this;
        }


        /**
         * 扫描得到的结果
         *
         * @param callBack
         * @return
         */
        public Builder scanCallBack(IBlueTooth callBack) {
            this.manger.iBlueTooth = callBack;
            return this;
        }

        /**
         * 连接蓝牙地址
         * @param address
         * @return
         */
        public Builder connect(String address ) {
            this.manger.address= address;
            return this;
        }

        /**
         * 取消扫描
         *
         * @return
         */
        public Builder cancel() {
            this.manger.cancelSearchBluetooth();
            return this;
        }

        /**
         * 已经配对的蓝牙设备
         *
         * @param callBack
         * @return
         */
        public Builder scanHistoryCallBack(IHistoryBlueTooth callBack) {
            this.manger.historyBlueTooth = callBack;
            return this;
        }

        /**
         * 销毁广播
         * @param activity
         * @return
         */
        public Builder unregisterReceiver(Activity activity) {
            this.manger.unregisterReceiver(activity);
            return this;
        }

        /**
         * 手机蓝牙连接状态监听
         * @param bluetState
         * @return
         */
        public Builder blueToothState(IBluetState bluetState) {
            this.manger.bluetState = bluetState;
            return this;
        }

        /**
         * 蓝牙配对状态
         * @param blueToothPairState
         * @return
         */
        public Builder blueToothPairState(IBlueToothPairState blueToothPairState) {
            this.manger.blueToothPairState = blueToothPairState;
            return this;
        }

    }
}
