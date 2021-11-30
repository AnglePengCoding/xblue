package com.github.anglepengcoding.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;


import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.BLUETOOTH;
import static android.Manifest.permission.BLUETOOTH_ADMIN;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission_group.STORAGE;


public class MainActivity extends Activity {


    private int requestcode = 0x33;
    private String[] permissions = new String[]{ACCESS_FINE_LOCATION, BLUETOOTH_ADMIN, BLUETOOTH, ACCESS_COARSE_LOCATION, STORAGE, INTERNET};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
    }

    private void initUi() {
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestcode);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    public void blueScan(View view) {
        startActivity(new Intent(this,BlueActivity.class));
    }

    public void wifiScan(View view) {
        startActivity(new Intent(App.getAppContext(), WifiActivity.class));
    }

    public void usbScan(View view) {
        startActivity(new Intent(App.getAppContext(), UsbActivity.class));
    }

    public void goGit(View view) {
        startActivity(new Intent(App.getAppContext(), WebActivity.class));
    }
}
