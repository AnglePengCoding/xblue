package com.github.anglepengcoding.myapplication;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.anglepengcoding.xblue.XBluetooth;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IBlueTooth;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IBlueToothPairState;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IBluetState;
import com.github.anglepengcoding.xblue.bluetooth.bluetooth_interface.IHistoryBlueTooth;
import com.github.anglepengcoding.xblue.usb.IAppointUsbData;
import com.github.anglepengcoding.xblue.usb.IUsbTooth;
import com.github.anglepengcoding.xblue.usb.UsbManger;
import com.github.anglepengcoding.xblue.wifi.IWiFiSupplicantState;
import com.github.anglepengcoding.xblue.wifi.IWifiTooth;

import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.BLUETOOTH;
import static android.Manifest.permission.BLUETOOTH_ADMIN;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission_group.STORAGE;


public class MainActivity extends Activity {

    private RecyclerView mRecyclerView;
    private Button mBtScan, mBtWrite;


    private int requestcode = 0x33;
    private String[] permissions = new String[]{ACCESS_FINE_LOCATION, BLUETOOTH_ADMIN, BLUETOOTH, ACCESS_COARSE_LOCATION, STORAGE, INTERNET};


    private BaseQuickAdapter<ScanResult, BaseViewHolder> wifiAdapter;
    private BaseQuickAdapter<UsbDevice, BaseViewHolder> usbAdapter;
    private BaseQuickAdapter<BluetoothDevice, BaseViewHolder> adapter;

    private XBluetooth.Builder usbBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
        initClickListener();
        initBluetoothAdapter();//蓝牙
        initWifiAdapter();//wifi
        initUsbAdapter();//串口
    }

    private void initUi() {
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mBtScan = findViewById(R.id.mBtScan);
        mBtWrite = findViewById(R.id.mBtWrite);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestcode);
        }
    }

    private void initClickListener() {
        //扫描蓝牙
        mBtScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XBluetooth
                        .Builder(MainActivity.this)
                        .scanBlueTooth(true, 5000) //开启扫描 停止时间
                        .scanIsPrint(false)//true 显示打印机的蓝牙 false显示全部数据
                        .cancel()//取消扫描
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
            }
        });


        //扫描wifi
        mBtScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XBluetooth.Builder()
                        .openWifi(MainActivity.this)
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
        });

        //扫描usb
        mBtScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usbBuilder = new XBluetooth
                        .Builder()
                        .openUsb(MainActivity.this)
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == requestcode) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    final int[] grantResults = new int[permissions.length];
                    PackageManager packageManager = MainActivity.this.getPackageManager();
                    String packageName = MainActivity.this.getPackageName();
                    final int permissionCount = permissions.length;
                    for (int i = 0; i < permissionCount; i++) {
                        grantResults[i] = packageManager.checkPermission(
                                permissions[i], packageName);
                    }
                    MainActivity.this.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            });

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    // onResume 中进行调用
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                //TODO 提示权限已经被禁用 且不在提示
                return;
            }
            ActivityCompat.requestPermissions(this,
                    permissions,
                    requestcode);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
    }
}
