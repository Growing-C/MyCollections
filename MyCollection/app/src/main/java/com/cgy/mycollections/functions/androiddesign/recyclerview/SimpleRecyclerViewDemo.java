package com.cgy.mycollections.functions.androiddesign.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cgy.mycollections.R;

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

        recyclerView.addItemDecoration(new SpaceItemDecoration(10));//设置item间隔

        SimpleRecyclerViewAdapter adapter = new SimpleRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
    }


    class SimpleRecyclerViewAdapter extends RecyclerView.Adapter<SimpleRecyclerViewAdapter.SimpleRecyclerViewHolder> {


        @Override
        public SimpleRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SimpleRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_recycler_view, parent, false));
        }

        @Override
        public void onBindViewHolder(SimpleRecyclerViewHolder holder, int position) {
            holder.setData("位置：" + position);
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
            }

            public void setData(String data) {
                mText.setText(data);
            }

        }
    }
}
