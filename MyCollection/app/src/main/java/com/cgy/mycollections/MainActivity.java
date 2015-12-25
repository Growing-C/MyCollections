package com.cgy.mycollections;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cgy.mycollections.functions.threadpool.ThreadPoolDemo;
import com.cgy.mycollections.functions.weixindemo.RedEnvelopeDemo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout mContainer = (LinearLayout) findViewById(R.id.main_item_container);
        int height = getResources().getDisplayMetrics().heightPixels;

        //屏幕上加载10个item
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height / 10);

        for (int i = 0, len = demos.length; i < len; i++) {
            Demo demo = demos[i];
            View v = View.inflate(MainActivity.this, R.layout.item_demos, null);
            ((TextView) v.findViewById(R.id.tx_title)).setText(demo.titleId);
            ((TextView) v.findViewById(R.id.tx_info)).setText(demo.infoId);

            v.setId(i);
            v.setOnClickListener(mClickListener);

            mContainer.addView(v, params);
        }
    }

    private Demo[] demos = {
            new Demo(R.string.title_activity_thread_pool_demo, R.string.info_thread_pool_demo, ThreadPoolDemo.class),
            new Demo(R.string.title_activity_red_envelope_demo, R.string.info_red_envelope_demo, RedEnvelopeDemo.class)
    };

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id < 0 || id >= demos.length)
                return;
            startActivity(new Intent(MainActivity.this, demos[id].c));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
