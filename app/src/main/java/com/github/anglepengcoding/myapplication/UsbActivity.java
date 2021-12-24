package com.github.anglepengcoding.myapplication;

import android.app.Activity;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.anglepengcoding.xblue.XBluetooth;
import com.github.anglepengcoding.xblue.usb.IAppointUsbData;
import com.github.anglepengcoding.xblue.usb.IUsbTooth;

import java.util.List;

/**
 * Created by 刘红鹏 on 2021/11/30.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 */

public class UsbActivity extends Activity {

    private RecyclerView mRecyclerView;
    private BaseQuickAdapter<UsbDevice, BaseViewHolder> usbAdapter;
    private XBluetooth.Builder usbBuilder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView = new RecyclerView(App.getAppContext());
        setContentView(mRecyclerView);
        initUsbAdapter();//串口

        usbBuilder = new XBluetooth
                .Builder(getApplicationContext())
                .scanUsb(new IUsbTooth() {
                    @Override
                    public void scanUsbData(List<UsbDevice> usbDevices) {//扫描全部串口数据
                        usbAdapter.setNewData(usbDevices);
                    }
                })
                .appointScanUsb(1, 0, new IAppointUsbData() {//指定扫描串口数据
                    @Override
                    public void scanUsbData(UsbDevice device) {
                    }
                });


    }



    private void initUsbAdapter() {
        usbAdapter = new BaseQuickAdapter<UsbDevice, BaseViewHolder>(R.layout.activity_blue_tooth_device) {
            @Override
            protected void convert(BaseViewHolder helper, final UsbDevice item) {
                helper.setText(R.id.mTvName, item.getDeviceName());//usb名字
                helper.setText(R.id.mTvAddress, item.getVendorId());

                helper.getView(R.id.list_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //请求usb权限
                        usbBuilder.requestPermission(item);
                    }
                });

            }
        };


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setAdapter(usbAdapter);
    }
}
