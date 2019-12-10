package com.cgy.mycollections.functions.mediamanager;

import android.content.Context;
import android.view.LayoutInflater;
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

    public ImagePagerAdapter(Context context) {
        this.mContext = context;
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

    @Override
    public int getItemCount() {
        return mImagePathList == null ? 0 : mImagePathList.size();
    }

    class ImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView imageView;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
