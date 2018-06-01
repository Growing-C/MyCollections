package appframe.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.witon.jining.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义toast
 * Created by RB-cgy on 2017/12/15.
 */

public class ToastCustom {
    Toast mToast;

    TextView mToastText;

    private static final List<WeakReference<Toast>> sToastList = new ArrayList<>();

    public ToastCustom(Context context, CharSequence text, int length) {
        mToast = Toast.makeText(context, text, length);
        mToast.setGravity(Gravity.CENTER, 0, 0);

        View view = mToast.getView();
        if (view != null) {
            mToastText = ((TextView) view.findViewById(android.R.id.message));
            mToastText.setTypeface(Typeface.DEFAULT);//设置字体（似乎没啥用）
            mToastText.setShadowLayer(0F, 11F, 5F, Color.TRANSPARENT);//字体阴影会导致模糊 radius设为0就不会模糊了

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                mToastText.setTextAppearance(R.style.ToastTxAppearanceCommon);
            else {
                mToastText.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.px33_tx_size));
            }

            setTextColor(ContextCompat.getColor(mToastText.getContext(), R.color.tx_color_666666));
            mToastText.setGravity(Gravity.CENTER);
            setBackGround(R.drawable.toast_frame);//xml里有阴影的实现>>toast_frame   另外tt也可以用，只是有的手机左右会挤
        }
    }

    private void setBackGround(int backgroundIds) {
        View view = mToast.getView();
        if (view != null) {
            view.setBackgroundResource(backgroundIds);
        }
    }

    private void setTextColor(int messageColor) {
        if (mToastText != null) {
            mToastText.setTextColor(messageColor);
        }
    }

    public void show() {
        for (WeakReference<Toast> toastWeakReference : sToastList) {//先清空所有其他toast（可以不用list 直接用lastToast引用也可以）
            if (toastWeakReference != null && toastWeakReference.get() != null) {
                toastWeakReference.get().cancel();
            }
        }
        sToastList.clear();

        mToast.show();
        sToastList.add(new WeakReference<>(mToast));
    }

}
