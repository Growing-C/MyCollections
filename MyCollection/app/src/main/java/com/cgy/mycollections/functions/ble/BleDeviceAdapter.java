package com.cgy.mycollections.functions.ble;

import android.bluetooth.BluetoothDevice;
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
 * Date :2019/5/16
 */
public class BleDeviceAdapter extends RecyclerView.Adapter<BleDeviceAdapter.BLEDeviceHolder> {

    List<BluetoothDevice> mWifiP2pDeviceList;

    private OnTItemClickListener<BluetoothDevice> mOnItemClickListener;

    public void setOnItemClickListener(OnTItemClickListener<BluetoothDevice> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setData(List<BluetoothDevice> wifiP2pDeviceList) {
        this.mWifiP2pDeviceList = wifiP2pDeviceList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BleDeviceAdapter.BLEDeviceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BLEDeviceHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_p2p_device, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BleDeviceAdapter.BLEDeviceHolder holder, int position) {
        holder.setData(mWifiP2pDeviceList.get(position));
    }

    @Override
    public int getItemCount() {
        return mWifiP2pDeviceList == null ? 0 : mWifiP2pDeviceList.size();
    }

    class BLEDeviceHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.device_name)
        TextView deviceName;
        @BindView(R.id.connect)
        Button connectBtn;
        @BindView(R.id.disconnect)
        Button disconnectBtn;

        public BLEDeviceHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void setData(BluetoothDevice device) {
            String nameWithStatus = "name:" + device.getName() + " \naddress:" + device.getAddress();
            deviceName.setText(nameWithStatus);
            disconnectBtn.setVisibility(View.GONE);
//            if (device.status == WifiP2pDevice.AVAILABLE) {//可连接
//                connectBtn.setVisibility(View.VISIBLE);
//                disconnectBtn.setVisibility(View.GONE);
//            } else if (device.status == WifiP2pDevice.CONNECTED) {//已连接
//                connectBtn.setVisibility(View.GONE);
//                disconnectBtn.setVisibility(View.VISIBLE);
//            } else {//其他状态
//                connectBtn.setVisibility(View.GONE);
//                disconnectBtn.setVisibility(View.GONE);
//            }
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