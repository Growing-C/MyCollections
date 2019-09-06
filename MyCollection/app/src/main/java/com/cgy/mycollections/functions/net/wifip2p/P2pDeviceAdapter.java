package com.cgy.mycollections.functions.net.wifip2p;

import android.net.wifi.p2p.WifiP2pDevice;
import androidx.annotation.NonNull;
//import androidx.appcompat.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cgy.mycollections.R;
import com.cgy.mycollections.listeners.OnTItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description :
 * Author :cgy
 * Date :2019/5/15
 */
public class P2pDeviceAdapter extends RecyclerView.Adapter<P2pDeviceAdapter.P2pDeviceHolder> {

    List<WifiP2pDevice> mWifiP2pDeviceList;

    private OnTItemClickListener<WifiP2pDevice> mOnItemClickListener;

    public void setOnItemClickListener(OnTItemClickListener<WifiP2pDevice> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setData(List<WifiP2pDevice> wifiP2pDeviceList) {
        this.mWifiP2pDeviceList = wifiP2pDeviceList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public P2pDeviceAdapter.P2pDeviceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new P2pDeviceHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_p2p_device, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull P2pDeviceAdapter.P2pDeviceHolder holder, int position) {
        holder.setData(mWifiP2pDeviceList.get(position));
    }

    @Override
    public int getItemCount() {
        return mWifiP2pDeviceList == null ? 0 : mWifiP2pDeviceList.size();
    }

    class P2pDeviceHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.device_name)
        TextView deviceName;
        @BindView(R.id.connect)
        Button connectBtn;
        @BindView(R.id.disconnect)
        Button disconnectBtn;

        public P2pDeviceHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mOnItemClickListener != null) {
//                        int pos = getAdapterPosition();
//                        mOnItemClickListener.onItemClickOne(pos, mWifiP2pDeviceList.get(pos));
//                    }
//                }
//            });
        }

        public void setData(WifiP2pDevice device) {
            String nameWithStatus = device.deviceName + " status:" + device.status +
                    (device.status == WifiP2pDevice.CONNECTED ? "\n已连接" : "\n未连接");
            deviceName.setText(nameWithStatus);
            if (device.status == WifiP2pDevice.AVAILABLE) {//可连接
                connectBtn.setVisibility(View.VISIBLE);
                disconnectBtn.setVisibility(View.GONE);
            } else if (device.status == WifiP2pDevice.CONNECTED) {//已连接
                connectBtn.setVisibility(View.GONE);
                disconnectBtn.setVisibility(View.VISIBLE);
            } else {//其他状态
                connectBtn.setVisibility(View.GONE);
                disconnectBtn.setVisibility(View.GONE);
            }
        }

        @OnClick({R.id.connect, R.id.disconnect})
        public void onClick(View v) {
            int pos = getAdapterPosition();
            switch (v.getId()) {
                case R.id.connect:
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClickOne(pos, mWifiP2pDeviceList.get(pos));
                    break;
                case R.id.disconnect:
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClickTwo(pos, mWifiP2pDeviceList.get(pos));
                    break;
                default:
                    break;
            }
        }
    }
}
