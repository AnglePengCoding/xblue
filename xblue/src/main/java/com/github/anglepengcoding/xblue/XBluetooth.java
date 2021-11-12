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

        private Activity activity;

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

        public Builder unregisterReceiver(Activity activity) {
            this.manger.unregisterReceiver(activity);
            return this;
        }
    }
}
