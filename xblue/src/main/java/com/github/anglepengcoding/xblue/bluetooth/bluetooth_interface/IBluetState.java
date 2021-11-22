package com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface;

/**
 * Created by 刘红鹏 on 2021/11/14.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 * 手机蓝牙连接状态监听
 */

public interface IBluetState {

    /**
     * 手机蓝牙关闭
     */
    void state_off();

    /**
     * 手机蓝牙正在关闭
     */
    void state_turning_off();

    /**
     * 手机蓝牙开启
     */
    void state_on();

    /**
     * 手机蓝牙正在开启
     */
    void state_turning_on();
}
