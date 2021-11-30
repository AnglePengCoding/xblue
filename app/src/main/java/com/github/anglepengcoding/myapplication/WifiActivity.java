package com.github.anglepengcoding.myapplication;

import android.app.Activity;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.anglepengcoding.xblue.XBluetooth;
import com.github.anglepengcoding.xblue.wifi.IWiFiSupplicantState;
import com.github.anglepengcoding.xblue.wifi.IWifiTooth;

import java.util.List;

/**
 * Created by 刘红鹏 on 2021/11/30.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 */

public class WifiActivity extends Activity {
    private RecyclerView mRecyclerView;

    private BaseQuickAdapter<ScanResult, BaseViewHolder> wifiAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView = new RecyclerView(App.getAppContext());
        setContentView(mRecyclerView);
        initWifiAdapter();

        Toast.makeText(App.getAppContext(), "如果无数据显示,请重新关闭开启无线", Toast.LENGTH_LONG).show();
        new XBluetooth.Builder()
                .openWifi(WifiActivity.this)
                .scanWiFiData(new IWifiTooth() {
                    @Override
                    public void wifiData(List<ScanResult> addDeviceList) {
                        wifiAdapter.setNewData(addDeviceList);
                    }
                }, new IWiFiSupplicantState() {
                    @Override
                    public void wifi_state_enabling() {
                        Log.e("Yuang", "wifi_state_enabling: 正在打开 WIFI-WIFI状态发生变化");
                    }

                    @Override
                    public void wifi_state_enabled() {
                        Log.e("Yuang", "wifi_state_enabled:  WIFI 已打开-WIFI状态发生变化");
                    }

                    @Override
                    public void wifi_state_disabling() {
                        Log.e("Yuang", "wifi_state_disabling: WIFI 正在关闭");
                    }

                    @Override
                    public void wifi_state_disabled() {
                        Log.e("Yuang", "wifi_state_disabled:  WIFI 已关闭");
                    }

                    @Override
                    public void wifi_state_unknown() {
                        Log.e("Yuang", "wifi_state_unknown: WIFI 状态未知");
                    }

                    @Override
                    public void scanning() {
                        Log.e("Yuang", "scanning: 正在扫描-wifi连接结果通知   WIFI连接请求状态发生改变");
                    }

                    @Override
                    public void authenticating() {
                        Log.e("Yuang", "authenticating: 正在验证-wifi连接结果通知   WIFI连接请求状态发生改变");
                    }

                    @Override
                    public void associating() {
                        Log.e("Yuang", "associating: 正在关联-wifi连接结果通知   WIFI连接请求状态发生改变");
                    }

                    @Override
                    public void associated() {
                        Log.e("Yuang", "associated: 已经关联-wifi连接结果通知   WIFI连接请求状态发生改变");
                    }

                    @Override
                    public void completed() {
                        Log.e("Yuang", "completed: 完成-wifi连接结果通知   WIFI连接请求状态发生改变");
                    }
                });
    }


    private void initWifiAdapter() {
        wifiAdapter = new BaseQuickAdapter<ScanResult, BaseViewHolder>(R.layout.activity_blue_tooth_device) {
            @Override
            protected void convert(BaseViewHolder helper, ScanResult item) {
                helper.setText(R.id.mTvName, item.SSID);//无线名字
                helper.setText(R.id.mTvAddress, item.BSSID);//地址
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setAdapter(wifiAdapter);
    }
}
