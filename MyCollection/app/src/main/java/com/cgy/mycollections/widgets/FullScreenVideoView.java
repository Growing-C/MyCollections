package com.cgy.mycollections.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

import com.cgy.mycollections.utils.L;


/**
 * Description : video view 在放logo_anim时有黑边，用这个去除黑边
 * Author :cgy
 * Date :2019/3/11
 */
public class FullScreenVideoView extends VideoView {
    public FullScreenVideoView(Context context) {
        super(context);
    }

    public FullScreenVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullScreenVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //此处设置的默认值可随意,因为getDefaultSize中的size是有值的
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
        L.e("test", "======onMeasure===width===" + width);
        L.e("test", "======onMeasure===height===" + height);
    }
}
