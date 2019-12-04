package com.cgy.mycollections.functions.file;

import android.content.Context;
import android.os.Environment;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cgy.mycollections.R;

import java.io.File;

/**
 * Description : 文件路径显示view
 * Author :cgy
 * Date :2019/12/4
 */
public class FilePathView extends HorizontalScrollView {
    LinearLayout mFilePathContainer;

    File mRootDir;
    File mCurrentDir;

    public FilePathView(Context context) {
        this(context, null);
    }

    public FilePathView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilePathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mFilePathContainer = new LinearLayout(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        addView(mFilePathContainer, params);

        initRootFile();
    }

    /**
     * 初始化最小的文件
     */
    public void initRootFile() {
        mFilePathContainer.removeAllViews();

        mRootDir = Environment.getExternalStorageDirectory();
        mCurrentDir = mRootDir;

//        int pathDepth = mFilePathContainer.getChildCount();

        if (mRootDir != null && mRootDir.isDirectory()) {
            addPathName(mRootDir.getPath());
        }
    }

    /**
     * 增加一个层级的pathName
     *
     * @param pathName
     */
    private void addPathName(String pathName) {
        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        textView.setText(pathName);
        mFilePathContainer.addView(textView, params);
    }


    public void navToFile(File selectedDir) {

    }

    /**
     * 下一级
     */
    public void navDown(File selectedDir) {
        if (selectedDir != null && selectedDir.isDirectory()) {

        }
    }

    /**
     * 上一级
     */
    public void navUp() {
        if (!mRootDir.equals(mCurrentDir)) {
            mCurrentDir = mCurrentDir.getParentFile();
            if (mCurrentDir.isDirectory()) {


            }
        }
    }


}
