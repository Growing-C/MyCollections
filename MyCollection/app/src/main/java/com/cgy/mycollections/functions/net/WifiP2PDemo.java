package com.cgy.mycollections.functions.net;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.cgy.mycollections.base.BaseActivity;
import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.net.wifip2p.DirectActionListener;
import com.cgy.mycollections.functions.net.wifip2p.DirectBroadcastReceiver;
import com.cgy.mycollections.functions.net.wifip2p.OnProgressChangListener;
import com.cgy.mycollections.functions.net.wifip2p.P2pDeviceAdapter;
import com.cgy.mycollections.functions.net.wifip2p.TransferData;
import com.cgy.mycollections.functions.net.wifip2p.WifiSendTask;
import com.cgy.mycollections.functions.net.wifip2p.WifiServerService;
import com.cgy.mycollections.listeners.OnTItemClickListener;
import com.cgy.mycollections.utils.L;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WifiP2PDemo extends BaseActivity {
    @BindView(R.id.p2p_device_list)
    RecyclerView mP2pDeviceListV;
    @BindView(R.id.device_info)
    TextView mCurrentDeviceInfoV;
    @BindView(R.id.state)
    TextView mStateV;
    @BindView(R.id.wifi_ssid)
    TextInputEditText mSsidV;
    @BindView(R.id.wifi_password)
    TextInputEditText mPasswordV;
    @BindView(R.id.mode_toggle)
    ToggleButton mModeToggle;//isChecked?发送者：接受者；---发送者是client  接受者是group owner

    P2pDeviceAdapter mDeviceAdapter;
    List<WifiP2pDevice> mWifiP2pDeviceList = new ArrayList<>();

    WifiP2pDevice mCurrentDevice;
    WifiP2pDevice mConnectedDevice;
    WifiP2pInfo mConnectInfo;

    WifiP2pManager mWifiP2pManager;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver mReceiver;


    private WifiServerService mReceiveService;
    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            L.e("onServiceConnected");

            WifiServerService.MyBinder binder = (WifiServerService.MyBinder) service;
            mReceiveService = binder.getService();
            mReceiveService.setProgressChangListener(new OnProgressChangListener() {
                @Override
                public void onProgressChanged(final TransferData data, final int progress) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showLoadingDialog("数据接收中，进度：" + progress);
                        }
                    });
                }

                @Override
                public void onTransferFinished(final TransferData data) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("接收完成");
                            dismissLoadingDialog();
                            if (data != null && data.dataType == 1) {
                                mSsidV.setText(data.transferContent);
                            }
                        }
                    });
                }
            });

            startService(new Intent(WifiP2PDemo.this, WifiServerService.class));
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            L.e("onServiceDisconnected");
            mReceiveService = null;
            bindReceiveService();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_p2p_demo);
        ButterKnife.bind(this);

        mP2pDeviceListV.setLayoutManager(new LinearLayoutManager(this));
        mDeviceAdapter = new P2pDeviceAdapter();
        mDeviceAdapter.setOnItemClickListener(new OnTItemClickListener<WifiP2pDevice>() {
            @Override
            public void onItemClickOne(int position, WifiP2pDevice data) {
                connectDevice(data);
            }

            @Override
            public void onItemClickTwo(int position, WifiP2pDevice data) {
                disconnectDevice(data);
            }
        });
        mP2pDeviceListV.setAdapter(mDeviceAdapter);


        mWifiP2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mWifiP2pManager.initialize(this, getMainLooper(), new WifiP2pManager.ChannelListener() {
            @Override
            public void onChannelDisconnected() {
                L.e("onChannelDisconnected！！！！！！！！！！！！！！！！！！！！！！！！！！！");
            }
        });


        mReceiver = new DirectBroadcastReceiver(mWifiP2pManager, mChannel, new DirectActionListener() {
            @Override
            public void wifiP2pEnabled(boolean enabled) {
                L.e("111111111111111111 wifiP2pEnabled:" + enabled);
            }

            @Override
            public void onDiscoverStateChange(boolean isDiscovering) {
                String state = isDiscovering ? "搜索中" : "搜索结束";
                mStateV.setText(state);
                L.e("onDiscoverStateChange:" + state);
            }

            @Override
            public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
                dismissLoadingDialog();
                mWifiP2pDeviceList.clear();
                mDeviceAdapter.notifyDataSetChanged();
//                btn_disconnect.setEnabled(true);
//                btn_chooseFile.setEnabled(true);
                L.e("连接状态改变！获取到连接信息！");
                L.e("onConnectionInfoAvailable groupFormed: " + wifiP2pInfo.groupFormed);
                L.e("onConnectionInfoAvailable isGroupOwner: " + wifiP2pInfo.isGroupOwner);
                L.e("onConnectionInfoAvailable getHostAddress: " + wifiP2pInfo.groupOwnerAddress.getHostAddress());
                StringBuilder stringBuilder = new StringBuilder();


                if (mConnectedDevice != null) {
                    stringBuilder.append("连接的设备名：");
                    stringBuilder.append(mConnectedDevice.deviceName);
                    stringBuilder.append("\n");
                    stringBuilder.append("连接的设备的地址：");
                    stringBuilder.append(mConnectedDevice.deviceAddress);
                }
                stringBuilder.append("\n");
                stringBuilder.append("是否群主：");
                stringBuilder.append(wifiP2pInfo.isGroupOwner ? "是群主" : "非群主");
                stringBuilder.append("\n");
                stringBuilder.append("群主IP地址：");
                stringBuilder.append(wifiP2pInfo.groupOwnerAddress.getHostAddress());
                mStateV.setText(stringBuilder);
                if (wifiP2pInfo.groupFormed) {
                    mModeToggle.setChecked(!wifiP2pInfo.isGroupOwner);
                    if (!wifiP2pInfo.isGroupOwner) {
                        L.e("创建group完成，此设备是client");
                        mConnectInfo = wifiP2pInfo;
                    } else {
                        L.e("创建group完成，此设备是owner");
                        bindReceiveService();
                    }
                }

            }

            @Override
            public void onDisconnection() {
                showToast("已断开连接");
                mWifiP2pDeviceList.clear();
                mDeviceAdapter.notifyDataSetChanged();
                mStateV.setText(null);
                mConnectInfo = null;
            }

            @Override
            public void onSelfDeviceAvailable(WifiP2pDevice wifiP2pDevice) {
                StringBuffer sb = new StringBuffer();
                sb.append("获取到本机信息--");
                sb.append("DeviceName: " + wifiP2pDevice.deviceName);
                sb.append("DeviceAddress: " + wifiP2pDevice.deviceAddress);
                sb.append("Status: " + wifiP2pDevice.status);
                mCurrentDeviceInfoV.setText(sb.toString());
                mCurrentDevice = wifiP2pDevice;
                L.e("onSelfDeviceAvailable:" + sb.toString());
            }

            @Override
            public void onPeersAvailable(Collection<WifiP2pDevice> wifiP2pDeviceList) {
                mWifiP2pDeviceList.clear(); //清除旧的信息
                mWifiP2pDeviceList.addAll(wifiP2pDeviceList); //更新信息
                mDeviceAdapter.setData(mWifiP2pDeviceList);//更新列表
                L.e("搜索到设备 peersSize:" + wifiP2pDeviceList.size());
                dismissLoadingDialog();
            }

            @Override
            public void onChannelDisconnected() {
                L.e("onChannelDisconnected");
            }
        });
        registerReceiver(mReceiver, DirectBroadcastReceiver.getIntentFilter());

    }

    private void bindReceiveService() {
        L.e("owner bindReceiveService准备接收数据");
        Intent intent = new Intent(WifiP2PDemo.this, WifiServerService.class);
        bindService(intent, mServiceConn, BIND_AUTO_CREATE);
    }


    @OnClick({R.id.create_group, R.id.remove_group, R.id.send, R.id.discover, R.id.stop_discover})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_group:
                createGroup();
                break;
            case R.id.remove_group:
                removeGroup();
                break;
            case R.id.send:
                sendData();
                break;
            case R.id.discover:
                startDiscover();
                break;
            case R.id.stop_discover:
                stopDiscover();
                break;
            default:
                break;
        }
    }

    private void createGroup() {
        showLoadingDialog("正在创建群组");
        mWifiP2pManager.createGroup(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                dismissLoadingDialog();
                showToast("createGroup onSuccess");
            }

            @Override
            public void onFailure(int reason) {
                dismissLoadingDialog();
                showToast("createGroup onFailure" + reason);
            }
        });
    }

    private void removeGroup() {
        mWifiP2pManager.removeGroup(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                L.e("removeGroup onSuccess");
                showToast("onSuccess");
            }

            @Override
            public void onFailure(int reason) {
                L.e("removeGroup onFailure");
                showToast("onFailure");
            }
        });
    }

    /**
     * 连接设备
     *
     * @param selectDevice
     */
    public void connectDevice(final WifiP2pDevice selectDevice) {
        String connectHint = "请求连接 目标设备:" + selectDevice.deviceName + "--" + selectDevice.deviceAddress + "-状态：" + selectDevice.status;
        L.e(connectHint);
        //状态3表示可以连接
        showToast(connectHint);
        if (selectDevice.status == WifiP2pDevice.AVAILABLE) {
            WifiP2pConfig config = new WifiP2pConfig();
            config.deviceAddress = selectDevice.deviceAddress;
            config.wps.setup = WpsInfo.PBC;

            showLoadingDialog("正在连接 " + selectDevice.deviceName);
            mWifiP2pManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    mConnectedDevice = selectDevice;
                    L.e("connect success:" + selectDevice.status);
                    showToast("连接成功！");
                    String currentDeviceInfo = "已连接设备：" + selectDevice.deviceName;
                    mCurrentDeviceInfoV.setText(currentDeviceInfo);
                }

                @Override
                public void onFailure(int reason) {
                    L.e("connect failure");
                    showToast("连接失败 " + reason);
                    dismissLoadingDialog();
                }
            });
        }
    }

    /**
     * 断开连接
     *
     * @param selectDevice
     */
    public void disconnectDevice(WifiP2pDevice selectDevice) {
        String connectHint = "请求断开连接 目标设备" + selectDevice.deviceName + "-状态：" + selectDevice.status;
        showToast(connectHint);
        if (selectDevice.status == WifiP2pDevice.CONNECTED) {
            mWifiP2pManager.removeGroup(mChannel, new WifiP2pManager.ActionListener() {
                public void onSuccess() {
                    mConnectedDevice = null;
                    L.e(" remove group success");
                }

                public void onFailure(int reason) {
                    L.e(" remove group fail ");
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);

        if (mReceiveService != null) {
            mReceiveService.setProgressChangListener(null);
            unbindService(mServiceConn);
        }
        removeGroup();
        stopService(new Intent(this, WifiServerService.class));
        stopDiscover();
    }

    /**
     * 发送数据
     */
    public void sendData() {
        if (mConnectInfo == null) {
            showToast("设备未连接");
            return;
        }
        if (mModeToggle.isChecked()) {
            //发送者
            String sendContent = mSsidV.getText().toString();
            if (TextUtils.isEmpty(sendContent)) {
                showToast("请输入数据");
                return;
            }
            TransferData transferData = new TransferData(1, sendContent, null);
            L.e("准备发送信息：" + transferData);
            new WifiSendTask(this, transferData).execute(mConnectInfo.groupOwnerAddress.getHostAddress());
        } else {
            showToast("不是发送者，无法发送数据");
        }
    }

    public void startDiscover() {
        showLoadingDialog("正在搜索附近设备", new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                stopDiscover();
            }
        });

        mWifiP2pDeviceList.clear();
        mDeviceAdapter.notifyDataSetChanged();
        //搜寻附近带有 Wi-Fi P2P 的设备
        mWifiP2pManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                showToast("开始搜索");
            }

            @Override
            public void onFailure(int reason) {
                showToast("开始搜索失败：" + reason);
                dismissLoadingDialog();
            }
        });
    }

    public void stopDiscover() {
        mWifiP2pManager.stopPeerDiscovery(mChannel, null);

        mWifiP2pDeviceList.clear();
        mDeviceAdapter.notifyDataSetChanged();
        showToast("取消搜索");
    }

//    /**
//     * 读取输入流数据，返回一个byte数组
//     *
//     * @param inStream
//     * @return
//     */
//    public static byte[] readInputStream(InputStream inStream) throws Exception {
//        byte[] buffer = new byte[1024];
//        int len = -1;
//        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//        while ((len = inStream.read(buffer)) != -1) {
//            outStream.write(buffer, 0, len);// 把buffer里的数据写入流中
//
//        }
//        byte[] data = outStream.toByteArray();
//        outStream.close();
//        inStream.close();
//
//        return data;
//    }


}
