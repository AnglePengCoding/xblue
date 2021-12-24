package com.github.anglepengcoding.myapplication;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.anglepengcoding.xblue.XBluetooth;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IBlueTooth;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IBlueToothPairState;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IBlueToothScanState;
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
    XBluetooth.Builder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView = new RecyclerView(App.getAppContext());
        setContentView(mRecyclerView);
        initBluetoothAdapter();
        builder = new XBluetooth
                .Builder(getApplicationContext())
                .scanBlueTooth(5000) //开启扫描 停止时间
                .scanIsPrint(true)
                .scanCallBack(new IBlueTooth() {//搜索到蓝牙设备
                    @Override
                    public void blueData(List<BluetoothDevice> addDeviceList) {
                        adapter.setNewData(addDeviceList);
                    }
                }).scanHistoryCallBack(new IHistoryBlueTooth() {//历史配过的蓝牙
                    @Override
                    public void historyData(List<BluetoothDevice> historyDeviceList) {

                    }
                }).blueToothScanState(new IBlueToothScanState() {
                    @Override
                    public void started() {
                        Log.e("IBlueToothScanState","started");
                    }

                    @Override
                    public void finished() {
                        Log.e("IBlueToothScanState","finished");
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

                helper.getView(R.id.mTvName).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.startDiscovery(true);
                    }
                });
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        builder.unregisterReceiver();
    }
}
