package com.cgy.mycollections.functions.net.wifiap;

import android.net.wifi.ScanResult;
import android.net.wifi.p2p.WifiP2pDevice;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
public class WifiAdapter extends RecyclerView.Adapter<WifiAdapter.WifiHolder> {
    List<ScanResult> mWifiList;

    private OnTItemClickListener<ScanResult> mOnItemClickListener;

    public void setOnItemClickListener(OnTItemClickListener<ScanResult> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setData(List<ScanResult> wifiP2pDeviceList) {
        this.mWifiList = wifiP2pDeviceList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WifiAdapter.WifiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WifiHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_p2p_device, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WifiAdapter.WifiHolder holder, int position) {
        holder.setData(mWifiList.get(position));
    }

    @Override
    public int getItemCount() {
        return mWifiList == null ? 0 : mWifiList.size();
    }

    class WifiHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.device_name)
        TextView deviceName;
        @BindView(R.id.connect)
        Button connectBtn;
        @BindView(R.id.disconnect)
        Button disconnectBtn;

        public WifiHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(ScanResult wifi) {
            String nameWithStatus = wifi.SSID;
            deviceName.setText(nameWithStatus);
            connectBtn.setVisibility(View.VISIBLE);
            disconnectBtn.setVisibility(View.GONE);
        }

        @OnClick({R.id.connect, R.id.disconnect})
        public void onClick(View v) {
            int pos = getAdapterPosition();
            switch (v.getId()) {
                case R.id.connect:
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClickOne(pos, mWifiList.get(pos));
                    break;
                case R.id.disconnect:
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClickTwo(pos, mWifiList.get(pos));
                    break;
                default:
                    break;
            }
        }
    }
}
