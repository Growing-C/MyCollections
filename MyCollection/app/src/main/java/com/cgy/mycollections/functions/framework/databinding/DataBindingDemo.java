package com.cgy.mycollections.functions.framework.databinding;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cgy.mycollections.Demo;
import com.cgy.mycollections.R;
import com.cgy.mycollections.databinding.ActivityDataBindingDemoBinding;

public class DataBindingDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBindingDemoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding_demo);

        Demo demo = new Demo(R.string.title_activity_data_binding_demo, R.string.info_data_binding_demo, true, DataBindingDemo.class);
        binding.setDemo(demo);


    }
}
