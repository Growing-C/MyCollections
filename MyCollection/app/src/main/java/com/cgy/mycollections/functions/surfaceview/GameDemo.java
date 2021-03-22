package com.cgy.mycollections.functions.surfaceview;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.cgy.mycollections.R;
import com.cgy.mycollections.base.AppBaseActivity;

import appframe.utils.L;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description :
 * Author :cgy
 * Date :2020/10/26
 */
public class GameDemo extends AppBaseActivity {
    @BindView(R.id.game_surface)
    GameSurface mGameSurface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_demo);
        ButterKnife.bind(this);

        mGameSurface.setRudderListener(new GameSurface.RudderListener() {
            @Override
            public void onSteeringWheelChanged(int action, int angle) {
                L.e("onSteeringWheelChanged action:" + action + " action:" + angle);
            }
        });
    }
}
