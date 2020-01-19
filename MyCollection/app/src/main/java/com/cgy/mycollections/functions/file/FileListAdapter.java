package com.cgy.mycollections.functions.file;

//import androidx.appcompat.widget.RecyclerView;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.mediamanager.MediaHelper;
import com.cgy.mycollections.listeners.OnItemClickListener;
import com.cgy.mycollections.listeners.OnMyItemLongClickListener;
import com.cgy.mycollections.utils.image.ImageHelper;
import com.cgy.mycollections.utils.image.ImageLoader;

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
    private List<FileInfo> mSelectedFileList = new ArrayList<>();

    private boolean mIsSelect = false;
    private boolean mShowHide = false;

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
    }

    public FileInfo getItem(int position) {
        return mFileList.get(position);
    }

    public List<FileInfo> getSelectedFiles() {
        return mSelectedFileList;
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
                        mSelectedFileList.add(fileInfo);
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
            } else {
                if (info.getFile().isDirectory())
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
            if (info.getFile().isDirectory()) {
                fileImage.setImageResource(R.drawable.dir);
            } else if (ImageHelper.isPicIgnoreDot(info.getFilePath())) {//加载缩略图
                ImageLoader.loadImageThumbnail(rightArrow.getContext(), info.getFilePath(), fileImage);
            } else {
                fileImage.setImageResource(R.drawable.file);
            }

            String fileInfo = "";
            if (info.getFile().isDirectory()) {
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
