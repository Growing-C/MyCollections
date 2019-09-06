package com.cgy.mycollections.functions.ui.androiddesign.recyclerview;

import android.content.Intent;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
//import androidx.appcompat.widget.LinearLayoutManager;
//import androidx.appcompat.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cgy.mycollections.listeners.OnItemClickListener;
import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.ui.androiddesign.recyclerview.contact.ContactListActivity;
import com.cgy.mycollections.widgets.itemdecorations.SpaceItemDecoration;

public class SimpleRecyclerViewDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_recycler_view_demo);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.simple_recycler);
        //切换不同布局使用不同的layoutManager即可
        recyclerView.setLayoutManager(new LinearLayoutManager(SimpleRecyclerViewDemo.this));//设置线性布局，默认是vertical
//        recyclerView.setLayoutManager(new LinearLayoutManager(SimpleRecyclerViewDemo.this, LinearLayoutManager.HORIZONTAL, false));//设置水平布局的线性布局
//        recyclerView.setLayoutManager(new GridLayoutManager(SimpleRecyclerViewDemo.this, 3, GridLayoutManager.VERTICAL, false));//设置网格布局

        recyclerView.addItemDecoration(new SpaceItemDecoration(3));//设置item间隔

        SimpleRecyclerViewAdapter adapter = new SimpleRecyclerViewAdapter();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0) {
                    startActivity(new Intent(SimpleRecyclerViewDemo.this, ContactListActivity.class));
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }


    class SimpleRecyclerViewAdapter extends RecyclerView.Adapter<SimpleRecyclerViewAdapter.SimpleRecyclerViewHolder> {


        private OnItemClickListener mOnItemClickListener;

        @Override
        public SimpleRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SimpleRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_recycler_view, parent, false));
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        @Override
        public void onBindViewHolder(SimpleRecyclerViewHolder holder, int position) {
            if (position == 0) {//通讯录
                holder.setData("通讯录 ",R.color.colorAccent);
            } else if (position == 1) {
                holder.setData("位置：" + position,-1);
            } else
                holder.setData("位置：" + position,-1);
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        class SimpleRecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView mText;

            public SimpleRecyclerViewHolder(View itemView) {
                super(itemView);
                mText = (TextView) itemView.findViewById(R.id.text);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onItemClick(getAdapterPosition());
                        }
                    }
                });
            }

            public void setData(String data, int colorRes) {
                mText.setText(data);
                if (colorRes != -1) {
                    mText.setTextColor(ContextCompat.getColor(mText.getContext(), colorRes));
                } else {
                    mText.setTextColor(Color.WHITE);
                }
            }
        }
    }
}
