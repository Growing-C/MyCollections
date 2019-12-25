package com.cgy.mycollections.functions.net.wifip2p;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.text.TextUtils;

import appframe.utils.L;

import java.util.ArrayList;

/**
 * 作者：leavesC
 * 时间：2019/2/27 23:58
 * 描述：
 * GitHub：https://github.com/leavesC
 * Blog：https://www.jianshu.com/u/9df45b87cfdf
 */
public class DirectBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "DirectBroadcastReceiver";

    private WifiP2pManager mWifiP2pManager;

    private WifiP2pManager.Channel mChannel;

    private DirectActionListener mDirectActionListener;

    public DirectBroadcastReceiver(WifiP2pManager wifiP2pManager, WifiP2pManager.Channel channel, DirectActionListener directActionListener) {
        mWifiP2pManager = wifiP2pManager;
        mChannel = channel;
        mDirectActionListener = directActionListener;
    }

    public static IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION);
        return intentFilter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        L.e(TAG, "接收到广播： " + intent.getAction());
        if (!TextUtils.isEmpty(intent.getAction())) {
            switch (intent.getAction()) {
                // 用于指示 Wifi P2P 是否可用
                case WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION:
                    int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
                    if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                        mDirectActionListener.wifiP2pEnabled(true);
                    } else {
                        mDirectActionListener.wifiP2pEnabled(false);
                        mDirectActionListener.onPeersAvailable(new ArrayList<WifiP2pDevice>());
                    }
                    break;
                // 对等节点列表发生了变化
                case WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION:
                    mWifiP2pManager.requestPeers(mChannel, new WifiP2pManager.PeerListListener() {
                        @Override
                        public void onPeersAvailable(WifiP2pDeviceList peers) {
                            mDirectActionListener.onPeersAvailable(peers.getDeviceList());
                        }
                    });
                    break;
                // Wifi P2P 的连接状态发生了改变
                case WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION:
                    NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
                    if (networkInfo.isConnected()) {
                        mWifiP2pManager.requestConnectionInfo(mChannel, new WifiP2pManager.ConnectionInfoListener() {
                            @Override
                            public void onConnectionInfoAvailable(WifiP2pInfo info) {
                                mDirectActionListener.onConnectionInfoAvailable(info);
                            }
                        });
                        L.e(TAG, "已连接p2p设备");
                    } else {
                        mDirectActionListener.onDisconnection();
                        L.e(TAG, "与p2p设备已断开连接");
                    }
                    break;
                //本设备的设备信息发生了变化
                case WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION:
                    mDirectActionListener.onSelfDeviceAvailable((WifiP2pDevice) intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));
                    break;
                case WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION://搜索状态改变
                    int discoveryState = intent.getIntExtra(WifiP2pManager.EXTRA_DISCOVERY_STATE, WifiP2pManager.WIFI_P2P_DISCOVERY_STOPPED);
                    boolean isDiscover = discoveryState == WifiP2pManager.WIFI_P2P_DISCOVERY_STARTED;
                    L.e("discoveryState:" + discoveryState + (isDiscover ? "  开始扫描" : "其他"));
                    mDirectActionListener.onDiscoverStateChange(isDiscover);
                    //2是开始搜索  1是结束搜索
                    break;

            }
        }
    }

}

//    BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            L.e("BroadcastReceiver onReceive：" + action);
//            switch (action) {
//                case WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION://wifi状态改变
//                    int wifiState = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
//                    //2是打开  1是关闭  -1是没有获取到
//                    L.e("wifiState:" + wifiState);
////                    mWifiP2pManager.requestPeers();
//                    break;
//                case WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION://搜索到设备
//                    //获取到设备列表信息
//                    WifiP2pDeviceList mPeers = intent.getParcelableExtra(WifiP2pManager.EXTRA_P2P_DEVICE_LIST);
//                    mWifiP2pDeviceList.clear(); //清除旧的信息
//                    mWifiP2pDeviceList.addAll(mPeers.getDeviceList()); //更新信息
//                    mDeviceAdapter.setData(mWifiP2pDeviceList);//更新列表
//                    L.e("搜索到设备 peersSize:" + mPeers.getDeviceList().size());
//                    break;
//                case WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION://连接状态改变
////                    NetworkInfo 的isConnected()可以判断时连接还是断开时接收到的广播。
//                    NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
//                    L.e("1.networkInfo:" + networkInfo.toString());
////                    WifiP2pInfo保存着一些连接的信息，如groupFormed字段保存是否有组建立，
////                    groupOwnerAddress字段保存GO设备的地址信息，isGroupOwner字段判断自己是否是GO设备。
////                    WifiP2pInfo也可以随时用过wifiP2pManager.requestConnectionInfo来获取。
//                    mConnectInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_INFO);
//                    if (mConnectInfo != null) {
//                        L.e("2.wifiP2pInfo:" + mConnectInfo.toString());
//                    }
////                    WifiP2pGroup存放着当前组成员的信息，这个信息只有GO设备可以获取。
////                    同样这个信息也可以通过wifiP2pManager.requestGroupInfo获取，一些方法如下，都比较简单易懂：
//                    WifiP2pGroup wifiP2pGroup = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_GROUP);
//                    if (wifiP2pGroup != null) {
//                        L.e("3.wifiP2pGroup:" + wifiP2pGroup.toString());
//                    }
//                    break;
//                case WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION://此设备状态改变
//                    mCurrentDevice = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
//                    String currentDeviceInfo = "此设备名称：" + mCurrentDevice.deviceName;
//                    L.e("此设备信息改变：" + mCurrentDevice);
//                    break;
//                case WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION://搜索状态改变
//                    int discoveryState = intent.getIntExtra(WifiP2pManager.EXTRA_DISCOVERY_STATE, WifiP2pManager.WIFI_P2P_DISCOVERY_STOPPED);
//                    boolean isDiscover = discoveryState == WifiP2pManager.WIFI_P2P_DISCOVERY_STARTED;
//                    L.e("discoveryState:" + discoveryState);
//                    break;
//                default:
//                    break;
//            }
//        }
//    };