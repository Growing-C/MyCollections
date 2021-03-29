package com.cgy.mycollections.functions.mediamanager.mediaimageui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cgy.mycollections.R;
import com.cgy.mycollections.listeners.OnTItemClickListener;
import com.growingc.mediaoperator.beans.ThumbnailInfo;
import com.growingc.mediaoperator.utils.ImageLoader;

import java.util.List;

import appframe.utils.DisplayHelperUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

//import androidx.appcompat.widget.RecyclerView;

/**
 * Description :
 * Author :cgy
 * Date :2019/5/16
 */
public class MediaImageAdapter extends RecyclerView.Adapter<MediaImageAdapter.MediaImageHolder> {

    List<ThumbnailInfo> mThumbnailList;

    private OnTItemClickListener<ThumbnailInfo> mOnItemClickListener;

    int itemImageWidth = 0;
    int itemImageHeight = 0;

    public MediaImageAdapter() {
        itemImageWidth = DisplayHelperUtils.getScreenWidth() / 2 - 20;
        itemImageHeight = itemImageWidth;
    }

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

    public ThumbnailInfo getItem(int pos) {
        if (mThumbnailList != null && pos < mThumbnailList.size()) {
            return mThumbnailList.get(pos);
        }
        return null;
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

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageV.getLayoutParams();
            params.width = itemImageWidth;
            params.height = itemImageHeight;
            imageV.setLayoutParams(params);
            imageV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        int pos = getAdapterPosition();
                        mOnItemClickListener.onItemClickOne(pos, getItem(pos));
                    }
                }
            });
        }

        public void setData(ThumbnailInfo thumbnailInfo) {
//            pathV.setText(thumbnailInfo.data);
            ImageLoader.loadImageFitCenter(imageV.getContext(), thumbnailInfo.data, imageV);
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