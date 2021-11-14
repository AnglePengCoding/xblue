[![](https://jitpack.io/v/AnglePengCoding/xblue.svg)](https://jitpack.io/#AnglePengCoding/xblue)

# xblue

<h3> 添加 maven { url 'https://jitpack.io' }  </h3>
<h3> 添加  implementation 'com.github.AnglePengCoding:xblue:1.0' </h3>

<h4>效果图</h4>

<img src="https://github.com/AnglePengCoding/xblue/blob/main/app/device-2021-11-08-162859.png" width="150px">
<img src="https://github.com/AnglePengCoding/xblue/blob/main/app/device-2021-11-08-162927.png" width="150px">

<h4> 使用 </h4>

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

  <h3> 更新日志 </h3>
 
  <h4> 手机蓝牙状态监听 </h4>
  <h4> 蓝牙连接状态监听 </h4>
 
 
 
 
 <h3>新功能正在完善，如果您有什么建议或者功能请联系我的邮箱1016305858@qq.com </h3> 
