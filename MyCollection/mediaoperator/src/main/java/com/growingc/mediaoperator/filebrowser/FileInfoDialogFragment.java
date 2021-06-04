package com.growingc.mediaoperator.filebrowser;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.growingc.mediaoperator.R;
import com.growingc.mediaoperator.beans.FileInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author :cgy
 * Date :2019/12/4
 */
public class FileInfoDialogFragment extends BaseDialogFragment {

    TextView mFileNameV;
    TextView mFilePathV;
    TextView mFileSizeV;
    TextView mFileModifyTimeV;

    private FileInfo mFileInfo;

    List<FileInfo> mFileList = new ArrayList<>();

    public static FileInfoDialogFragment newInstance(ArrayList<FileInfo> mFileList) {
        Bundle args = new Bundle();
        args.putSerializable("fileInfo", mFileList);

        FileInfoDialogFragment fragment = new FileInfoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutIds() {
        return R.layout.dialog_fileinfo;
    }

    @Override
    public void initView(View view) {
        mFileNameV = view.findViewById(R.id.file_name);
        mFilePathV = view.findViewById(R.id.file_path);
        mFileSizeV = view.findViewById(R.id.file_size);
        mFileModifyTimeV = view.findViewById(R.id.file_last_modify_time);
        view.findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Bundle bd = getArguments();
        if (bd != null) {
            mFileList = (List<FileInfo>) bd.getSerializable("fileInfo");
            if (mFileList != null && mFileList.size() > 0)
                mFileInfo = mFileList.get(0);
        }
        if (mFileInfo != null) {
            mFileNameV.setText("文件名:" + mFileInfo.getFileName());
            mFilePathV.setText("文件路径:\n" + mFileInfo.getFilePath());

            String fileSize = "文件类型和大小:\n";
            if (mFileInfo.getRealFile() != null) {
                if (mFileInfo.isDirectory()) {
                    fileSize += "文件夹,";
                    fileSize += mFileInfo.getDirChildCount(true) + "项  ";
                } else {
                    fileSize += "文件,";
                    fileSize += mFileInfo.getFileLengthWithUnit();
                }

                String modifyTime = "文件修改时间:\n" + mFileInfo.getLastModifyTime();
                String addProtectTime = mFileInfo.getAddProtectDate();

                if (!TextUtils.isEmpty(addProtectTime)) {
                    modifyTime += "\n\n文件添加保护时间：\n" + addProtectTime;
                }

                mFileSizeV.setText(fileSize);
                mFileModifyTimeV.setText(modifyTime);
            }
        }
    }

}
