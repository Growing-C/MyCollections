package com.cgy.mycollections.functions.file;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cgy.mycollections.R;
import com.cgy.mycollections.listeners.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description :
 * Author :cgy
 * Date :2019/7/25
 */
public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.FileHolder> {
    private OnItemClickListener mOnItemClickListener;
    private List<FileInfo> mFileList;

    public FileListAdapter() {
    }

    public void setData(List<FileInfo> list) {
        this.mFileList = list;
        notifyDataSetChanged();
    }

    public FileInfo getItem(int position) {
        return mFileList.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
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
        TextView fileInfo;

        @BindView(R.id.file_image)
        ImageView fileImage;

        public FileHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }

        public void setData(FileInfo info) {
            fileName.setText(info.fileName);
            if (info.file.isDirectory()) {
                fileImage.setImageResource(R.drawable.dir);
            } else {
                fileImage.setImageResource(R.drawable.file);
            }
            fileInfo.setText(info.getInfo());
//            mText.setText(data);
//            if (colorRes != -1) {
//                mText.setTextColor(ContextCompat.getColor(mText.getContext(), colorRes));
//            } else {
//                mText.setTextColor(Color.WHITE);
//            }
        }
    }
}
