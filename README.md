# xblue

<h3> maven { url 'https://jitpack.io' }  </h3>
<h3> 添加 implementation 'com.github.AnglePengCoding:xblue:xblue1.0' </h3>

<h4>效果图</h4>

<img src="https://github.com/AnglePengCoding/xblue/blob/main/app/device-2021-11-08-162859.png" width="150px">
<img src="https://github.com/AnglePengCoding/xblue/blob/main/app/device-2021-11-08-162927.png" width="150px">

<h4> 使用 </h4>

` `` 

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
` `` 

  <h4>  功能支持 </h4>
  
  
  
