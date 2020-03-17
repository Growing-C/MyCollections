package com.cgy.mycollections.functions.ui.scale;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cgy.mycollections.R;
import com.cgy.mycollections.widgets.ruler.ScrollRulerLayout;
import com.cgy.mycollections.widgets.scaleview.HorizontalScaleView;
import com.cgy.mycollections.widgets.scaleview.OnValueChangeListener;
import com.cgy.mycollections.widgets.scaleview.VerticalScaleView;

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
        horizontalScaleView.setRangeAndScale(40, 100, 0.5f)//设置范围 和一个刻度的大小
                .setPointerValue(43, 50)//设置左右指针位置
                .setSmallScaleSpaceCountInBig(2)
                .setDrawTextSmallScaleIndex(6)
                .setScaleMovable(false)
                .setOnValueChangeListener(new OnValueChangeListener() {
                    @Override
                    public void onValueChanged(float leftValue, float rightValue) {
                        L.e("horizontalScaleView onValueChanged:" + leftValue + "  " + rightValue);
                    }
                })
                .setAvailableFilter(new HorizontalScaleView.IAvailableFilter() {
                    @Override
                    public boolean valueAvailable(float value) {
                        if (value > 50 && value < 57)
                            return false;
                        return true;
                    }
                });

        verticalScaleView.setOnValueChangeListener(new OnValueChangeListener() {
            @Override
            public void onValueChanged(float leftValue, float rightValue) {
                L.e("verticalScaleView onValueChanged:" + leftValue);
            }
        });
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                horizontalScaleView.setRange(10, 200).reset();


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
