package com.github.anglepengcoding.xblue.wifi;

/**
 * Created by 刘红鹏 on 2021/11/22.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 */

public interface IWiFiSupplicantState {
    /**
     * 正在打开 WIFI-WIFI状态发生变化
     */
    void wifi_state_enabling();

    /**
     * WIFI 已打开-WIFI状态发生变化
     */
    void wifi_state_enabled();

    /**
     * WIFI 正在关闭
     */
    void wifi_state_disabling();

    /**
     * WIFI 已关闭
     */
    void wifi_state_disabled();

    /**
     *WIFI 状态未知
     */
    void wifi_state_unknown();

    /**
     * 正在扫描-wifi连接结果通知   WIFI连接请求状态发生改变
     */
    void scanning();


    /**
     * 正在验证-wifi连接结果通知   WIFI连接请求状态发生改变
     */
    void authenticating();

    /**
     * 正在关联-wifi连接结果通知   WIFI连接请求状态发生改变
     */
    void associating();

    /**
     * 已经关联-wifi连接结果通知   WIFI连接请求状态发生改变
     */
    void associated();

    /**
     * 完成-wifi连接结果通知   WIFI连接请求状态发生改变
     */
    void completed();


}
