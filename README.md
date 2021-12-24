[![](https://jitpack.io/v/AnglePengCoding/xblue.svg)](https://jitpack.io/#AnglePengCoding/xblue)

<h2>介绍</h2>

<h3>xblue是一款支持 usb wifi 蓝牙扫描库 支持Androidx，Anroid版本 </h3>
            
<img src="https://github.com/AnglePengCoding/xblue/blob/main/app/log.png" >


<h4> Apk 扫描二维码下载 </h4>
<img src="https://github.com/AnglePengCoding/xblue/blob/main/app/xz.png" >
<h3> Step 1.  Add it in your root build.gradle at the end of repositories: </h3>

```java  

maven { url 'https://jitpack.io' } 

```

<h3> Step 2. Add the dependency </h3>

```java  

implementation 'com.github.AnglePengCoding:xblue:1.4'

```

<h4>效果图</h4>
<img src="https://github.com/AnglePengCoding/xblue/blob/main/app/device-2021-11-08-162927.png" width="150px">

<h4> 蓝牙扫描使用 </h4>

```java 
mBtScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XBluetooth
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

<h4> wifi扫描使用 </h4>

```java 
                            mBtScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XBluetooth.Builder()
                        .openWifi(MainActivity.this)
                        .scanWiFiData(new IWifiTooth() {
                            @Override
                            public void wifiData(List<ScanResult> addDeviceList) { //wifi数据
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
<h4> usb扫描使用 </h4>

```java 

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

public interface IBluetState {

    /**
     * 手机蓝牙关闭
     */
    void state_off();

    /**
     * 手机蓝牙正在关闭
     */
    void state_turning_off();

    /**
     * 手机蓝牙开启
     */
    void state_on();

    /**
     * 手机蓝牙正在开启
     */
    void state_turning_on();
}

```

<h4>蓝牙连接状态监听 </h4>

```java 

public interface IBlueToothPairState {

    /**
     * 正在配对
     */
    void bond_bonding();

    /**
     * 完成配对
     */
    void bond_bonded();

    /**
     * 取消配对
     */
    void bond_none();
}


```


<h4>wifi连接状态监听 </h4>

```java 
  /**
     * 正在打开 WIFI-WIFI状态发生变化
     */
    void wifi_state_enabling();

    /**
     * WIFI 已打开-WIFI状态发生变化
     */
    void wifi_state_enabled();

    /**
     * WIFI 正在关闭
     */
    void wifi_state_disabling();

    /**
     * WIFI 已关闭
     */
    void wifi_state_disabled();

    /**
     *WIFI 状态未知
     */
    void wifi_state_unknown();

    /**
     * 正在扫描-wifi连接结果通知   WIFI连接请求状态发生改变
     */
    void scanning();


    /**
     * 正在验证-wifi连接结果通知   WIFI连接请求状态发生改变
     */
    void authenticating();

    /**
     * 正在关联-wifi连接结果通知   WIFI连接请求状态发生改变
     */
    void associating();

    /**
     * 已经关联-wifi连接结果通知   WIFI连接请求状态发生改变
     */
    void associated();

    /**
     * 完成-wifi连接结果通知   WIFI连接请求状态发生改变
     */
    void completed();



```

<h4> 请求usb权限 </h4>

```java 

   helper.getView(R.id.list_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //请求usb权限
                        usbBuilder.requestPermission(item);
                    }
                });

```

  <h3>  百忙之中，给个star</h3>


  <h3>  功能支持 </h3>
  
  <h4> 支持蓝牙扫描</h4>
  <h4> 支持wifi扫描</h4>
  <h4> 支持Usb扫描</h4>
  <h4> 支持Usb请求权限</h4>
  <h4> 支持一键搜索</h4>
  <h4> 支持只显示打印机的蓝牙</h4>
  <h4> 支持已配对的蓝牙数据</h4>
  <h4> 支持连接wifi</h4>
  <h4> 支持wifi状态回调显示</h4>

 
 
 
 <h3>新功能正在完善，如果您有什么建议或者功能请联系我的邮箱1016305858@qq.com </h3> 
