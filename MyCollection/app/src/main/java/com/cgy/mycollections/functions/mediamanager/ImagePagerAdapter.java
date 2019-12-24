package com.cgy.mycollections.functions.mediamanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.mediamanager.images.ImageInfo;
import com.cgy.mycollections.listeners.OnItemClickListener;
import com.cgy.mycollections.listeners.OnMyItemClickListener;
import com.cgy.mycollections.listeners.OnTItemClickListener;
import com.cgy.mycollections.utils.L;
import com.cgy.mycollections.utils.image.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description :
 * Author :cgy
 * Date :2019/12/10
 */
public class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.ImageHolder> {
    private List<File> mImagePathList;
    private Context mContext;
    OnMyItemClickListener<File> mListener;

    public ImagePagerAdapter(Context context, OnMyItemClickListener<File> listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    public void setData(List<File> list) {
        if (mImagePathList == null)
            mImagePathList = new ArrayList<>();

        mImagePathList.clear();
        if (list != null && !list.isEmpty()) {
            mImagePathList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImagePagerAdapter.ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageHolder(LayoutInflater.from(mContext).inflate(R.layout.item_image_pager, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImagePagerAdapter.ImageHolder holder, int position) {
        holder.setData(mImagePathList.get(position));
    }

    public File getItem(int pos) {
        return mImagePathList.get(pos);
    }

    @Override
    public int getItemCount() {
        return mImagePathList == null ? 0 : mImagePathList.size();
    }

    class ImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView imageView;

        @SuppressLint("ClickableViewAccessibility")
        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition();
//                    if (mListener != null)
//                        mListener.onItemClick(pos, getItem(pos));
//                }
//            });
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (mListener != null)
                        mListener.onItemClick(pos, getItem(pos));
                }
            });
//            imageView.setOnTouchListener((v, event) -> {
//                boolean result = true;
//                //can scroll horizontally checks if there's still a part of the image
//                //that can be scrolled until you reach the edge
//                if (event.getPointerCount() >= 2 || v.canScrollHorizontally(1) && v.canScrollHorizontally(-1)) {
//                    //multi-touch event
//                    switch (event.getAction()) {
//                        case MotionEvent.ACTION_DOWN:
//                        case MotionEvent.ACTION_MOVE:
//                            // Disallow RecyclerView to intercept touch events.
//                            itemView.getParent().requestDisallowInterceptTouchEvent(true);
//                            // Disable touch on view
//                            result = false;
//                            break;
//                        case    MotionEvent.ACTION_UP:
//                            // Allow RecyclerView to intercept touch events.
//                            itemView.getParent().requestDisallowInterceptTouchEvent(false);
//                            result = true;
//                            break;
//                        default:
//                            break;
//                    }
//                }
//                return result;
//            });
        }

        public void setData(File imageFile) {
            ImageLoader.loadImageFitCenter(mContext, imageFile.getPath(), imageView);
        }
    }

//    @Override
//    public int getItemPosition(Object object) {
//        // 1.最简单解决 notifyDataSetChanged() 页面不刷新问题的方法,不适合数据量大的界面，会重载所有界面
////       2.还可以 在 instantiateItem() 方法中给每个 View 添加 tag（使用 setTag() 方法），然后在 getItemPosition() 方法中通过 View.getTag() 来判断是否是需要刷新的页面，是就返回 POSITION_NONE，否就返回 POSITION_UNCHANGED。
//// （参考自：ViewPager刷新单个页面的方法）这里有一点要注意的是，当清空数据源的时候需要返回 POSITION_NONE
//        return POSITION_NONE;
//    }
}
