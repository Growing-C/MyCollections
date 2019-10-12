package com.cgy.mycollections.functions.ui.wheel.rvgallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.recyclerview.widget.RecyclerView;

import com.cgy.mycollections.R;
import com.cgy.mycollections.utils.L;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Random;

/**
 * @author RyanLee
 * @date 2017/12/7
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder> {
    private final String TAG="RecyclerAdapter";
    private Context mContext;
    private List<Integer> mDatas;

    private OnItemPhotoChangedListener mOnItemPhotoChangedListener;


    RecyclerAdapter(Context mContext, List<Integer> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        L.d( TAG, "RecyclerAdapter onAttachedToRecyclerView");
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        L.d( TAG, "RecyclerAdapter onCreateViewHolder" + " width = " + parent.getWidth());
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_gallery, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        L.d( TAG, "RecyclerAdapter onBindViewHolder" + "--> position = " + position);
        holder.mView.setImageResource(mDatas.get(holder.getAdapterPosition()));
        holder.mChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int randomNum = new Random().nextInt(9);
                int[] res = {R.drawable.photo_nba1, R.drawable.photo_nba2, R.drawable.photo_nba3, R.drawable.photo_nba4,
                        R.drawable.photo_nba5, R.drawable.photo_nba6, R.drawable.photo_nba7, R.drawable.photo_nba8, R.drawable.photo_nba9};
                mDatas.set(holder.getAdapterPosition(), res[randomNum]);
                notifyItemChanged(holder.getAdapterPosition(), this.getClass().getName());
                if (mOnItemPhotoChangedListener != null) {
                    mOnItemPhotoChangedListener.onItemPhotoChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        final ImageView mView;
        FloatingActionButton mChange;

        MyHolder(View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.iv_photo);
            mChange = itemView.findViewById(R.id.fab_change);
        }
    }

    /**
     * 获取position位置的resId
     *
     * @param position int
     * @return int
     */
    public int getResId(int position) {
        return mDatas == null ? 0 : mDatas.get(position);
    }

    public void setOnItemPhotoChangedListener(OnItemPhotoChangedListener mOnItemPhotoChangedListener) {
        this.mOnItemPhotoChangedListener = mOnItemPhotoChangedListener;
    }

    public interface OnItemPhotoChangedListener {
        /**
         * 局部更新后需要替换背景图片
         */
        void onItemPhotoChanged();
    }
}
