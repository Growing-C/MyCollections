package appframe.utils;

import android.net.Uri;
import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 头像拍照和本地图片管理类
 * Created by RB-cgy on 2017/5/27.
 */
public class PhotoManager {

    /**
     * 生成保存拍照照片的路径
     *
     * @return
     */
    public static Uri createSaveCameraPhotoUri() {
        String timeStamp = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        // 照片命名
        String cropFileName = "osc_camera_" + timeStamp + ".jpg";
        // 拍照保存的绝对路径
        String photoFilePath = FileUtils.getCameraPhotoPath() + "/" + cropFileName;
        System.out.println("getCameraPhotoUri:" + photoFilePath);
        File portraitFile = new File(photoFilePath);
        Uri cameraPhotoUri = Uri.fromFile(portraitFile);

        return cameraPhotoUri;
    }

    /**
     * 创建裁剪的图片保存的路径
     *
     * @param logonName
     * @return
     */
    public static Uri createCropOutputUri(String logonName) {
        String headerDirPath;
        if (FileUtils.isSdcardExits()) {
            headerDirPath = FileUtils.getUserHeaderDirPath(logonName);
            File saveDir = new File(headerDirPath);
            if (!saveDir.exists())
                saveDir.mkdirs();
        } else {
            //无法保存上传的头像，请检查SD卡是否挂载
            return null;
        }

        String croppedHeaderPath = headerDirPath + "/header.png";
        System.out.println("croppedHeaderPath:" + croppedHeaderPath);
        // 照片命名
//        // 裁剪头像的绝对路径
        File portraitFile = new File(croppedHeaderPath);
        if (portraitFile.exists())//删除之前可能存在的头像
            portraitFile.delete();
        return Uri.fromFile(portraitFile);

    }


    /**
     * 把图片转换为base64格式
     *
     * @param path
     * @return
     */
    public static String parsePhoto2Base64(String path) {
        File file = new File(path);
        if (file == null)
            return "";

        String resultString = "";
        try {
            InputStream inputStream = new FileInputStream(file);
            byte[] photoByte = IOUtils.toByteArray(inputStream);
            if (photoByte != null) {

                resultString = Base64.encodeToString(photoByte, Base64.DEFAULT);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }
}
