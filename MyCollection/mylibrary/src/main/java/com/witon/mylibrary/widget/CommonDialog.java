package com.witon.mylibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.witon.mylibrary.R;

import appframe.utils.DisplayHelperUtils;


/**
 * 通用的提示对话框
 * <p>使用方式
 * CommonDialog dialog = new Builder(context)
 * .setTitle("标题")
 * .setMessage("测试用消息")
 * .setSubMessage("测试用副标题")
 * .setPositiveButton("确定", new OnClickListener() {
 * public void onClick(DialogInterface dialog, int which) {
 * System.out.println("aaaaaaaaaaaaa");
 * }})
 * .setNegativeButton("取消", null)
 * .create();
 * dialog.show();
 * Created by RB-cgy on 2017/1/11.
 */
public class CommonDialog extends Dialog {
    float mDialogWidth = -1;

    public CommonDialog(Context context) {
        super(context);
    }

    public CommonDialog(Context context, int theme) {
        super(context, theme);
    }

    //TODO:该dialog的使用参照下面的方式
    public static void testShow(Context context) {
        CommonDialog a = new CommonDialog.Builder(context)
                .setTitle("标题")//设置标题
                .setMessage("测试用消息")//设置消息
                .setSubMessage("测试用副消息")//副消息，可选
                .setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("Positive button onClick");
                    }
                })
                .setNegativeButton("取消", null)//可选
                .create();

        a.show();
    }

    public void setWidth(float width) {
        mDialogWidth = width;
    }

    @Override
    public void show() {
        super.show();
        //只有在此处设置宽度  才不会有限制
        if (mDialogWidth != -1) {
            Window win = this.getWindow();
            win.getDecorView().setPadding(0, 0, 0, 0);//dialog 默认的样式@android:style/Theme.Dialog 对应的style 有pading属性，所以就能够水平占满了
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = (int) mDialogWidth; //设置宽度
            win.setAttributes(lp);
        }
    }

    /**
     * Helper class for creating a custom dialog
     */
    public static class Builder {

        private Context context;
        private CharSequence title;
        private String message;
        private String subMessage;
        private String positiveButtonText;
        private String negativeButtonText;

        private int messageTextAlignment = -1;//消息的文字位置
        private int titleTextAlignment = -1;//标题的文字位置
        private float widthWeight = -1;//宽度的屏幕比例

        private OnClickListener positiveButtonClickListener, negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setWidthWeight(float weight) {
            if (weight > 0 && weight <= 1)//weight要在0到1之间
                this.widthWeight = weight;

            return this;
        }

        /**
         * Set the Dialog message from String
         *
         * @return
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog sub message from String
         *
         * @return
         */
        public Builder setSubMessage(String message) {
            this.subMessage = message;
            return this;
        }

        /**
         * Set the Dialog sub message from resource
         *
         * @return
         */
        public Builder setSubMessage(int message) {
            this.subMessage = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */
        public Builder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder setMessageTextAlignment(int messageTextAlignment) {
            this.messageTextAlignment = messageTextAlignment;
            return this;
        }

        public Builder setTitleTextAlignment(int titleTextAlignment) {
            this.titleTextAlignment = titleTextAlignment;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the positive button text and it's listener
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button resource and it's listener
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button text and it's listener
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * Create the custom dialog
         */
        public CommonDialog create() {
            // instantiate the dialog with the custom Theme
            final CommonDialog dialog = new CommonDialog(context, R.style.CommonDialogTheme);
            dialog.setCanceledOnTouchOutside(false);
            View layout = View.inflate(context, R.layout.dialog_common, null);
            LinearLayout dialogRoot = (LinearLayout) layout.findViewById(R.id.dialog_root_ll);
            if (widthWeight != -1) {//手动设置宽度
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) dialogRoot.getLayoutParams();
                float screenWidth = context.getResources().getDisplayMetrics().widthPixels;//屏幕宽度
                params.width = (int) (screenWidth * widthWeight);
//                System.out.println(" params.width:" + params.width);
                dialog.setWidth(params.width);
                dialogRoot.setLayoutParams(params);
            }
            //upper part of the dialog , we will change it's height over different content
            LinearLayout upperPart = (LinearLayout) layout.findViewById(R.id.dialog_upper_part);
            // set the dialog title
            TextView titleView = (TextView) layout.findViewById(R.id.dialog_title);//titleView
            TextView messageView = (TextView) layout.findViewById(R.id.dialog_message);//messageView
            TextView subMessageView = (TextView) layout.findViewById(R.id.dialog_sub_message);//subMessageView
            titleView.setText(title);
            if (titleTextAlignment != -1) {
                titleView.setGravity(titleTextAlignment);
            }
            // set the confirm button
            Button positiveButton = (Button) layout.findViewById(R.id.btn_positive);
            Button negativeButton = (Button) layout.findViewById(R.id.btn_negative);
            if (positiveButtonText != null) {
                positiveButton.setText(positiveButtonText);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (positiveButtonClickListener != null) {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                        dialog.dismiss();
                    }
                });
            } else {
                // if no confirm button just set the visibility to GONE
                positiveButton.setVisibility(View.GONE);
                layout.findViewById(R.id.btn_divider).setVisibility(View.GONE);
            }

            // set the cancel button
            if (negativeButtonText != null) {
                negativeButton.setText(negativeButtonText);
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (negativeButtonClickListener != null) {
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                        dialog.dismiss();
                    }
                });
            } else {
                // if no confirm button just set the visibility to GONE
                negativeButton.setVisibility(View.GONE);
                layout.findViewById(R.id.btn_divider).setVisibility(View.GONE);
                positiveButton.setBackgroundResource(R.drawable.selector_dialog_btn_bottom);//底部双圆角
            }

            // set the content message
            if (message != null) {
                messageView.setVisibility(View.VISIBLE);
                messageView.setText(message);
                if (messageTextAlignment != -1) {
                    messageView.setGravity(messageTextAlignment);
                }
                //set the sub message, be sure you have set message before setting subMessage
                if (subMessage != null) {
                    subMessageView.setText(subMessage);
                    //if there is a sub message ,we should set the height of the upper part bigger
                    FrameLayout.LayoutParams upperParams = (FrameLayout.LayoutParams) upperPart.getLayoutParams();
                    upperParams.height = context.getResources().getDimensionPixelSize(R.dimen.px240_size);
                    upperPart.setLayoutParams(upperParams);
                }
            } else {
                messageView.setVisibility(View.GONE);
                //if there is no message ,change title top margin according to design
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) titleView.getLayoutParams();
                int bottomMargin = context.getResources().getDimensionPixelSize(R.dimen.px30_common);
                int topMargin = context.getResources().getDimensionPixelSize(R.dimen.px40_margin);
                params.setMargins(0, topMargin, 0, bottomMargin);
                titleView.setLayoutParams(params);
            }

            //设定最大高度
            final ScrollView upperScroll = (ScrollView) layout.findViewById(R.id.upper_scroll);
            ViewTreeObserver vto = upperScroll.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    upperScroll.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    int screenHeight = DisplayHelperUtils.getScreenHeight();
                    int upperHeight = upperScroll.getHeight();

                    int maxHeight = screenHeight * 3 / 4;
                    if (upperHeight > maxHeight) {//防止内容太长导致dialog高度超过屏幕高度
                        LinearLayout.LayoutParams upperParams = (LinearLayout.LayoutParams) upperScroll.getLayoutParams();
                        upperParams.height = maxHeight;
                        upperScroll.setLayoutParams(upperParams);
                    }
                }
            });

            dialog.setContentView(layout);
            return dialog;
        }

    }
}
