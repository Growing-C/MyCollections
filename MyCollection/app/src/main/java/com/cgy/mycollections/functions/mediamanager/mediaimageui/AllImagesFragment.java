package com.cgy.mycollections.functions.mediamanager.mediaimageui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import androidx.core.app.Fragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.appcompat.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.mediamanager.images.ThumbnailInfo;
import com.cgy.mycollections.listeners.OnTItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 用于显示所有图片，仅根据thumbnail来查询
 */
public class AllImagesFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    @BindView(R.id.image_list)
    RecyclerView mImageListV;

    MediaImageAdapter mImageAdapter;

    private PageViewModel pageViewModel;

    public static AllImagesFragment newInstance(int index) {
        AllImagesFragment fragment = new AllImagesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
//        L.e("AllImagesFragment onCreate");
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
//        L.e("AllImagesFragment onCreateView");
        View root = inflater.inflate(R.layout.fragment_media_images, container, false);
        ButterKnife.bind(this, root);

        final TextView textView = root.findViewById(R.id.section_label);
//        final RecyclerView imageListV = root.findViewById(R.id.image_list);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("显示所有图片" + s);
                textView.setVisibility(View.GONE);
            }
        });

        mImageAdapter = new MediaImageAdapter();

        mImageAdapter.setOnItemClickListener(new OnTItemClickListener<ThumbnailInfo>() {
            @Override
            public void onItemClickOne(int position, ThumbnailInfo data) {
                if (data != null) {

                }
            }

            @Override
            public void onItemClickTwo(int position, ThumbnailInfo data) {

            }
        });
//        mImageListV.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        mImageListV.setLayoutManager(new LinearLayoutManager(getContext()));
        mImageListV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mImageListV.setAdapter(mImageAdapter);

        pageViewModel.getThumbnails().observe(this, new Observer<List<ThumbnailInfo>>() {
            @Override
            public void onChanged(@Nullable List<ThumbnailInfo> thumbnailInfoList) {
                mImageAdapter.setData(thumbnailInfoList);
            }
        });
        return root;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }
}