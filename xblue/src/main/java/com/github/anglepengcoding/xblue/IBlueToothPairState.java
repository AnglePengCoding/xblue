package com.github.anglepengcoding.xblue;

/**
 * Created by 刘红鹏 on 2021/11/14.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 * 蓝牙连接状态
 */

public interface IBlueToothPairState {

    /**
     * 正在配对
     */
    void bond_bonding();

    /**
     * 完成配对
     */
    void bond_bonded();

    /**
     * 取消配对
     */
    void bond_none();
}
