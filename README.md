

[![](https://jitpack.io/v/AnglePengCoding/xblue.svg)](https://jitpack.io/#AnglePengCoding/xblue)

# xblue 这是一个支持连接蓝牙，wifi库。

<h3>工程目录添加 </h3>

```java  
maven { url 'https://jitpack.io' } 

```


<h3>build添加 </h3>

```java  
implementation 'com.github.AnglePengCoding:xblue:1.1' //蓝牙

implementation 'com.github.AnglePengCoding:xblue:1.2' //支持连接wifi

```

<h4>效果图</h4>

<img src="https://github.com/AnglePengCoding/xblue/blob/main/app/wifi.png" width="150px">
<img src="https://github.com/AnglePengCoding/xblue/blob/main/app/device-2021-11-08-162927.png" width="150px">

<h4> 连接蓝牙使用 </h4>

```java 
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
```

<h4> 连接wifi使用 </h4>

```java 
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

```


<h4> 历史配对过的蓝牙 </h4>

```java 
.scanHistoryCallBack(new IHistoryBlueTooth() {//历史配过的蓝牙
                            @Override
                            public void historyData(List<BluetoothDevice> historyDeviceList) {

                            }
                        })

```

<h4>手机蓝牙状态监听 </h4>

```java 
.blueToothState(new IBluetState() {
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
                        })
```

<h4>蓝牙连接状态监听 </h4>

```java 

.blueToothPairState(new IBlueToothPairState() {
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
                        })


```

  <h3>  功能支持 </h3>
  
  <h4>  支持蓝牙名字显示</h4>
  <h4>  支持一键搜索</h4>
  <h4>  支持只显示打印机的蓝牙</h4>
  <h4> 支持已配对的蓝牙数据</h4>
  <h4> 支持连接wifi</h4>
  <h4> 支持wifi状态回调显示</h4>
 
 
 
 
 <h3>新功能正在完善，如果您有什么建议或者功能请联系我的邮箱1016305858@qq.com </h3> 
