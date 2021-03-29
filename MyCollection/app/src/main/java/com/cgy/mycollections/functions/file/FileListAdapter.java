package com.cgy.mycollections.functions.file;

//import androidx.appcompat.widget.RecyclerView;

import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cgy.mycollections.R;
import com.cgy.mycollections.listeners.OnMyItemLongClickListener;
import com.growingc.mediaoperator.beans.FileInfo;
import com.growingc.mediaoperator.utils.FileUtil;
import com.growingc.mediaoperator.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description :
 * Author :cgy
 * Date :2019/7/25
 */
public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.FileHolder> {
    private OnMyItemLongClickListener<FileInfo> mOnItemClickListener;
    private List<FileInfo> mFileList;
    private ArrayList<FileInfo> mSelectedFileList = new ArrayList<>();

    private boolean mIsSelect = false;//是否是选择文件
    private boolean mShowHide = false;//是否显示隐藏文件

    public FileListAdapter() {
    }

    public FileListAdapter(boolean showHide) {
        this.mShowHide = showHide;
    }

    public void setShowHideFiles(boolean showHide) {
        this.mShowHide = showHide;
        notifyDataSetChanged();
    }

    public void setData(List<FileInfo> list) {
        this.mFileList = list;
        notifyDataSetChanged();
    }

    public List<FileInfo> getData() {
        return mFileList;
    }

    public void setIsSelect(boolean isSelect) {
        this.mIsSelect = isSelect;
        if (!isSelect)
            clearSelectedFiles();
        notifyDataSetChanged();
    }

    public void select(FileInfo info) {
        if (mFileList.contains(info))
            addSelect(info);
        notifyDataSetChanged();
    }

    public boolean isSelectMode() {
        return mIsSelect;
    }

    public FileInfo getItem(int position) {
        return mFileList.get(position);
    }

    public ArrayList<FileInfo> getSelectedFiles() {
        return mSelectedFileList;
    }

    public void clearSelectedFiles() {
        mSelectedFileList.clear();
    }

    public void setOnItemClickListener(OnMyItemLongClickListener<FileInfo> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public FileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FileHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false));
    }

    @Override
    public void onBindViewHolder(FileHolder holder, int position) {
        holder.setData(mFileList.get(position));
    }

    @Override
    public int getItemCount() {
        return mFileList == null ? 0 : mFileList.size();
    }

    private void addSelect(FileInfo info) {
        if (!mSelectedFileList.contains(info))
            mSelectedFileList.add(info);
    }

    class FileHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.file_name)
        TextView fileName;
        @BindView(R.id.file_info)
        TextView fileInfoV;
        @BindView(R.id.right_arrow)
        View rightArrow;
        @BindView(R.id.select)
        CheckBox selectBox;

        @BindView(R.id.file_image)
        ImageView fileImage;
        @BindView(R.id.file_protected)
        View protectedV;

        public FileHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    FileInfo fileInfo = getItem(position);
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(position, fileInfo);
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    FileInfo fileInfo = getItem(position);
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemLongClick(position, fileInfo);
                    }
                    return true;
                }
            });
            selectBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = getAdapterPosition();
                    FileInfo fileInfo = getItem(position);
                    if (isChecked) {
                        addSelect(fileInfo);
                    } else {
                        mSelectedFileList.remove(fileInfo);
                    }
                }
            });
        }

        public void setData(FileInfo info) {

            if (mIsSelect) {
                rightArrow.setVisibility(View.INVISIBLE);
                selectBox.setVisibility(View.VISIBLE);
                if (mSelectedFileList.contains(info)) {
                    selectBox.setChecked(true);
                } else {
                    selectBox.setChecked(false);
                }
            } else {
                if (info.isDirectory())
                    rightArrow.setVisibility(View.VISIBLE);
                else {
                    rightArrow.setVisibility(View.INVISIBLE);
                }
                selectBox.setVisibility(View.INVISIBLE);
            }

            if (info.protectState == FileConstants.STATE_PROTECTED) {
                protectedV.setVisibility(View.VISIBLE);
            } else {
                protectedV.setVisibility(View.GONE);
            }

            fileName.setText(info.getShowFileNameWithoutHideFilter());
            String fileType = FileUtil.getFileType(info.getRealFile());
            if (TextUtils.isEmpty(fileType))
                fileImage.setImageResource(R.drawable.file);
            else if (TextUtils.equals(FileUtil.FILE_TYPE_DIR, fileType)) {
                fileImage.setImageResource(R.drawable.dir);
            } else if (TextUtils.equals(fileType, FileUtil.FILE_TYPE_IMAGE)) {
                //加载缩略图
                //TODO:隐藏后的路径也能加载出图片，待研究是不是glide缓存
                ImageLoader.loadImageThumbnail(rightArrow.getContext(), info.getFilePath(), fileImage);
            } else if (TextUtils.equals(fileType, FileUtil.FILE_TYPE_APK)) {
                //加载apk icon
                ImageLoader.loadApkIcon(rightArrow.getContext(), info.getRealFile().getPath(), fileImage, R.drawable.file);
            } else {
                fileImage.setImageResource(R.drawable.file);
            }
//            if (info.isDirectory()) {
//            } else if (ImageHelper.isPicIgnoreDot(info.getFilePath())) {//加载缩略图
//                ImageLoader.loadImageThumbnail(rightArrow.getContext(), info.getFilePath(), fileImage);
//            } else {
//
//            }

            String fileInfo = "";
            if (info.isDirectory()) {
                fileInfo += info.getDirChildCount(mShowHide) + "项  ";
            } else {
                fileInfo += info.getFileLengthWithUnit();
            }
            fileInfo += "  " + info.getLastModifyTime();
//
            fileInfoV.setText(fileInfo);
//            mText.setText(data);
//            if (colorRes != -1) {
//                mText.setTextColor(ContextCompat.getColor(mText.getContext(), colorRes));
//            } else {
//                mText.setTextColor(Color.WHITE);
//            }
        }
    }
}
