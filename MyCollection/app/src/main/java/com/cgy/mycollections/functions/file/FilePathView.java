package com.cgy.mycollections.functions.file;

import android.content.Context;
import android.gesture.Prediction;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.cgy.mycollections.R;
import appframe.utils.L;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Description : 文件路径显示view
 * Author :cgy
 * Date :2019/12/4
 */
public class FilePathView extends HorizontalScrollView {
    private final String TAG = FilePathView.class.getSimpleName();
    LinearLayout mFilePathContainer;

    IPathSelectListener mListener;
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

    public void setPathSelectListener(IPathSelectListener mListener) {
        this.mListener = mListener;
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
            addPathName("手机存储");// 手机存储=storage/emulated/0
        }
    }

    public File getRootDir() {
        return mRootDir;
    }

    public File getCurrentDir() {
        return mCurrentDir;
    }

    /**
     * 增加一个层级的pathName
     *
     * @param pathName
     */
    private void addPathName(String pathName) {
        if (TextUtils.isEmpty(pathName))
            return;

        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        textView.setMaxWidth(250);
        textView.setSingleLine();
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setText(pathName);
        textView.setGravity(Gravity.CENTER);
        Drawable rightDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_navigate_next_black_24dp);
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, rightDrawable, null);
        mFilePathContainer.addView(textView, params);
    }

    /**
     * 显示到某个文件夹的具体路径
     *
     * @param selectedDir
     */
    public void navToFile(File selectedDir) {
        if (selectedDir != null && selectedDir.isDirectory()) {
            if (mCurrentDir.equals(selectedDir))
                return;

            if (mCurrentDir.getPath().contains(selectedDir.getPath())) {
                //当前路径已经包含了selectedDir 说明可能是上一级或者上几级，需要删除之后的view
                do {
                    navUpInternal();
                    L.e(TAG, "navToFile navUp");
                } while (!mCurrentDir.equals(selectedDir));
            } else if (selectedDir.getPath().contains(mCurrentDir.getPath())) {
                //选中的file 是mCurrentDir的子文件，需要增加path
                List<File> potentialDownFileList = new ArrayList<>();
                File pathFile = new File(selectedDir.getPath());
                do {
                    potentialDownFileList.add(pathFile);
                    pathFile = pathFile.getParentFile();
                } while (!mCurrentDir.equals(pathFile));
                for (int i = potentialDownFileList.size() - 1; i >= 0; i--) {
                    navDownInternal(potentialDownFileList.get(i));
                }
            } else {
                //这种情况只可能是不同的文件夹，不存在层级关系，此处必定不可以出现这种情况
                throw new IllegalArgumentException("navToFile file ：" + selectedDir.getAbsolutePath() + "  mCurrentDir:" + mCurrentDir.getPath());
            }
//            mCurrentDir = selectedDir;
            if (mListener != null) {
                mListener.onDirPathSelected(mCurrentDir);
            }
        }
    }

    public boolean navUp() {
        boolean navUpOk = navUpInternal();
        if (navUpOk && mListener != null) {
            mListener.onDirPathSelected(mCurrentDir);
        }
        return navUpOk;
    }

    /**
     * 下一级
     */
    private boolean navDownInternal(File selectedDir) {
        if (selectedDir != null && selectedDir.isDirectory()) {
            //selectedDir 必须是mCurrentDir的下一级
            if (!selectedDir.getParentFile().equals(mCurrentDir)) {
                throw new IllegalArgumentException("navDown file ：" + selectedDir.getAbsolutePath() + "  mCurrentDir:" + mCurrentDir.getPath());
            }
            L.e("navDown getAbsolutePath:" + selectedDir.getAbsolutePath());
            L.e("navDown getPath:" + selectedDir.getPath());
            L.e("navDown getName:" + selectedDir.getName());
//            navDown getAbsolutePath:/storage/emulated/0/baidu
//            navDown getPath:/storage/emulated/0/baidu
//            navDown getName:baidu
            mCurrentDir = selectedDir;

            addPathName(mCurrentDir.getName());
            return true;
        }
        return false;
    }

    /**
     * 上一级
     *
     * @return true-成功到上一级
     * false-无法到上一级
     */
    private boolean navUpInternal() {
        if (!mRootDir.equals(mCurrentDir)) {
            int childCount = mFilePathContainer.getChildCount();
            if (childCount > 1) {//0位置是root 不可以up
                mFilePathContainer.removeViewAt(childCount - 1);
                mCurrentDir = mCurrentDir.getParentFile();
                L.e("navUp  mCurrentDir:" + mCurrentDir.getPath());
                return true;
            }
        }
        return false;
    }

    public interface IPathSelectListener {
        /**
         * 文件夹路径被选中
         *
         * @param dirPathFile
         */
        void onDirPathSelected(File dirPathFile);
    }

}