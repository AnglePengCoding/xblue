package com.github.anglepengcoding.myapplication;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.anglepengcoding.xblue.IBlueTooth;
import com.github.anglepengcoding.xblue.XBluetooth;

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
    private BaseQuickAdapter<BluetoothDevice, BaseViewHolder> adapter;
    private String[] permissions = new String[]{
            ACCESS_FINE_LOCATION, BLUETOOTH_ADMIN,
            BLUETOOTH, ACCESS_COARSE_LOCATION, STORAGE, INTERNET};
    private int requestcode = 0x33;
    XBluetooth.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mBtScan = findViewById(R.id.mBtScan);
        mBtWrite = findViewById(R.id.mBtWrite);

        requestPermissions(permissions, requestcode);
        initAdapter();

        mBtScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new XBluetooth
                        .Builder(MainActivity.this)
                        .scanBlueTooth(true, 5000) //开启扫描 停止时间
                        .scanIsPrint(true)//true 显示打印机的蓝牙 false显示全部数据
                        .cancel()//取消扫描
                        .scanCallBack(new IBlueTooth() {//搜索到蓝牙设备
                            @Override
                            public void blueData(List<BluetoothDevice> addDeviceList) {
                                adapter.setNewData(addDeviceList);
                            }
                        });
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        builder.unregisterReceiver(MainActivity.this);
    }

    private void initAdapter() {
        adapter = new BaseQuickAdapter<BluetoothDevice, BaseViewHolder>(R.layout.activity_blue_tooth_device) {
            @Override
            protected void convert(BaseViewHolder helper, BluetoothDevice item) {
                helper.setText(R.id.mTvName, item.getName());
                helper.setText(R.id.mTvAddress, item.getAddress());
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
