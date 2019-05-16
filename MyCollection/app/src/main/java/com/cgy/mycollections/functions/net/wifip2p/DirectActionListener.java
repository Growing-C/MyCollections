package com.cgy.mycollections.functions.net.wifip2p;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;

import java.util.Collection;

/**
 * Description :
 * Author :cgy
 * Date :2019/5/15
 */
public interface DirectActionListener extends WifiP2pManager.ChannelListener {

    void wifiP2pEnabled(boolean enabled);

    void onDiscoverStateChange(boolean isDiscovering);

    void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo);

    void onDisconnection();

    void onSelfDeviceAvailable(WifiP2pDevice wifiP2pDevice);

    void onPeersAvailable(Collection<WifiP2pDevice> wifiP2pDeviceList);

}
