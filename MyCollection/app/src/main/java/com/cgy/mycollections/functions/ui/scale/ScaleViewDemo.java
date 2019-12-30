package com.cgy.mycollections.functions.ui.scale;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cgy.mycollections.Demo;
import com.cgy.mycollections.MainItemAdapter;
import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.ui.androiddesign.recyclerview.SimpleRecyclerViewDemo;
import com.cgy.mycollections.functions.ui.dialogdemo.DialogDemo;
import com.cgy.mycollections.functions.ui.scale.ruler.ScrollRulerLayout;
import com.cgy.mycollections.functions.ui.scale.scaleview.HorizontalScaleView;
import com.cgy.mycollections.functions.ui.scale.scaleview.OnValueChangeListener;
import com.cgy.mycollections.functions.ui.scale.scaleview.VerticalScaleView;
import com.cgy.mycollections.functions.ui.systemui.statusbar.StatusBarDemo;
import com.cgy.mycollections.functions.ui.textdemo.TextDemo;
import com.cgy.mycollections.functions.ui.wheel.WheelDemo;
import com.cgy.mycollections.listeners.OnItemClickListener;

import appframe.utils.L;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ScaleViewDemo extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.ruler_view)
    ScrollRulerLayout rulerView;

    @BindView(R.id.vertical_scale)
    VerticalScaleView verticalScaleView;
    @BindView(R.id.horizontal_scale)
    HorizontalScaleView horizontalScaleView;
    @BindView(R.id.verical_horizontal_switch)
    ToggleButton toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_view);
        ButterKnife.bind(this);//butterKnife和dataBinding并不冲突

        setSupportActionBar(toolbar);

        rulerView.setScope(5000, 15001, 500);
        rulerView.setCurrentItem("10000");


        verticalScaleView.setRange(100, 200);
        horizontalScaleView.setRange(40, 100);

        verticalScaleView.setOnValueChangeListener(new OnValueChangeListener() {
            @Override
            public void onValueChanged(float value) {
                L.e("verticalScaleView onValueChanged:" + value);
            }
        });
        horizontalScaleView.setOnValueChangeListener(new OnValueChangeListener() {
            @Override
            public void onValueChanged(float value) {
                L.e("horizontalScaleView onValueChanged:" + value);
            }
        });
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    verticalScaleView.setVisibility(View.VISIBLE);
                    horizontalScaleView.setVisibility(View.GONE);
                } else {
                    verticalScaleView.setVisibility(View.GONE);
                    horizontalScaleView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
