package com.github.anglepengcoding.xblue.wifi;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by 刘红鹏 on 2021/11/15.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 */

public class WifiScan implements WifiClient {

    private WifiManager mWifiManager;
    private Activity activity;
    private IWifiTooth wifiTooth;
    private IWiFiSupplicantState wiFiSupplicantState;

    @Override
    public void openWifi(Activity activity) {
        this.activity = activity;
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
    }

    @Override
    public void closeWifi() {
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
            activity.unregisterReceiver(new NetworkBroadcastReceiver());
            if (wifiTooth != null) {
                wifiTooth = null;
            }
            if (wiFiSupplicantState != null) {
                wiFiSupplicantState = null;
            }
        }
    }

    @Override
    public void setWifiManager(WifiManager wifiManager) {
        try {
            this.mWifiManager = wifiManager;
        } catch (NullPointerException p) {
            Log.e(TAG, "WifiManager: null");
            p.printStackTrace();
        }
    }

    @Override
    public void scanWiFiData(IWifiTooth wifiTooth, IWiFiSupplicantState wiFiSupplicantState) {
        this.wifiTooth = wifiTooth;
        this.wiFiSupplicantState = wiFiSupplicantState;
        registerReceivers(wifiTooth, wiFiSupplicantState);
    }


    private void registerReceivers(IWifiTooth wifiTooth, IWiFiSupplicantState wiFiSupplicantState) {
        try {
            IntentFilter filter = new IntentFilter();
            if (Build.VERSION.SDK_INT >= 28) {
                filter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
            }
            // Android 8.0之后对静态注册的广播增加限制,保护用户隐私,故推荐使用动态注册
            // WifiStateBroadcast 继承自 BroadcastReceiver
            filter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
            filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
            filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
            filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
            activity.registerReceiver(new NetworkBroadcastReceiver(wifiTooth, wiFiSupplicantState), filter);
        } catch (NullPointerException n) {
            Log.e(TAG, "context: null");
            n.printStackTrace();
        }
    }

    /**
     * 广播接收者
     */
    public static class NetworkBroadcastReceiver extends BroadcastReceiver {
        private IWifiTooth wifiTooth;
        private IWiFiSupplicantState wiFiSupplicantState;

        public NetworkBroadcastReceiver() {
        }

        public NetworkBroadcastReceiver(IWifiTooth wifiTooth, IWiFiSupplicantState wiFiSupplicantState) {
            this.wifiTooth = wifiTooth;
            this.wiFiSupplicantState = wiFiSupplicantState;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            //获得WifiManager的实例对象
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            switch (intent.getAction()) {
                //WIFI状态发生变化
                case WifiManager.WIFI_STATE_CHANGED_ACTION:
                    switch (intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)) {
                        case WifiManager.WIFI_STATE_ENABLING:
                            Log.i(TAG, "onReceive: 正在打开 WIFI...");
                            wiFiSupplicantState.wifi_state_enabling();
                            break;
                        case WifiManager.WIFI_STATE_ENABLED:
                            Log.i(TAG, "onReceive: WIFI 已打开");
                            wiFiSupplicantState.wifi_state_enabled();
                            break;
                        case WifiManager.WIFI_STATE_DISABLING:
                            Log.i(TAG, "onReceive: 正在关闭 WIFI...");
                            wiFiSupplicantState.wifi_state_disabling();
                            break;
                        case WifiManager.WIFI_STATE_DISABLED:
                            Log.i(TAG, "onReceive: WIFI 已关闭");
                            wiFiSupplicantState.wifi_state_disabled();
                            break;
                        case WifiManager.WIFI_STATE_UNKNOWN:
                        default:
                            wiFiSupplicantState.wifi_state_unknown();
                            Log.i(TAG, "onReceive: WIFI 状态未知!");
                            break;
                    }
                    break;
                // WIFI扫描完成
                case WifiManager.SCAN_RESULTS_AVAILABLE_ACTION:
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        boolean isUpdated = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
                        Log.i(TAG, "onReceive: WIFI扫描  " + (isUpdated ? "完成" : "未完成"));
                    } else {
                        Log.i(TAG, "onReceive: WIFI扫描完成");
                    }
                    if (wifiTooth != null) {
                        wifiTooth.wifiData(wifiManager.getScanResults());
                    }
                    break;
                //WIFI网络状态变化通知
                case WifiManager.NETWORK_STATE_CHANGED_ACTION:
                    WifiInfo wifiInfo = intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
                    if (null != wifiInfo && wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
                        String ssid = wifiInfo.getSSID();
                        Log.i(TAG, "onReceive: 网络连接成功 ssid = " + ssid);
                    }
                    break;
                //WIFI连接状态变化的时候
                case WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION:
                    boolean isConnected = intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false);
                    Log.i(TAG, "onReceive: SUPPLICANT_CONNECTION_CHANGE_ACTION  isConnected = " + isConnected);
                    break;
                //wifi连接结果通知   WIFI连接请求状态发生改变
                case WifiManager.SUPPLICANT_STATE_CHANGED_ACTION:
                    // 获取连接状态
                    SupplicantState supplicantState = intent.getParcelableExtra(WifiManager.EXTRA_NEW_STATE);
                    switch (supplicantState) {
                        case INTERFACE_DISABLED: // 接口禁用
                            Log.i(TAG, "onReceive: INTERFACE_DISABLED 接口禁用");
                            break;
                        case DISCONNECTED:// 断开连接
                        case INACTIVE: // 不活跃的
                            WifiInfo connectFailureInfo = wifiManager.getConnectionInfo();
                            Log.i(TAG, "onReceive: INACTIVE 不活跃的  connectFailureInfo = " + connectFailureInfo);
                            if (null != connectFailureInfo) {
                                // 断开连接
                                int networkId = connectFailureInfo.getNetworkId();
                                boolean isDisable = wifiManager.disableNetwork(networkId);
                                boolean isDisconnect = wifiManager.disconnect();
                                Log.i(TAG, "onReceive: 断开连接  =  " + (isDisable && isDisconnect));
                            }
                            break;
                        case SCANNING: // 正在扫描
                            Log.i(TAG, "onReceive: SCANNING 正在扫描");
                            wiFiSupplicantState.scanning();
                            break;
                        case AUTHENTICATING: // 正在验证
                            Log.i(TAG, "onReceive: AUTHENTICATING: // 正在验证");
                            wiFiSupplicantState.authenticating();
                            break;
                        case ASSOCIATING: // 正在关联
                            Log.i(TAG, "onReceive: ASSOCIATING: // 正在关联");
                            wiFiSupplicantState.associating();
                            break;
                        case ASSOCIATED: // 已经关联
                            wiFiSupplicantState.associated();
                            Log.i(TAG, "onReceive: ASSOCIATED: // 已经关联");
                            break;
                        case FOUR_WAY_HANDSHAKE: // 四次握手
                            Log.i(TAG, "onReceive: FOUR_WAY_HANDSHAKE:");
                            break;
                        case GROUP_HANDSHAKE:  // 组握手
                            Log.i(TAG, "onReceive: GROUP_HANDSHAKE:");
                            break;
                        case COMPLETED: // 完成
                            Log.i(TAG, "onReceive: WIFI_CONNECT_SUCCESS: // 完成");
                            WifiInfo connectSuccessInfo = wifiManager.getConnectionInfo();
                            if (null != connectSuccessInfo) {
                                wiFiSupplicantState.completed();
                            }
                            break;
                        case DORMANT: // 休眠
                            Log.i(TAG, "onReceive: DORMANT:");
                            break;
                        case UNINITIALIZED: // 未初始化
                            Log.i(TAG, "onReceive: UNINITIALIZED: // 未初始化");
                            break;
                        case INVALID: // 无效的
                            Log.i(TAG, "onReceive: INVALID: // 无效的");
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    }


}
