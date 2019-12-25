/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cgy.mycollections;

//import androidx.appcompat.widget.RecyclerView;

import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cgy.mycollections.listeners.OnItemClickListener;
import com.cgy.mycollections.listeners.swipedrag.ItemTouchHelperAdapter;
import com.cgy.mycollections.listeners.swipedrag.ItemTouchHelperViewHolder;
import appframe.utils.L;
import com.cgy.mycollections.utils.Typefaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by YOLANDA on 2016/7/22.
 */
public class MainItemAdapter extends RecyclerView.Adapter<MainItemAdapter.DefaultViewHolder> implements ItemTouchHelperAdapter {
    //    private Demo[] demos;
    private List<Demo> demos;

    private OnItemClickListener mOnItemClickListener;

    private final OnStartDragListener dragStartListener;

    public MainItemAdapter(Demo[] demos, OnStartDragListener dragStartListener) {
        this.demos = new ArrayList<>();
        this.dragStartListener = dragStartListener;
        if (demos != null)
            Collections.addAll(this.demos, demos);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return demos == null ? 0 : demos.size();
    }

    @Override
    public MainItemAdapter.DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false));
//        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainItemAdapter.DefaultViewHolder holder, int position) {
        holder.setData(demos.get(position));
        holder.dragView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() ==
                        MotionEvent.ACTION_DOWN) {
                    if (dragStartListener != null)
                        dragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    //-----------------滑动删除和拖拽-----------------------------------------------
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        L.e("test", "onItemMove from:" + fromPosition + "  to:" + toPosition);

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(demos, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(demos, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        L.e("test", "onItemDismiss position  :" + position);

        //右滑删除 的删除操作在此处进行，如果不操作会一直显示优化删除的图片
        // 通过notifyItemChanged可以恢复recyclerView的状态到原始状态
//        notifyItemChanged(position);
        notifyItemRemoved(position);
        demos.remove(position);
        notifyItemRangeChanged(0, getItemCount());
    }
    //----------------------------------------------------------------

    class DefaultViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        TextView tvTitle;
        TextView tvDescription;
        View dragView;
        ImageView hasChildArrow;
//        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);

            dragView = itemView.findViewById(R.id.drag_view);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_des);
            hasChildArrow = itemView.findViewById(R.id.has_child_arrow);

            tvTitle.setTypeface(Typefaces.getRobotoBlack(tvTitle.getContext()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }

        public void setData(Demo demo) {
            tvTitle.setText(demo.titleId);
            tvDescription.setText(demo.infoId);
            hasChildArrow.setVisibility(demo.hasChild ? View.VISIBLE : View.INVISIBLE);
        }

        //-----------------滑动删除和拖拽-----------------------------------------------
        @Override
        public void onItemSelected(Context context) {
//            container.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
//            tvItemName.setTextColor(ContextCompat.getColor(context, R.color.white));
//            ivReorder.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);
        }

        @Override
        public void onItemClear(Context context) {
//            container.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
//            ivReorder.setColorFilter(ContextCompat.getColor(context, R.color.textlight), PorterDuff.Mode.SRC_IN);
//            tvItemName.setTextColor(ContextCompat.getColor(context, R.color.textlight));
        }
        //-----------------滑动删除和拖拽-----------------------------------------------
    }

    public interface OnStartDragListener {

        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }
}
