package com.cgy.mycollections.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import appframe.utils.L;

/**
 * 作者: 陈高阳
 * 创建日期: 2020/8/26 18:42
 * 修改日期: 2020/8/26 18:42
 * 类说明：圆角的linearLayout，用于将子view显示为圆角（因为webview的圆角有点问题）
 */
public class RoundLinearLayout extends LinearLayout {

    float width, height = 0;

    private int radiusInPx = 0;

    public RoundLinearLayout(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public RoundLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public RoundLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        super.onSizeChanged(w, h, ow, oh);
        width = w;
        height = h;
    }

    public void setRadiusInPx(int radiusInPx) {
        this.radiusInPx = radiusInPx;
        invalidate();
    }

    public void setRadiusInDp(int radiusInDp) {
        this.radiusInPx = dp2px(radiusInDp);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // linearLayout没有背景等相关绘制资源时 并不会调用 onDraw
        // 此时需要手动设置 setWillNotDraw(false); 来强制调用onDraw
        float y = 0;
        int r = radiusInPx;

        if (r > 0 && width > r && height > r) {
            Path path = getRoundRectPath(y, r);
            canvas.clipPath(path);//将路径闭合构成控件的区域
        }
        super.onDraw(canvas);
    }

    /**
     * 获取round的外边框path
     *
     * @param y
     * @param r
     * @return
     */
    private Path getRoundRectPath(float y, int r) {
        Path path = new Path();

        path.moveTo(r, 0);

        path.lineTo(width - r, 0);

        path.quadTo(width, 0, width, r);

        path.lineTo(width, y + height - r);//1,r改为0

        path.quadTo(width, y + height, width - r, y + height); //2,r改为0

        path.lineTo(r, y + height);//3,r改为0

        path.quadTo(0, height, 0, y + height - r); //4,r改为0  这四处r改为0即可实现上左上右为圆角，否则四角皆为圆角

        path.lineTo(0, r);

        path.quadTo(0, 0, r, 0);
        return path;
    }

    /** dp to px */
    protected int dp2px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
