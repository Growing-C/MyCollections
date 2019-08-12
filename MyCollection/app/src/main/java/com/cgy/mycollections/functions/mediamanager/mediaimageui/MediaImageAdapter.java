package com.cgy.mycollections.functions.mediamanager.mediaimageui;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.mediamanager.images.ThumbnailInfo;
import com.cgy.mycollections.listeners.OnTItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description :
 * Author :cgy
 * Date :2019/5/16
 */
public class MediaImageAdapter extends RecyclerView.Adapter<MediaImageAdapter.MediaImageHolder> {

    List<ThumbnailInfo> mThumbnailList;

    private OnTItemClickListener<ThumbnailInfo> mOnItemClickListener;

    public void setOnItemClickListener(OnTItemClickListener<ThumbnailInfo> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setData(List<ThumbnailInfo> thumbnailInfoList) {
        this.mThumbnailList = thumbnailInfoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MediaImageAdapter.MediaImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MediaImageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MediaImageAdapter.MediaImageHolder holder, int position) {
        holder.setData(mThumbnailList.get(position));
    }

    @Override
    public int getItemCount() {
        return mThumbnailList == null ? 0 : mThumbnailList.size();
    }

    class MediaImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView imageV;
        @BindView(R.id.path)
        TextView pathV;

        public MediaImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void setData(ThumbnailInfo thumbnailInfo) {
            pathV.setText(thumbnailInfo.data);

        }

//        @OnClick({R.id.connect, R.id.disconnect})
//        public void onClick(View v) {
//            int pos = getAdapterPosition();
//            switch (v.getId()) {
//                case R.id.connect:
//                    if (mOnItemClickListener != null)
//                        mOnItemClickListener.onItemClickOne(pos, mWifiP2pDeviceList.get(pos));
//                    break;
//                case R.id.disconnect:
//                    if (mOnItemClickListener != null)
//                        mOnItemClickListener.onItemClickTwo(pos, mWifiP2pDeviceList.get(pos));
//                    break;
//                default:
//                    break;
//            }
//        }
    }
}