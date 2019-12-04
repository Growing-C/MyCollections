package com.cgy.mycollections.functions.file;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cgy.mycollections.R;
import com.cgy.mycollections.base.BaseDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description :
 * Author :cgy
 * Date :2019/12/4
 */
public class FileInfoDialogFragment extends BaseDialogFragment {

    @BindView(R.id.file_name)
    TextView mFileNameV;
    @BindView(R.id.file_path)
    TextView mFilePathV;
    @BindView(R.id.file_size)
    TextView mFileSizeV;
    @BindView(R.id.file_last_modify_time)
    TextView mFileModifyTimeV;

    private FileInfo mFileInfo;

    public static FileInfoDialogFragment newInstance(FileInfo fileInfo) {
        Bundle args = new Bundle();
        args.putSerializable("fileInfo", fileInfo);

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
        ButterKnife.bind(this, view);

        Bundle bd = getArguments();
        if (bd != null) {
            mFileInfo = (FileInfo) bd.getSerializable("fileInfo");
        }
        if (mFileInfo != null) {
            mFileNameV.setText("文件名:" + mFileInfo.fileName);
            mFilePathV.setText("文件路径:\n" + mFileInfo.filePath);

            String fileSize = "文件类型和大小:\n";
            if (mFileInfo.file != null) {
                if (mFileInfo.file.isDirectory()) {
                    fileSize += "文件夹,";
                    fileSize += mFileInfo.getDirChildCount(true) + "项  ";
                } else {
                    fileSize += "文件,";
                    fileSize += mFileInfo.file.length() + "B  ";
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

    @OnClick({R.id.close_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_btn:
                dismiss();
                break;
            default:
                break;
        }
    }
}
