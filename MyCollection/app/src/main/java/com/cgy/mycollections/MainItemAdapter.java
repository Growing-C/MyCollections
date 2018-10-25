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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by YOLANDA on 2016/7/22.
 */
public class MainItemAdapter extends RecyclerView.Adapter<MainItemAdapter.DefaultViewHolder> {
    private Demo[] demos;

    private OnItemClickListener mOnItemClickListener;

    public MainItemAdapter(Demo[] demos) {
        this.demos = demos;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return demos == null ? 0 : demos.length;
    }

    @Override
    public MainItemAdapter.DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false));
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainItemAdapter.DefaultViewHolder holder, int position) {
        holder.setData(demos[position]);
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDescription;
        ImageView hasChildArrow;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_des);
            hasChildArrow = itemView.findViewById(R.id.has_child_arrow);
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
            hasChildArrow.setVisibility(demo.hasChild ? View.VISIBLE : View.GONE);
        }
    }

}
