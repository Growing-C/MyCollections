package com.cgy.mycollections.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cgy.mycollections.R;

/**
 * Created by RB-cgy on 2015/9/16.
 */
public class MSpinner extends RelativeLayout {
    private static final int ARROW_RIGHT_MARGIN = 20;
    private static final int TEXT_RIGHT_MARGIN = 50;

    private float mTextSize;
    private String mTextS;
    private int mTextColor;
    private int mDownListTextColor;
    private int mDownListBackground;
    private int mDownListDividerColor;

    private Context mContext;
    private TextView mText;
    private ImageView mDownArrow;
    private PopupWindow mPop;

    private boolean isPopShowing = false;
    private String[] mDownListItem;//下拉列表资源

    private int mPopHeight;//下拉列表高度

    private OnItemClickListener mOnItemClickListener;

    public MSpinner(Context context) {
        super(context);
        mContext = context;

        initView();
    }

    public MSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MSpinner);

        mTextS = a.getString(R.styleable.MSpinner_text);

        mTextSize = a.getDimensionPixelSize(R.styleable.MSpinner_textSize, 20);

        mTextColor = a.getColor(R.styleable.MSpinner_textColor, Color.BLACK);

        mDownListTextColor = a.getColor(R.styleable.MSpinner_downListTextColor, Color.BLACK);

        mDownListBackground = a.getColor(R.styleable.MSpinner_downListBackground, Color.WHITE);

        mDownListDividerColor = a.getColor(R.styleable.MSpinner_downListDividerColor, Color.BLACK);

        initView();

        a.recycle();

    }


    void initView() {
        //可获得焦点
        this.setFocusable(true);
//        this.setFocusableInTouchMode(true);

        mText = new TextView(mContext);
        mText.setGravity(Gravity.CENTER);
        mText.setTextColor(mTextColor);
        mText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mText.setText(mTextS);

        mDownArrow = new ImageView(mContext);
        mDownArrow.setImageResource(R.drawable.arrow_down);
        mDownArrow.setMaxHeight((int) mTextSize);
        mDownArrow.setAdjustViewBounds(true);

        this.addView(mText);
        this.addView(mDownArrow);

        MarginLayoutParams imgMp = new MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT);
        imgMp.setMargins(0, 0, ARROW_RIGHT_MARGIN, 0);
        LayoutParams imgParams = new LayoutParams(imgMp);
        imgParams.addRule(RelativeLayout.CENTER_VERTICAL);
        imgParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mDownArrow.setLayoutParams(imgParams);

        MarginLayoutParams txMp = new MarginLayoutParams(MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.MATCH_PARENT);
        txMp.setMargins(0, 0, TEXT_RIGHT_MARGIN, 0);
        LayoutParams txParams = new LayoutParams(txMp);
        mText.setLayoutParams(txParams);

        this.setOnClickListener(mClickListener);


    }

    private void showPop() {
        if (mPop == null) {
            //如果下拉列表没有数据则不显示
            if (mDownListItem == null || mDownListItem.length == 0) {
                return;
            }
            mPop = new PopupWindow(getPopView(), LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            mPop.setOutsideTouchable(true);
            mPop.setFocusable(true);
            mPop.setBackgroundDrawable(new BitmapDrawable());
            mPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    isPopShowing = false;
                    mDownArrow.setImageResource(R.drawable.arrow_down);
                }
            });
        }
        splitPopShowWay();

        isPopShowing = true;
        mDownArrow.setImageResource(R.drawable.arrow_up);
    }

    /**
     * 根据view到屏幕底部的距离和pop的高度决定pop显示的方式是下拉还是在上方
     */
    private void splitPopShowWay() {

        int[] location = new int[2];
        this.getLocationOnScreen(location);
        //LogUtils.e("pop x:" + location[0]);
        //LogUtils.e("pop y:" + location[1]);

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metric);

        // LogUtils.e("screen height:" + metric.heightPixels);
        //LogUtils.e("pop height:" + mPopHeight);
        //如果pop下拉空间不足则改为上方显示
        if (location[1] + getHeight() + mPopHeight <= metric.heightPixels) {
            mPop.showAsDropDown(this);
        } else {
            mPop.showAtLocation(this, Gravity.NO_GRAVITY, location[0], location[1] - mPopHeight);
        }

    }

    private View getPopView() {
        int width = this.getWidth();
        int height = this.getHeight();
        mPopHeight = 0;

        LinearLayout ll = new LinearLayout(mContext);
        ll.setBackgroundColor(mDownListBackground);
        ll.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(width, 1);

        LayoutParams txParams = new LayoutParams(width, height);

        int len = mDownListItem.length;
        for (int i = 0; i < len; i++) {
            if (i > 0) {
                View line = new View(mContext);
                line.setLayoutParams(lineParams);
                line.setBackgroundColor(mDownListDividerColor);
                ll.addView(line);
            }
            final TextView textView = new TextView(mContext);
            textView.setPadding(0, 0, TEXT_RIGHT_MARGIN, 0);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
            textView.setTextColor(mDownListTextColor);
            textView.setTag(i);

            //可获取焦点
            textView.setFocusable(true);
//            textView.setFocusableInTouchMode(true);
            textView.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        v.setBackgroundColor(getResources().getColor(R.color.focused_color));
                    } else {
                        v.setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            });

            textView.setLayoutParams(txParams);
            textView.setText(mDownListItem[i]);
            mPopHeight += height;//每添加一个item就把item高度添加到pop的总高度中

            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mOnItemClickListener != null) {
                        int position = (int) textView.getTag();
                        mOnItemClickListener.onItemClick(position, textView.getText().toString());
                        mPop.dismiss();
                    }
                }
            });
            ll.addView(textView);
        }
        return ll;
    }

    OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isPopShowing) {
                showPop();
            } else {
                mPop.dismiss();
            }
        }
    };

    public interface OnItemClickListener {
        void onItemClick(int position, String s);
    }

    public void setDownListItems(String[] args) {
        mDownListItem = args;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setText(String tx) {
        mText.setText(tx);
    }

    public String getText() {
        return mText.getText().toString();
    }

}
