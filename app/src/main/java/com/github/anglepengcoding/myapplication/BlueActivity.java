package com.github.anglepengcoding.myapplication;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.anglepengcoding.xblue.XBluetooth;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IBlueTooth;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IBlueToothPairState;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IBluetState;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IHistoryBlueTooth;

import java.util.List;

/**
 * Created by 刘红鹏 on 2021/11/30.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 */

public class BlueActivity extends Activity {
    private RecyclerView mRecyclerView;
    private BaseQuickAdapter<BluetoothDevice, BaseViewHolder> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView = new RecyclerView(App.getAppContext());
        setContentView(mRecyclerView);
        initBluetoothAdapter();
        new XBluetooth
                .Builder(BlueActivity.this)
                .scanBlueTooth(true, 5000) //开启扫描 停止时间
                .scanCallBack(new IBlueTooth() {//搜索到蓝牙设备
                    @Override
                    public void blueData(List<BluetoothDevice> addDeviceList) {
                        adapter.setNewData(addDeviceList);
                    }
                }).scanHistoryCallBack(new IHistoryBlueTooth() {//历史配过的蓝牙
            @Override
            public void historyData(List<BluetoothDevice> historyDeviceList) {

            }
        }).blueToothState(new IBluetState() {
            @Override
            public void state_off() {
                //手机蓝牙关闭
            }

            @Override
            public void state_turning_off() {
                //手机蓝牙正在关闭
            }

            @Override
            public void state_on() {
                //手机蓝牙开启
            }

            @Override
            public void state_turning_on() {
                //手机蓝牙正在开启
            }
        }).blueToothPairState(new IBlueToothPairState() {
            @Override
            public void bond_bonding() {
                //正在配对
            }

            @Override
            public void bond_bonded() {
                //完成配对
            }

            @Override
            public void bond_none() {
                //取消配对
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }


    private void initBluetoothAdapter() {
        adapter = new BaseQuickAdapter<BluetoothDevice, BaseViewHolder>(R.layout.activity_blue_tooth_device) {
            @Override
            protected void convert(BaseViewHolder helper, BluetoothDevice item) {
                helper.setText(R.id.mTvName, item.getName());//蓝牙名字
                helper.setText(R.id.mTvAddress, item.getAddress());//蓝牙地址
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setAdapter(adapter);
    }
}
