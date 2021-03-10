package com.growingc.mediaoperator;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import appframe.utils.L;

/**
 * @author 余洋
 * @Date: 2019-11-28
 * @Description:限速居中顯示文字(textview的drawabletop drawablebounds的中心點與canvas寬度的中心點一致)
 */
public class RestrictSpeedTextDrawable extends Drawable {
    private final TextPaint mTextPaint;
    private CharSequence mShowText;

    /**
     * @param textSize 文字大小
     * @param color    文字颜色
     */
    public RestrictSpeedTextDrawable(int textSize, @ColorInt int color, Typeface typeface) {
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTypeface(typeface);
        mTextPaint.setColor(color);

    }

    public void setText(CharSequence charSequence) {
        mShowText = charSequence;
        invalidateSelf();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
//        view的canvas 寬度居中 高度居中是bound的居中減去文字高度一半（drawblegravity center bounds与canvas宽度中心一致）
        if (TextUtils.isEmpty(mShowText)) {
            return;
        }

        //先设图片 再设这个
//        2021-03-04 17:16:36.443 11715-11715/com.cgy.mycollections I/MLlinkstec: canvas width:378 getHeight:198
//        2021-03-04 17:16:36.444 11715-11715/com.cgy.mycollections I/MLlinkstec: RestrictSpeedTextDrawable draw :Rect(112, 22 - 266, 176) l t r b

        //直接设这个
//        2021-03-04 17:19:13.498 12006-12006/com.cgy.mycollections I/MLlinkstec: canvas width:378 getHeight:198
//        2021-03-04 17:19:13.498 12006-12006/com.cgy.mycollections I/MLlinkstec: RestrictSpeedTextDrawable draw :Rect(90, 0 - 288, 198)

        L.i("canvas width:" + canvas.getWidth() + " getHeight:" + canvas.getHeight());
        L.i("RestrictSpeedTextDrawable draw :" + getBounds());
//        layer height:198  width:378
        Rect bounds = getBounds();// x为文字绘制起始点，与 mTextPaint.setTextAlign(Paint.Align.CENTER); 配合可以使文字以x为中心点
        canvas.drawText(mShowText, 0, mShowText.length(), (bounds.right + bounds.left) / 2f, (bounds.bottom + bounds.top - mTextPaint.getFontMetrics().descent - mTextPaint.getFontMetrics().ascent) / 2, mTextPaint);
        //因为 layerDrawable child 是居中的 所以child 的bound  left+right=layerDrawable.getWidth top+bottom=layerDrawable.getHeight,所以找layerDrawable的中心点必须加起来 而不是减
        //不能使用canvas 的width height 来计算x，y。使用canvas 有个问题，如果是ImageView没啥问题，是TextView 此时图片也会偏移，因为canvas是整个textview的canvas

    }

    @Override
    public void setAlpha(int alpha) {
        mTextPaint.setAlpha(alpha);
        invalidateSelf();
    }


    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mTextPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
