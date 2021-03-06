package com.github.anglepengcoding.xblue.bluetooth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IBlueTooth;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IBlueToothPairState;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IBlueToothScanState;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IBluetState;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IHistoryBlueTooth;
import com.github.anglepengcoding.xblue.utils.BlueUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import static android.bluetooth.BluetoothDevice.ACTION_PAIRING_REQUEST;
import static android.content.ContentValues.TAG;
import static com.github.anglepengcoding.xblue.utils.BlueUtils.isBluetoothEnabled;

/**
 * Created by 刘红鹏 on 2021/11/5.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 * 蓝牙管理类
 */

public class BluetoothManger {

    public Context mContext;
    BluetoothAdapter mBluetoothAdapter;
    public boolean printBoolean = false;
    public List<BluetoothDevice> addDeviceList;
    public IBlueTooth iBlueTooth;
    public List<BluetoothDevice> historyDeviceList;
    public IHistoryBlueTooth historyBlueTooth;
    public String address;
    public IBluetState bluetState;
    public IBlueToothPairState blueToothPairState;
    public IBlueToothScanState scanState;

    public BluetoothManger() {
    }

    public void scanBlueTooth(int scanMillis, Context context) {
        this.mContext = context;
        if (bluetoothEnable()) {
            BlueUtils.openBluetooth(context);
        }
        int permissionCheck = ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.e(TAG, "Cannot have location permission");
            return;
        }


        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }

        if (isScanning()) {
            cancelSearchBluetooth();
        }

        addDeviceList = new ArrayList<>();
        historyDeviceList = new ArrayList<>();

        initBluetoothReceiver(mContext);
        try {
            mBluetoothAdapter.startDiscovery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        TimerTask task = new TimerTask() {
            public void run() {
                Message msg = new Message();
                msg.what = 0;
                change.handleMessage(msg);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, scanMillis);

        getPairedData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (historyBlueTooth!=null){
                    historyBlueTooth.historyData(historyDeviceList);
                }
            }
        }).start();
    }


    @SuppressLint("HandlerLeak")
    private
    Handler change = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                cancelSearchBluetooth();
            }
        }
    };

    /**
     * 停止扫描
     */
    public void cancelSearchBluetooth() {
        if (isScanning()) {
            if (mBluetoothAdapter != null) {
                mBluetoothAdapter.cancelDiscovery();
            }
        }

    }

    /**
     * false 未扫描  true 扫描
     *
     * @return true
     */
    private boolean isScanning() {
        try {
            return mBluetoothAdapter.isDiscovering();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private boolean bluetoothEnable() {
        return BlueUtils.bluetoothEnable(mContext);
    }


    private void initBluetoothReceiver(Context context) {

        IntentFilter intent = new IntentFilter();
        intent.addAction(BluetoothDevice.ACTION_FOUND);// 用BroadcastReceiver来取得搜索结果
        intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intent.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intent.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intent.addAction(ACTION_PAIRING_REQUEST);
        intent.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        intent.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        context.registerReceiver(bluetoothReceiver, intent);

    }

    public void unregisterReceiver() {
        try {
            change.removeMessages(1);
            mContext.unregisterReceiver(bluetoothReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isBluetoothEnabled()) {
                Log.e(TAG, "bluetoothAdapter is not enabled");
                throw new NullPointerException();
            }
            String action = intent.getAction();
            BluetoothDevice device;
            // 搜索设备时，取得设备的MAC地址
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (printBoolean) {//是否为打印机
                    if (device.getBluetoothClass().getMajorDeviceClass() != BluetoothClass.Device.Major.MISC
                            && device.getBluetoothClass().getMajorDeviceClass() != BluetoothClass.Device.Major.COMPUTER
                            && device.getBluetoothClass().getMajorDeviceClass() != BluetoothClass.Device.Major.PHONE
                            && device.getBluetoothClass().getMajorDeviceClass() != BluetoothClass.Device.Major.AUDIO_VIDEO
                            && device.getBluetoothClass().getMajorDeviceClass() != BluetoothClass.Device.Major.WEARABLE
                            && device.getBluetoothClass().getMajorDeviceClass() != BluetoothClass.Device.Major.TOY
                            && device.getBluetoothClass().getMajorDeviceClass() != BluetoothClass.Device.Major.UNCATEGORIZED
                            && device.getBluetoothClass().getMajorDeviceClass() != BluetoothClass.Device.Major.HEALTH
                            ) {

                        if (!addDeviceList.contains(device)) {
                            addDeviceList.add(device);
                        }
                    }

                } else {
                    if (!TextUtils.isEmpty(device.getName()) && !TextUtils.isEmpty(device.getAddress())) {
                        if (!addDeviceList.contains(device)) {
                            addDeviceList.add(device);
                        }
                    }

                }
                if (iBlueTooth != null) {
                    iBlueTooth.blueData(addDeviceList);
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Log.e(TAG, " BluetoothDevice ACTION_DISCOVERY_STARTED");
                if (scanState != null) {
                    scanState.started();
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                if (scanState != null) {
                    scanState.finished();
                }
                Log.e(TAG, " BluetoothDevice ACTION_DISCOVERY_FINISHED");
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                Log.e(TAG, " BluetoothDevice ACTION_BOND_STATE_CHANGED");
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_BONDING:
                        //正在配对
                        if (blueToothPairState != null) {
                            blueToothPairState.bond_bonding();
                        }
                        break;
                    case BluetoothDevice.BOND_BONDED:
                        //完成配对
                        if (blueToothPairState != null) {
                            blueToothPairState.bond_bonded();
                        }
                        break;
                    case BluetoothDevice.BOND_NONE:
                        //取消配对
                        if (blueToothPairState != null) {
                            blueToothPairState.bond_none();
                        }
                    default:
                        break;
                }

            } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                Log.e(TAG, " BluetoothDevice ACTION_ACL_CONNECTED");
                //蓝牙已连接
            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                Log.e(TAG, " BluetoothDevice ACTION_ACL_DISCONNECTED");
                //当直接关闭蓝牙时此处不会被触发，只有当蓝牙未关闭并且断开蓝牙时才会触发
            } else if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                Log.e(TAG, " BluetoothDevice ACTION_STATE_CHANGED");
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.e(TAG, " BluetoothDevice ACTION_STATE_CHANGED 手机蓝牙关闭");
                        if (bluetState != null) {
                            bluetState.state_off();
                        }
                        //手机蓝牙关闭
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        //手机蓝牙正在关闭
                        Log.e(TAG, " BluetoothDevice ACTION_STATE_CHANGED 手机蓝牙正在关闭");
                        if (bluetState != null) {
                            bluetState.state_turning_off();
                        }
                        break;
                    case BluetoothAdapter.STATE_ON:
                        //手机蓝牙开启
                        Log.e(TAG, " BluetoothDevice STATE_ON 手机蓝牙正在关闭");
                        if (bluetState != null) {
                            bluetState.state_on();
                        }
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.e(TAG, " BluetoothDevice STATE_TURNING_ON 手机蓝牙正在开启");
                        if (bluetState != null) {
                            bluetState.state_turning_on();
                        }
                        //手机蓝牙正在开启
                        break;
                }
            }
        }

    };


    private void getPairedData() {
        if (mBluetoothAdapter == null) {
            return;
        }

        if (!historyDeviceList.isEmpty()) {
            historyDeviceList.clear();
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices != null && !pairedDevices.isEmpty()) {
            for (BluetoothDevice device : pairedDevices) {
                if (!historyDeviceList.contains(device)) {
                    historyDeviceList.add(device);
                }
            }
        }
    }


    public void startDiscovery(boolean printBoolean) {
        this.printBoolean = printBoolean;
        if (mBluetoothAdapter == null) {
            return;
        }
        if (!mBluetoothAdapter.isDiscovering()) {
            //判断蓝牙是否正在扫描，如果是调用取消扫描方法；如果不是，则开始扫描
            mBluetoothAdapter.startDiscovery();
        }
    }
}
