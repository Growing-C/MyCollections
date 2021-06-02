package com.growingc.mediaoperator.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;

import com.facebook.common.file.FileUtils;
import com.facebook.common.internal.Preconditions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import appframe.utils.CommonUtils;
import appframe.utils.L;

/**
 * 文件操作工具 可以创建和删除文件等
 */
public class FileUtil {
    public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值

    //文件类型,
    public static final String FILE_TYPE_DIR = "dir";//mineType为null
    public static final String FILE_TYPE_FILE = "file";//用于暂时不认识的文件类型
    public static final String FILE_TYPE_IMAGE = "image";//mineType=image/jpeg
    public static final String FILE_TYPE_AUDIO = "audio";//mineType=audio/mpeg
    public static final String FILE_TYPE_VIDEO = "video";//mineType=video/mp4
    public static final String FILE_TYPE_APK = "apk";//mineType=application/vnd.android.package-archive
    public static final String FILE_TYPE_ZIP = "zip";//mineType=

    public static final HashMap<String, String> sFileTypes = new HashMap<String, String>();

    private FileUtil() {
    }

    //--------------------新增-------------------------------

    /**
     * 指定硬盘缓存的目录，有sd卡优先sd卡，没有sd卡就使用fileSystem中的
     *
     * @param context
     * @param uniqueName
     * @return
     */
    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if ((Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable())
                && context.getExternalCacheDir() != null) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    public static void handlerGetFile(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            if (file.exists()) {
                listFile(file);
                listFile(file.getParentFile());
            } else {
                L.e("文件不存在：" + filePath);
            }
        }
    }

    /**
     * 返回文件夹下面的所有图片文件（jpg,png,gif）
     *
     * @param file
     * @return
     */
    public static List<File> listImageFile(File file) {
        return listImageFile(file, false);
    }

    public static List<File> listImageFile(File file, boolean ignoreDot) {
        Preconditions.checkNotNull(file);

        List<File> fileList = new ArrayList<>();
        if (file.isDirectory()) {
            L.e("listFile，这是文件夹，路径为：" + file.getPath());
            File[] files = file.listFiles();
            for (int i = 0, len = files.length; i < len; i++) {
//                L.e("路径" + files[i].getPath());
                String filePath = files[i].getPath();
                if (ignoreDot) {
                    if (ImageHelper.isPicIgnoreDot(filePath))
                        fileList.add(files[i]);
                } else if (ImageHelper.isPic(filePath)) {
                    fileList.add(files[i]);
                }
            }
        } else {
            L.e("listFile，这是文件，路径为：" + file.getPath());
        }
        return fileList;
    }

    /**
     * 列出文件夹中的所有文件
     *
     * @param file
     */
    public static void listFile(File file) {
        Preconditions.checkNotNull(file);

        if (file.isDirectory()) {
            L.e("listFile，这是文件夹，路径为：" + file.getPath());
            File[] files = file.listFiles();
            if (files == null) {
                L.e("listFile，该文件夹子文件为空：" + file.getPath());
                return;
            }
            for (File value : files) {
                L.e("路径" + value.getPath());
            }
        } else {
            L.e("listFile，这是文件，路径为：" + file.getPath());
        }
    }


    //<editor-fold desc="获取文件类型">

    /**
     * 获取系统返回的文件类型
     *
     * @param file
     * @return
     */
    public static String getRawFileType(@NonNull File file) {
        if (file.isDirectory())
            return null;
        String mimeType = getMimeType(file.getPath());
        if (TextUtils.isEmpty(mimeType)) {
            //有mimeType就好办了
            //mineType有以下几种 image/jpeg , audio/mpeg , video/mp4 , application/vnd.android.package-archive
            return mimeType;
        } else {//没有mimeType此时表示可能无法识别,需要读取文件头来识别
            L.e("fileUtil", "--no mineType!!!!!!!!!!!" + file.getName());
            String rawFileType = getFileTypeFromHeader(file.getPath());
            if (!TextUtils.isEmpty(rawFileType)) {
                L.e("fileUtil", "raw fileType:" + rawFileType + " --path:" + file.getPath());
                return rawFileType;
            }
        }
        return null;
    }

    /**
     * 获取文件类型，此方法会返回一个大的类型
     * 反回的类型是自定义的，如果要系统的类型使用
     * {@link #getRawFileType(File)}
     *
     * @param file 此处的file需要是realFile
     * @return
     */
    public static String getFileType(@NonNull File file) {
        if (file.isDirectory())
            return FILE_TYPE_DIR;
        String mimeType = getMimeType(file.getPath());
        if (!TextUtils.isEmpty(mimeType)) {
            //有mimeType就好办了
            //mineType有以下几种 image/jpeg , audio/mpeg , video/mp4 , application/vnd.android.package-archive
            return convertFileTypeFromTypeString(mimeType);
        } else {//没有mimeType此时表示可能无法识别,需要读取文件头来识别
            L.e("fileUtil", "--no mineType!!!!!!!!!!!" + file.getName());
            String rawFileType = getFileTypeFromHeader(file.getPath());
            if (!TextUtils.isEmpty(rawFileType)) {
                L.e("fileUtil", "raw fileType:" + rawFileType + " --path:" + file.getPath());
                return convertFileTypeFromTypeString(rawFileType);
            }
        }
        return FILE_TYPE_FILE;
    }

    /**
     * 从文件类型string转换成我们的常量type
     * 如  image/jpeg  --》  image
     *
     * @param typeString
     * @return
     */
    private static String convertFileTypeFromTypeString(String typeString) {
        if (!TextUtils.isEmpty(typeString)) {
            //有mimeType就好办了
            //mineType有以下几种 image/jpeg , audio/mpeg , video/mp4 , application/vnd.android.package-archive
            if (typeString.contains(FILE_TYPE_IMAGE)) {
                return FILE_TYPE_IMAGE;
            } else if (typeString.contains(FILE_TYPE_VIDEO))
                return FILE_TYPE_VIDEO;
            else if (typeString.contains("package-archive"))
                return FILE_TYPE_APK;
            else if (typeString.contains(FILE_TYPE_AUDIO))
                return FILE_TYPE_AUDIO;
        }
        return FILE_TYPE_FILE;
    }

    /**
     * 从文件名后缀获取文件类型
     *
     * @param filePath
     * @return
     */
    private static String getMimeType(String filePath) {
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        //有的文件可能被隐藏了，此处获取隐藏前真实的名字 来决定mineType
        String realFilePath = FileUtil.getFileOriginName(filePath);
        try {
            String ext = realFilePath.substring(realFilePath.lastIndexOf(".") + 1);
            String type = mime.getMimeTypeFromExtension(ext);
            L.e("fileUtil", "getMimeType:" + type + "---" + filePath);
            return type;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过读取文件头获取文件类型
     *
     * @param filePath
     * @return
     */
    public static synchronized String getFileTypeFromHeader(String filePath) {
        if (sFileTypes.size() == 0) {
            //images
            sFileTypes.put("FFD8FF", "image/jpg");
            sFileTypes.put("89504E47", "image/png");
            sFileTypes.put("47494638", "image/gif");
            sFileTypes.put("49492A00", "image/tif");
            sFileTypes.put("424D", "image/bmp");
            sFileTypes.put("41433130", "dwg"); //CAD
            sFileTypes.put("38425053", "psd");
            sFileTypes.put("7B5C727466", "rtf"); //日记本
            sFileTypes.put("3C3F786D6C", "xml");
            sFileTypes.put("68746D6C3E", "html");
            sFileTypes.put("44656C69766572792D646174653A", "eml"); //邮件
            sFileTypes.put("D0CF11E0", "doc");
            sFileTypes.put("5374616E64617264204A", "mdb");
            sFileTypes.put("252150532D41646F6265", "ps");
            sFileTypes.put("255044462D312E", "pdf");
            sFileTypes.put("504B0304", "zip/zip");
            sFileTypes.put("52617221", "zip/rar");
            sFileTypes.put("57415645", "video/wav");
            sFileTypes.put("41564920", "video/avi");
            sFileTypes.put("2E524D46", "video/rm");
            sFileTypes.put("000001BA", "video/mpg");
            sFileTypes.put("000001B3", "video/mpg");
            sFileTypes.put("6D6F6F76", "video/mov");
            sFileTypes.put("3026B2758E66CF11", "asf");
            sFileTypes.put("4D546864", "mid");
            sFileTypes.put("1F8B08", "gz");
        }
        return sFileTypes.get(getFileHeader(filePath));
    }

    /**
     * 通过读取文件头获取文件类型
     *
     * @param filePath
     * @return
     */
    private static String getFileHeader(String filePath) {
        FileInputStream is = null;
        String value = null;
        try {
            is = new FileInputStream(filePath);
            byte[] b = new byte[3];
            is.read(b, 0, b.length);
            value = bytesToHexString(b);
        } catch (Exception e) {
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return value;
    }

    private static String bytesToHexString(byte[] src) {
        if (src == null || src.length <= 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        String hv;
        for (int i = 0; i < src.length; i++) {
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }
    //</editor-fold>

    //<editor-fold desc="文件权限相关">

    /**
     * 设置文件权限
     *
     * @param f 文件
     */
    public static void setFileWith755Permission(File f) {
        if (null == f) {
            return;
        }
        L.i("setFileWith755Permission-- " + f.getAbsolutePath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Path path = Paths.get(f.getAbsolutePath());
            try {
                Set<PosixFilePermission> perms = Files.readAttributes(path, PosixFileAttributes.class).permissions();
                perms.add(PosixFilePermission.OWNER_WRITE);
                perms.add(PosixFilePermission.OWNER_READ);
                perms.add(PosixFilePermission.OWNER_EXECUTE);
                perms.add(PosixFilePermission.GROUP_READ);
                perms.add(PosixFilePermission.GROUP_EXECUTE);
                perms.add(PosixFilePermission.OTHERS_READ);
                perms.add(PosixFilePermission.OTHERS_EXECUTE);
                Files.setPosixFilePermissions(path, perms);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件操作权限相关
     *
     * @param f 文件
     * @return 权限描述
     */
    public static String getFilePermission(File f) {
        if (null == f) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        L.i("getFilePermission-- " + f.getAbsolutePath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Path path = Paths.get(f.getAbsolutePath());
            try {
                Set<PosixFilePermission> perms = Files.readAttributes(path, PosixFileAttributes.class).permissions();
                for (PosixFilePermission item : perms) {
                    sb.append(item.name()).append(",");
                }
                if (sb.length() > 0) {
                    sb.setLength(sb.length() - 1);
                }
                L.i("getFilePermission-- " + f.getAbsolutePath() + ",permission:" + sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    //</editor-fold>

    //<editor-fold desc="文件隐藏相关">

    /**
     * 文件是否是已经保护性隐藏的文件
     *
     * @param file
     * @return
     */
    public static boolean isFileHidden(File file) {
        if (file != null) {
            String fileName = file.getName();
            if (!TextUtils.isEmpty(fileName) && fileName.contains(MediaHelper.REPLACE_DOT)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据文件状态（隐藏或者显示）获取相反状态的文件,并不保证一定存在
     *
     * @param rawFile
     * @return
     */
    public static File getOriginOrHiddenFileByFileState(File rawFile) {
        if (isFileHidden(rawFile)) {
            return getOriginFile(rawFile);
        }
        return getHiddenFile(rawFile);
    }

    /**
     * 获取文件的隐藏文件。
     * 如果已经是隐藏的 会多一个点
     *
     * @param rawFile
     * @return
     */
    public static File getHiddenFile(File rawFile) {
        File hiddenFile = rawFile;
        if (rawFile != null) {
            hiddenFile = new File(rawFile.getParentFile().getPath() + "/." + rawFile.getName().replace(".", MediaHelper.REPLACE_DOT));
        }
        return hiddenFile;
    }

    /**
     * 获取隐藏文件的原始文件，
     * 如果是隐藏文件一定可以获取到原始文件
     * 如果不是隐藏文件，返回的还是原始文件
     *
     * @param hiddenFile
     * @return
     */
    public static File getOriginFile(File hiddenFile) {

        File originFile = hiddenFile;
        if (hiddenFile != null) {
            String fileName = hiddenFile.getName();
            if (fileName.startsWith(".") && fileName.length() > 1)
                originFile = new File(hiddenFile.getParentFile().getPath() + "/" + fileName.substring(1).replace(MediaHelper.REPLACE_DOT, "."));
        }
        return originFile;
    }

    /**
     * 获取文件原来的名称，用于使用 REPLACE_DOT 修改了文件名隐藏的文件 显示原来的名称
     *
     * @param currentName
     * @return
     */
    public static String getFileOriginName(String currentName) {
        if (TextUtils.isEmpty(currentName))
            return currentName;

        return currentName.replace(MediaHelper.REPLACE_DOT, ".");
    }

    /**
     * 将单个文件或者文件夹由可见改成隐藏
     *
     * @param context
     * @param file
     * @return
     */
    public static boolean hideSingleFile(@NonNull Context context, File file) {
        Preconditions.checkNotNull(file);

        String[] pathList = new String[1];
        try {
            if (!file.getName().startsWith(".")) {
                File targetFile = new File(file.getParentFile().getPath() + "/." + file.getName().replace(".", MediaHelper.REPLACE_DOT));
                L.e("hideSingleFile", file.getPath() + "-->to:" + targetFile.getPath());
//                        20190619_070221.gif
                if (!file.exists()) {
                    if (targetFile.exists()) {
                        L.e("hideSingleFile", "源文件不存在，隐藏文件存在，说明已隐藏,跳过：" + file.getPath());
                    } else {
                        return false;//都不存在说明这个文件没了
                    }
                } else {
                    FileUtils.rename(file, targetFile);
                }
                pathList[0] = file.getPath();
            } else {
                L.e("hideSingleFile", "已隐藏,跳过：" + file.getPath());
            }

            //直接全部扫描，用于把系统记录的图片记录全部干掉
            MediaScannerConnection.scanFile(context, pathList,
                    null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            L.e("hideSingleFile", "onScanCompleted path:" + path + "    ---uri:" + uri);

                        }
                    });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将单个文件或者文件夹 由隐藏改成可见
     *
     * @param context
     * @param file
     * @return
     */
    public static boolean showSingleFile(@NonNull Context context, File file) {
        Preconditions.checkNotNull(file);

        String fileName = file.getName();
        if (fileName.startsWith(".") && fileName.length() > 1) {
            File targetFile = new File(file.getParentFile().getPath() + "/" + fileName.substring(1).replace(MediaHelper.REPLACE_DOT, "."));
            L.e("showSingleFile", file.getPath() + "-->to:" + targetFile.getPath());
//                        20190619_070221.gif
            try {
                if (!file.exists()) {
                    if (targetFile.exists()) {
                        L.e("showSingleFile", "源文件不存在，恢复文件存在，说明已恢复,跳过：" + file.getPath());
                    } else {
                        return false;//都不存在说明这个文件没了
                    }
                } else {
                    FileUtils.rename(file, targetFile);
                }

                String[] pathList = new String[1];
                pathList[0] = targetFile.getPath();
                //直接全部扫描，用于要求系统显示 该文件
                MediaScannerConnection.scanFile(context, pathList,
                        null, new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                                L.e("showSingleFile", "onScanCompleted path:" + path + "    ---uri:" + uri);

                            }
                        });
                return true;
            } catch (Exception e) {
                L.e("showSingleFile", "Exception：" + e.getMessage());
            }
        } else {
            //TODO:有隐患，如果有多个点的话会有问题
            File hideFile = new File(file.getParentFile().getPath() + "/." + file.getName().replace(".", MediaHelper.REPLACE_DOT));
            if (!file.exists()) {
                if (hideFile.exists()) {
                    L.e("showSingleFile", "源文件不存在，隐藏文件存在，说明已隐藏,需要对隐藏文件执行恢复：" + file.getPath());
                    showSingleFile(context, hideFile);
                } else {
                    return false;//都不存在说明这个文件没了
                }
            } else {
                L.e("showSingleFile", "不符合隐藏特征,跳过：" + file.getPath());
            }
        }
        return false;
    }

    /**
     * 批量重命名(不循环遍历仅隐藏当前文件夹下的所有文件和文件夹) 前面加个.可以隐藏文件 ，但是小米手机依然会扫描出来
     * 所以把文件名中的.jpg等后缀 的. 改成 _+_
     * xxx/xxff.jpg -> xxx/.xxff_+_jpg
     *
     * @param directory
     */
    public static void hideFilesUnderDir(@NonNull Context context, File directory) {
        Preconditions.checkNotNull(directory);

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            String[] pathList = new String[files.length];
            try {
                for (int i = 0, len = files.length; i < len; i++) {
                    File file = files[i];
                    if (!file.getName().startsWith(".")) {
                        File targetFile = new File(directory.getPath() + "/." + file.getName().replace(".", MediaHelper.REPLACE_DOT));
                        L.e("hideFilesUnderDir", file.getPath() + "-->to:" + targetFile.getPath());
//                        20190619_070221.gif
                        FileUtils.rename(file, targetFile);
                        pathList[i] = file.getPath();
                    } else {
                        L.e("hideFilesUnderDir", "已隐藏,跳过：" + file.getPath());
                    }
                }

                //直接全部扫描，用于把系统记录的图片记录全部干掉
                MediaScannerConnection.scanFile(context, pathList,
                        null, new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                                L.e("hideFilesUnderDir", "onScanCompleted path:" + path + "    ---uri:" + uri);

                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 批量恢复图片  xxx/.xxff_+_jpg -> xxx/xxff.jpg
     * 并且添加到系统的最近图片中
     *
     * @param directory
     */
    public static void showFilesUnderDir(@NonNull Context context, File directory) {
        Preconditions.checkNotNull(directory);

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            String[] pathList = new String[files.length];
            try {
                for (int i = 0, len = files.length; i < len; i++) {
                    File file = files[i];
                    String fileName = file.getName();
                    if (fileName.startsWith(".") && fileName.length() > 1) {
                        File targetFile = new File(directory.getPath() + "/" + fileName.substring(1).replace(MediaHelper.REPLACE_DOT, "."));
                        L.e("showFilesUnderDir", file.getPath() + "-->to:" + targetFile.getPath());
//                        20190619_070221.gif
                        FileUtils.rename(file, targetFile);
                        pathList[i] = targetFile.getPath();
                    } else {
                        L.e("showFilesUnderDir", "不符合隐藏特征,跳过：" + file.getPath());
                    }
                }

                //直接全部扫描，用于将图片添加到系统的最近文件中
                MediaScannerConnection.scanFile(context, pathList,
                        null, new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                                L.e("showFilesUnderDir", "onScanCompleted path:" + path + "    ---uri:" + uri);

                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //</editor-fold>
    //---------------------------------------------------

    /**
     * 获取文件后缀名
     */
    public static String getFileExtensionName(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return "";
        }
        int endP = fileName.lastIndexOf(".");
        return endP > -1 ? fileName.substring(endP + 1) : "";
    }

    //<editor-fold desc="文件MD5相关">

    /**
     * 校验文件MD5码
     *
     * @param md5
     * @param updateFile
     * @return
     */
    public static boolean checkMD5(String md5, File updateFile) {
        if (TextUtils.isEmpty(md5) || updateFile == null) {
            L.e("MD5 string empty or updateFile null");
            return false;
        }

        String calculatedDigest = getFileMD5(updateFile);
        if (calculatedDigest == null) {
            return false;
        }
        return calculatedDigest.equalsIgnoreCase(md5);
    }

    /**
     * 获取文件MD5码
     *
     * @param updateFile
     * @return
     */
    public static String getFileMD5(File updateFile) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }

        InputStream is;
        try {
            is = new FileInputStream(updateFile);
        } catch (FileNotFoundException e) {
            return null;
        }

        byte[] buffer = new byte[8192];
        int read;
        try {
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
            byte[] md5sum = digest.digest();
            BigInteger bigInt = new BigInteger(1, md5sum);
            String output = bigInt.toString(16);
            // Fill to 32 chars
            output = String.format("%32s", output).replace(' ', '0');
            return output;
        } catch (IOException e) {
            throw new RuntimeException("Unable to process file for MD5", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }

    //</editor-fold>

    /**
     * 获取SDCard的目录路径功能
     */
    public static String getSDCardPath() {
        File sdcardDir = null;
        // 判断SDCard是否存在
        boolean sdcardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdcardExist) {
            sdcardDir = Environment.getExternalStorageDirectory();
        } else {
            sdcardDir = Environment.getDataDirectory();
        }
        return sdcardDir.toString();
    }

    /**
     * 给定根目录，返回一个相对路径所对应的实际文件名.
     *
     * @param baseDir     指定根目录
     * @param absFileName 相对路径名，来自于ZipEntry中的name
     * @return java.io.File 实际的文件
     */
    private static File getRealFileName(String baseDir, String absFileName) {
        String[] dirs = absFileName.split("/");
        File ret = new File(baseDir);
        String substr = null;
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                substr = dirs[i];
                try {
                    //substr.trim();
                    substr = new String(substr.getBytes("8859_1"), "GB2312");

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                ret = new File(ret, substr);

            }
//            L.d("upZipFile", "1ret = " + ret);
            if (!ret.exists())
                ret.mkdirs();
            substr = dirs[dirs.length - 1];
            try {
                //substr.trim();
                substr = new String(substr.getBytes("8859_1"), "GB2312");
//                L.d("upZipFile", "substr = " + substr);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ret = new File(ret, substr);
//            L.d("upZipFile", "2ret = " + ret);
            return ret;
        }

        return ret;
    }


    //<editor-fold desc="文件读写，解压缩">

    /**
     * 解压缩功能.
     * 将ZIP_FILENAME文件解压到ZIP_DIR目录下.
     *
     * @param zip        压缩文件
     * @param folderPath 解压目录
     */
    public static int unZip(File zip, String folderPath) {
        if (zip == null || !zip.exists()) {
            L.e("unZipFile", "unZipFile 目标文件不存在 !!! ");
            return -1;
        }

        try {
            L.e("unZipFile", "-开始解压缩 unZipFile zip文件路径 = " + zip.getPath());
            ZipEntry zipEntry;
            String szName = "";

            ZipFile zipFile = new ZipFile(zip);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                zipEntry = entries.nextElement();
                szName = zipEntry.getName();
                if (zipEntry.isDirectory()) {
                    L.e("unZipFile", "unZipFile file is dir :ze.getName() = " + szName);
                    // get the folder name of the widget
                    szName = szName.substring(0, szName.length() - 1);
                    File folder = new File(folderPath + File.separator + szName);
                    folder.mkdirs();
                } else {
                    //此处有个坑，解压缩出来第一级是文件夹的时候不一定走上面的isDirectory,直接跑这里第二级文件来了
                    //所以第二级文件的parent文件夹不一定存在，所以需要parent创建一下
                    File file = new File(folderPath + File.separator + szName);
                    file.getParentFile().mkdirs();//似乎不需要getParent
                    L.e("unZipFile", file.exists() + "-unZipFile is file path = " + file.getPath());
                    file.createNewFile();
                    // get the output stream of the file
                    FileOutputStream out = new FileOutputStream(file);
                    int len;
                    byte[] buffer = new byte[1024 * 100];
                    InputStream inZip = new BufferedInputStream(zipFile.getInputStream(zipEntry));
                    // read (len) bytes into buffer
                    while ((len = inZip.read(buffer)) != -1) {
                        // write (len) byte from buffer at the position 0
                        out.write(buffer, 0, len);
                        out.flush();
                    }
                    inZip.close();
                    out.close();
                }
            }
            zipFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            L.e("unZipFile", "unZipFile error = " + e.toString());
        }

        return 0;
    }

    /**
     * 解压缩功能.
     * 将ZIP_FILENAME文件解压到ZIP_DIR目录下.
     *
     * @param zipFile    压缩文件
     * @param folderPath 解压目录
     * @deprecated 这个方法有时候有问题，使用上面的方法
     */
    public static int unZipFile(File zipFile, String folderPath) {
        ZipFile zfile = null;
        try {
            zfile = new ZipFile(zipFile);
            Enumeration zList = zfile.entries();
            ZipEntry ze = null;
            byte[] buf = new byte[1024];
            while (zList.hasMoreElements()) {
                ze = (ZipEntry) zList.nextElement();
                if (ze.isDirectory()) {
//                    L.d(TAG, "ze.getName() = " + ze.getName());
                    String dirstr = folderPath + File.separator + ze.getName();
                    //dirstr.trim();
                    dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
//                    L.d(TAG, "str = " + dirstr);
                    File f = new File(dirstr);
                    f.mkdirs();
                    continue;
                }
//                L.d(TAG, "ze.getName() = " + ze.getName());
//                OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName(folderPath, ze.getName())));
                OutputStream os = new BufferedOutputStream(new FileOutputStream(folderPath + File.separator + ze.getName()));
                InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
                int readLen = 0;
                while ((readLen = is.read(buf)) != -1) {
                    os.write(buf, 0, readLen);
                }
                is.close();
                os.close();
            }
            zfile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 读取文件，返回一个byte数组
     *
     * @return
     * @throws
     */
    public static byte[] readFile(File file) throws Exception {
        if (file == null || !file.isFile()) {
            return null;
        }
        FileInputStream inStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);// 把buffer里的数据写入流中
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();

        return data;
    }

    /**
     * 从文件读取对象
     */
    public static Object readObj(String path) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
            }
        }
        return null;
    }

    /**
     * 存储对象到文件,只有实现了Serailiable接口的对象才能被存储
     */
    public static void writeObj(String path, Object object) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.mkdirs();
        }
        try {
            fos = new FileOutputStream(path);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
        } catch (IOException e) {
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
            }
        }
    }

    //</editor-fold>

    //<editor-fold desc="文件创建，复制，删除">

    /**
     * 通过流创建文件
     */
    public static void createFileFormInputStream(InputStream is, String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            byte[] buf = new byte[1376];
            while (is.read(buf) > 0) {
                fos.write(buf, 0, buf.length);
            }
            is.close();
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建文件 当文件不存在的时候就创建一个文件，否则直接返回文件
     *
     * @param path
     * @return
     */
    public static File createFile(String path) {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            L.d("目标文件所在路径不存在，准备创建……");
            if (!createDir(file.getParent())) {
                L.d("创建目录文件所在的目录失败！文件路径【" + path + "】");
            }
        }
        // 创建目标文件
        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    L.d("创建文件成功:" + file.getAbsolutePath());
                }
            }
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建目录 当目录不存在的时候创建文件，否则返回false
     *
     * @param path
     * @return
     */
    public static boolean createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                L.d("创建失败，请检查路径和是否配置文件权限！");
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 拷贝文件
     *
     * @param fromPath
     * @param toPath
     * @return
     */
    public static boolean copy(String fromPath, String toPath) {
        File file = new File(fromPath);
        if (!file.exists()) {
            return false;
        }
        createFile(toPath);
        return copyFile(fromPath, toPath);
    }

    /**
     * 拷贝文件
     *
     * @param fromFile
     * @param toFile
     * @return
     */
    private static boolean copyFile(String fromFile, String toFile) {
        InputStream fosfrom = null;
        OutputStream fosto = null;
        try {
            fosfrom = new FileInputStream(fromFile);
            fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (fosfrom != null) {
                    fosfrom.close();
                }
                if (fosto != null) {
                    fosto.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 复制文件夹及其中的文件
     *
     * @param oldPath String 原文件夹路径 如：data/user/0/com.test/files
     * @param newPath String 复制后的路径 如：data/user/0/com.test/cache
     * @return <code>true</code> if and only if the directory and files were copied;
     * <code>false</code> otherwise
     */
    public static boolean copyFolder(String oldPath, String newPath) {
        try {
            File newFile = new File(newPath);
            if (!newFile.exists()) {
                if (!newFile.mkdirs()) {
                    Log.e("--FileUtil--", "copyFolder: cannot create directory.");
                    return false;
                }
            }
            File oldFile = new File(oldPath);
            String[] files = oldFile.list();
            File temp;
            for (String file : files) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file);
                } else {
                    temp = new File(oldPath + File.separator + file);
                }

                if (temp.isDirectory()) {   //如果是子文件夹
                    copyFolder(oldPath + "/" + file, newPath + "/" + file);
                } else if (!temp.exists()) {
                    Log.e("--FileUtil--", "copyFolder:  oldFile not exist.");
                    return false;
                } else if (!temp.isFile()) {
                    Log.e("--FileUtil--", "copyFolder:  oldFile not file.");
                    return false;
                } else if (!temp.canRead()) {
                    Log.e("--FileUtil--", "copyFolder:  oldFile cannot read.");
                    return false;
                } else {
                    copyFile(temp.getPath(), newPath + "/" + temp.getName());
                }

            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * delete file or directory
     * <ul>
     * <li>if path is null or empty, return true</li>
     * <li>if path not exist, return true</li>
     * <li>if path exist, delete recursion. return true</li>
     * <ul>
     *
     * @param path
     * @return
     */
    public static boolean deleteFileAndFolder(String path) {
        if (TextUtils.isEmpty(path)) {
            return true;
        }

        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }

    /**
     * 删除文件 如果文件存在删除文件，否则返回false
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return 删除成功返回true，否则返回false,如果文件是空，那么永远返回true
     */
    public static boolean deleteDir(File dir) {
        if (dir == null) {
            return true;
        }
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除，或者删除的是文件
        return dir.delete();
    }
    //</editor-fold>


    /**
     * 获取apk文件的icon
     * 该方法耗时较长，最好放到子线程（几十到100多ms）
     *
     * @param context
     * @param path    apk文件路径
     * @return
     */
    public static Drawable getApkIcon(Context context, String path) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            //android有bug，需要下面这两句话来修复才能获取apk图片
            appInfo.sourceDir = path;
            appInfo.publicSourceDir = path;
//			    String packageName = appInfo.packageName;  //得到安装包名称
//	            String version=info.versionName;       //得到版本信息
            return pm.getApplicationIcon(appInfo);
        }
        return null;
    }


    //<editor-fold desc="文件大小相关 ">

    /**
     * 格式化文件大小
     *
     * @param size file.length() 获取文件大小
     * @return
     */
    public static String formatFileSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return CommonUtils.formatZeroDecimal(size) + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static Double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return formatFileSize(blockSize, sizeType);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return formatFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     *
     * @param
     * @return
     * @throws
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
//            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    /**
     * 获取文件夹大小
     */
    public static long getDirSize(String filePath) {
        long size = 0;
        File f = new File(filePath);
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    size += getDirSize(file.getPath());
                    continue;
                }
                size += file.length();
            }
        } else {
            size += f.length();
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private static String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static Double formatFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                BigDecimal b = new BigDecimal((double) fileS);
                fileSizeLong = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                break;
            case SIZETYPE_KB:
//                BigDecimal kb = new BigDecimal((double) fileS / 1024);
//                fileSizeLong = kb.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
//                BigDecimal mb = new BigDecimal((double) fileS / 1048576);
//                fileSizeLong = mb.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                BigDecimal gb = new BigDecimal((double) fileS / 1073741824);
                fileSizeLong = gb.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                fileSizeLong = Double.valueOf(df
//                        .format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    /**
     * 递归返回文件或者目录的大小（单位:KB）
     * 不建议使用这个方法，有点坑
     * 可以使用下面的方法：http://blog.csdn.net/loongggdroid/article/details/12304695
     *
     * @param path
     * @param size
     * @return
     */
    private static float getSize(String path, Float size) {
        File file = new File(path);
        if (file.exists()) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int fileIndex = 0; fileIndex < children.length; ++fileIndex) {
                    float tmpSize = getSize(file.getPath() + File.separator + children[fileIndex], size) / 1000;
                    size += tmpSize;
                }
            } else if (file.isFile()) {
                size += file.length();
            }
        }
        return size;
    }
    //</editor-fold>

    //<editor-fold desc="打开各种格式的文件 ">

    /**
     * 打开文件，如果文件不存在会找到对应的隐藏或者非隐藏文件
     *
     * @param context
     * @param file
     * @return
     */
    public static Intent openFileIncludingHiddenFile(Context context, File file) {
        if (file == null)
            return null;
        File originFile = file;
        if (!file.exists()) {
            originFile = FileUtil.getOriginOrHiddenFileByFileState(file);
        }
        return getOpenFileIntent(context, originFile);
    }

    /**
     * 根据filepath打开文件，filepath对应的文件可能不存在
     *
     * @param context
     * @param filePath
     * @return
     */
    public static Intent openFile(Context context, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return null;

        return getOpenFileIntent(context, filePath);

//        /* 取得扩展名 */
//        String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase();
//        /* 依扩展名的类型决定MimeType */
//        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") ||
//                end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
//            return getAudioFileIntent(filePath);
//        } else if (end.equals("3gp") || end.equals("mp4")) {
//            return getAudioFileIntent(filePath);
//        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") ||
//                end.equals("jpeg") || end.equals("bmp")) {
//            return getImageFileIntent(filePath);
//        } else if (end.equals("apk")) {
//            return getApkFileIntent(filePath);
//        } else if (end.equals("ppt")) {
//            return getPptFileIntent(filePath);
//        } else if (end.equals("xls")) {
//            return getExcelFileIntent(filePath);
//        } else if (end.equals("doc")) {
//            return getWordFileIntent(filePath);
//        } else if (end.equals("pdf")) {
//            return getPdfFileIntent(context, filePath);
//        } else if (end.equals("chm")) {
//            return getChmFileIntent(filePath);
//        } else if (end.equals("txt")) {
//            return getTextFileIntent(filePath, false);
//        } else {
//            return getAllIntent(filePath);
//        }
    }

    //Android获取一个用于打开APK文件的intent
    public static Intent getAllIntent(String param) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "*/*");
        return intent;
    }

    //Android获取一个用于打开APK文件的intent
    public static Intent getApkFileIntent(String param) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        return intent;
    }

    //Android获取一个用于打开VIDEO文件的intent
    public static Intent getVideoFileIntent(String param) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    //Android获取一个用于打开AUDIO文件的intent
    public static Intent getAudioFileIntent(String param) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    //Android获取一个用于打开Html文件的intent
    public static Intent getHtmlFileIntent(String param) {

        Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param).build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    //Android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(String param) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    //Android获取一个用于打开PPT文件的intent
    public static Intent getPptFileIntent(String param) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    //Android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(String param) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    //Android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(String param) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    //Android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent(String param) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    //Android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(String param, boolean paramBoolean) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (paramBoolean) {
            Uri uri1 = Uri.parse(param);
            intent.setDataAndType(uri1, "text/plain");
        } else {
            Uri uri2 = Uri.fromFile(new File(param));
            intent.setDataAndType(uri2, "text/plain");
        }
        return intent;
    }

    //Android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(Context context, String param) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", new File(param));
        intent.setDataAndType(uri, getMimeType(param));
        return intent;
    }

    /**
     * 通用的打开文件的intent，上面的其他intent都可以整合成这一个
     *
     * @param context
     * @param filePath
     * @return
     */
    public static Intent getOpenFileIntent(Context context, String filePath) {
        return getOpenFileIntent(context, new File(filePath));
    }

    public static Intent getOpenFileIntent(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", new File(param));
        intent.setDataAndType(uri, getMimeType(file.getPath()));
        return intent;
    }


    //</editor-fold>
}
