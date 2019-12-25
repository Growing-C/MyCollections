package com.cgy.mycollections.functions.ui.wheel.rvgallery;


import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.ui.wheel.rvgallery.lib.AnimManager;
import com.cgy.mycollections.functions.ui.wheel.rvgallery.lib.GalleryRecyclerView;
import com.cgy.mycollections.functions.ui.wheel.rvgallery.lib.OnItemSelectedListener;
import appframe.utils.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GalleryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GalleryFragment extends Fragment {
    public static final String TAG = "MainActivity_TAG";

    @BindView(R.id.rv_list)
    GalleryRecyclerView mRecyclerView;
    @BindView(R.id.rl_container)
    RelativeLayout mContainer;
    @BindView(R.id.seekBar)
    SeekBar mSeekbar;

    private Map<String, Drawable> mTSDraCacheMap = new HashMap<>();
    private static final String KEY_PRE_DRAW = "key_pre_draw";

    /**
     * 获取虚化背景的位置
     */
    private int mLastDraPosition = -1;

    private boolean shouldScrollBySeekBar = false;

    public GalleryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GalleryFragment.
     */
    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.e("onActivityCreated!");

        final RecyclerAdapter adapter = new RecyclerAdapter(getContext(), getDatas());
        adapter.setOnItemPhotoChangedListener(new RecyclerAdapter.OnItemPhotoChangedListener() {
            @Override
            public void onItemPhotoChanged() {
                setBlurImage(true);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView
                // 设置滑动速度（像素/s）
                .initFlingSpeed(9000)
                // 设置页边距和左右图片的可见宽度，单位dp
                .initPageParams(0, 40)
                // 设置切换动画的参数因子
                .setAnimFactor(0.1f)
                // 设置切换动画类型，目前有AnimManager.ANIM_BOTTOM_TO_TOP和目前有AnimManager.ANIM_TOP_TO_BOTTOM
                .setAnimType(AnimManager.ANIM_BOTTOM_TO_TOP)
                // 设置点击事件
                .setOnItemClickListener(new GalleryRecyclerView.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getContext(), "position=" + position, Toast.LENGTH_SHORT).show();
                        L.d(TAG, "onItemClick position():" + position);
                        // 点击有bug ，偶现 点击的item有时候贴边不居中,往右边点击4次再往左边点击4次 第7个position稳定复现(已通过GalleryItemDecoration itemView setMargin改成outRect.left解决)
                        mRecyclerView.selectItem(position);
                    }
                })
                .setItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {
                        L.d(TAG, "onItemSelected:" + index);

                        setBlurImage(false);
                        //所有日期选择最后都会走这里（点击选择和滑动选择）
                        //选中位置的viewHolder，此处用于设置当前选中的view 和其字体颜色
//                RecyclerView.ViewHolder selectedHolder = mDateListV.findViewHolderForAdapterPosition(index);
                    }
                }).autoPlay(false)// 设置自动播放
                // 设置自动播放间隔时间 ms
                .intervalTime(2000)
                // 设置初始化的位置
                .initPosition(0)
                .setVisibleItemCount(1)
                // 在设置完成之后，必须调用setUp()方法
                .setUp();


        // 背景高斯模糊 & 淡入淡出
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    setBlurImage(false);

                    if (!shouldScrollBySeekBar)
                        mSeekbar.setProgress(mRecyclerView.getScrolledPosition());
                }
            }
        });
        setBlurImage(false);

        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (shouldScrollBySeekBar)
                    mRecyclerView.smoothScrollToPosition(progress);
//
                L.d(TAG, "onProgressChanged progress():" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                DLog.d(TAG, "onProgressChanged progress():onStartTrackingTouch");
                shouldScrollBySeekBar = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                shouldScrollBySeekBar = false;
//                DLog.d(TAG, "onStopTrackingTouch progress :" + seekBar.getProgress());
//                mRecyclerView.smoothScrollToPosition(seekBar.getProgress());
            }
        });
    }

    /**
     * 设置背景高斯模糊
     */
    public void setBlurImage(boolean forceUpdate) {
        RecyclerAdapter adapter = (RecyclerAdapter) mRecyclerView.getAdapter();
        final int mCurViewPosition = mRecyclerView.getScrolledPosition();

        boolean isSamePosAndNotUpdate = (mCurViewPosition == mLastDraPosition) && !forceUpdate;

        if (adapter == null || mRecyclerView == null || isSamePosAndNotUpdate || adapter.getItemCount() == 0) {
            return;
        }
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                //如果是Fragment的话，需要判断Fragment是否Attach当前Activity，否则getResource会报错
                /*if (!isAdded()) {
                    // fix fragment not attached to Activity
                    return;
                }*/
                // 获取当前位置的图片资源ID
                int resourceId = ((RecyclerAdapter) mRecyclerView.getAdapter()).getResId(mCurViewPosition);
                // 将该资源图片转为Bitmap
                Bitmap resBmp = BitmapFactory.decodeResource(getResources(), resourceId);
                // 将该Bitmap高斯模糊后返回到resBlurBmp
                Bitmap resBlurBmp = BlurBitmapUtil.blurBitmap(mRecyclerView.getContext(), resBmp, 15f);
                // 再将resBlurBmp转为Drawable
                Drawable resBlurDrawable = new BitmapDrawable(resBlurBmp);
                // 获取前一页的Drawable
                Drawable preBlurDrawable = mTSDraCacheMap.get(KEY_PRE_DRAW) == null ? resBlurDrawable : mTSDraCacheMap.get(KEY_PRE_DRAW);

                /* 以下为淡入淡出效果 */
                Drawable[] drawableArr = {preBlurDrawable, resBlurDrawable};
                TransitionDrawable transitionDrawable = new TransitionDrawable(drawableArr);
                mContainer.setBackground(transitionDrawable);
                transitionDrawable.startTransition(500);

                // 存入到cache中
                mTSDraCacheMap.put(KEY_PRE_DRAW, resBlurDrawable);
                // 记录上一次高斯模糊的位置
                mLastDraPosition = mCurViewPosition;
            }
        });
    }


    /***
     * 测试数据
     * @return List<Integer>
     */
    public List<Integer> getDatas() {
        List<Integer> tDatas = new ArrayList<>();
        try {
            //图片array必须这样获取，且图片必须放在可用的drawable文件夹中，自定义的drawable-xx获取不到
            TypedArray ar = getResources().obtainTypedArray(R.array.test_arr);
            final int[] resIds = new int[ar.length()];
            for (int i = 0; i < ar.length(); i++) {
                resIds[i] = ar.getResourceId(i, 0);
            }
            ar.recycle();
            for (int resId : resIds) {
                tDatas.add(resId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        final int[] resIds = getResources().getIntArray(R.array.test_arr);
        return tDatas;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRecyclerView != null) {
            // 释放资源
            mRecyclerView.release();
        }
    }
}
