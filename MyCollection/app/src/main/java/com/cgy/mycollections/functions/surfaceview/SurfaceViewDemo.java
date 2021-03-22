package com.cgy.mycollections.functions.surfaceview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cgy.mycollections.R;
import com.cgy.mycollections.base.AppBaseActivity;

public class SurfaceViewDemo extends AppBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_demo);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.game_demo) {
            startActivity(new Intent(this, GameDemo.class));
        }
    }


}
