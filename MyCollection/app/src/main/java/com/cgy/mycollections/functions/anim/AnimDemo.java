package com.cgy.mycollections.functions.anim;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import com.cgy.mycollections.R;
import appframe.utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 该页面展示了动画效果
 * 包含多种动画
 * 1.airbnb/lottie-android 库动画
 */
public class AnimDemo extends AppCompatActivity {

    @BindView(R.id.video_view)
    VideoView mLogoAnimV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_demo);
        ButterKnife.bind(this);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lottie:
                startActivity(new Intent(AnimDemo.this, LottieAnimActivity.class));
                break;
            case R.id.android_anim:
                startActivity(new Intent(AnimDemo.this, AndroidAnimActivity.class));
                break;
            case R.id.start_video://动画
                playLogoAnim();
                break;
            default:
                break;
        }
    }


    public void playLogoAnim() {
        try {
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + R.raw.logo_anim);
            mLogoAnimV.setVideoURI(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mLogoAnimV.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
//                L.e("test", "onPrepared 111111111111  wid:"+   mLogoAnimV.getWidth());
//                L.e("test", "onPrepared 111111111111  hei:"+   mLogoAnimV.getHeight());

                //用于去除视频播放前的黑屏，搭配background使用
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                            mLogoAnimV.setBackgroundColor(Color.TRANSPARENT);
                        return true;
                    }
                });
                mLogoAnimV.start();
            }
        });
        mLogoAnimV.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
//                mLogoAnimV.start();
//                mLogoAnimV.setVisibility(View.GONE);
//                L.e("test", "onCompletion");
                Toast.makeText(AnimDemo.this, "播放结束", Toast.LENGTH_SHORT).show();
            }
        });
        mLogoAnimV.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                L.e("test", "onError");
                stopPlaybackVideo();
                return true;
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        if (!mLogoAnimV.isPlaying()) {
            mLogoAnimV.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mLogoAnimV.isPlaying() && mLogoAnimV.canPause())
            mLogoAnimV.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPlaybackVideo();
    }

    private void stopPlaybackVideo() {
        try {
            mLogoAnimV.stopPlayback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
