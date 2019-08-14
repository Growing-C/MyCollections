package com.cgy.mycollections.functions.ui.textdemo.fonttext;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by yanghailang on 2017/11/2.
 */
public class IconFontTextView extends AppCompatTextView {
    Context mContext;

    public IconFontTextView(Context context) {
        super(context);
        init(context);
    }

    public IconFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    /**
     * 初始化iconfont
     *
     * @param context
     */
    private void init(Context context) {
        mContext = context;
        //加载字体文件
        Typeface typeface = IconFontTypeFace.getTypeface(context);
        if (typeface != null) {
            this.setTypeface(typeface);
            //去掉padding,这样iconfont和普通字体容易对齐
            setIncludeFontPadding(false);
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        init(mContext);
    }

    public static class IconFontTypeFace {
        //用static,整个app共用整个typeface就够了
        private static Typeface ttfTypeface = null;

        public static synchronized Typeface getTypeface(Context context) {
            if (ttfTypeface == null) {
                try {
                    if (context == null)
                        return null;
                    ttfTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/iconfont.ttf");
                } catch (Exception e) {
                    Log.e("IconFontTextView","cannot find iconfont!");
                }
            }
            return ttfTypeface;
        }

        public static synchronized void clearTypeface() {
            ttfTypeface = null;
        }
    }
}

