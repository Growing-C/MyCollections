package com.cgy.mycollections.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;

import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.util.Log;

import com.cgy.mycollections.functions.mediamanager.MediaHelper;
import appframe.utils.L;
import com.cgy.mycollections.utils.image.ImageLoader;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 文件操作工具 可以创建和删除文件等
 */
public class FileUtil {
    public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值

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
        Preconditions.checkNotNull(file);

        List<File> fileList = new ArrayList<>();
        if (file.isDirectory()) {
            L.e("listFile，这是文件夹，路径为：" + file.getPath());
            File[] files = file.listFiles();
            for (int i = 0, len = files.length; i < len; i++) {
                L.e("路径" + files[i].getPath());
                String filePath = files[i].getPath();
                if (ImageLoader.isPic(filePath)) {
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
            for (int i = 0, len = files.length; i < len; i++) {
                L.e("路径" + files[i].getPath());
            }
        } else {
            L.e("listFile，这是文件，路径为：" + file.getPath());
        }
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
     * 批量重命名 前面加个.可以隐藏文件 ，但是小米手机依然会扫描出来
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

    //---------------------------------------------------

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
     * 存储bitmap
     */
    public static void saveBitmap(@NonNull String filePath, @NonNull Bitmap bitmap) {
        File file = createFile(filePath);
        try {
            FileOutputStream os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 格式化文件大小
     *
     * @param size file.length() 获取文件大小
     * @return
     */
    public static String formatFileSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    /**
     * 获取文件后缀名
     */
    public static String getFileExtensionName(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return "";
        }
        int endP = fileName.lastIndexOf(".");
        return endP > -1 ? fileName.substring(endP + 1, fileName.length()) : "";
    }

    /**
     * 校验文件MD5码
     *
     * @param md5
     * @param updateFile
     * @return
     */
    public static boolean checkMD5(String md5, File updateFile) {
        if (TextUtils.isEmpty(md5) || updateFile == null) {
//            L.e(TAG, "MD5 string empty or updateFile null");
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

    /**
     * 解压缩功能.
     * 将ZIP_FILENAME文件解压到ZIP_DIR目录下.
     *
     * @param zipFile    压缩文件
     * @param folderPath 解压目录
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

    /**
     * 创建文件 当文件不存在的时候就创建一个文件，否则直接返回文件
     *
     * @param path
     * @return
     */
    public static File createFile(String path) {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
//            FL.d(TAG, "目标文件所在路径不存在，准备创建……");
            if (!createDir(file.getParent())) {
//                FL.d(TAG, "创建目录文件所在的目录失败！文件路径【" + path + "】");
            }
        }
        // 创建目标文件
        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
//                    FL.d(TAG, "创建文件成功:" + file.getAbsolutePath());
                }
                return file;
            } else {
                return file;
            }
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
//                FL.d(TAG, "创建失败，请检查路径和是否配置文件权限！");
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
     * 删除文件 如果文件存在删除文件，否则返回false
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
            return true;
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

    /**
     * 获取apk文件的icon
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
        return FormetFileSize(blockSize, sizeType);
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
        return FormetFileSize(blockSize);
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
    private static String FormetFileSize(long fileS) {
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
    private static Double FormetFileSize(long fileS, int sizeType) {
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
}
