package appframe.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils {
    private static final String COMPANY_DOMAIN = "/witon/";//公司

    private FileUtils() {
        throw new AssertionError();
    }

    /**
     * 获取相机拍照的路径
     *
     * @return
     */
    public static String getCameraPhotoPath() {
        String photoPath = getSDCardPath();
        if (!TextUtils.isEmpty(photoPath)) {
            photoPath += "/DCIM/Camera";//默认的相册位置
        }
        return photoPath;
    }

    /**
     * 获取用户头像图片本地路径
     *
     * @param userLogonName
     * @return
     */
    public static String getUserHeaderDirPath(String userLogonName) {
        String dirPath = getSDCardPath();
        if (!TextUtils.isEmpty(dirPath)) {
            dirPath += COMPANY_DOMAIN;
            if (!TextUtils.isEmpty(userLogonName)) {
                dirPath += userLogonName;
            }
        }

        return dirPath;
    }

    /**
     * 读取文件，返回一个byte数组
     *
     * @return
     * @throws Exception
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
     * read file
     *
     * @param filePath
     * @param charsetName The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    public static StringBuilder readFile(String filePath, String charsetName) {
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }


    /**
     * Indicates if this file represents a file on the underlying file system.
     *
     * @param filePath
     * @return
     */
    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }

        File file = new File(filePath);
        return (file.exists() && file.isFile());
    }

    /**
     * Indicates if this file represents a directory on the underlying file system.
     *
     * @param directoryPath
     * @return
     */
    public static boolean isFolderExist(String directoryPath) {
        if (TextUtils.isEmpty(directoryPath)) {
            return false;
        }

        File dire = new File(directoryPath);
        return (dire.exists() && dire.isDirectory());
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
    public static boolean deleteFile(String path) {
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
     * 检测Sdcard是否存在
     */
    public static boolean isSdcardExits() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    public static String getSDCardPath() {
        String path = null;
        if (isSdcardExits()) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
        }
        return path;
    }

    public static String getSDCardPath(Context context) {
        String path = null;
        if (isSdcardExits()) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
        } else {
            path = context.getFilesDir().toString();
        }
        return path;
    }


    /**
     * 判断当前Url是否标准的content://样式，如果不是，则返回绝对路径
     *
     * @param mUri
     * @return
     */
    public static String getAbsolutePathFromNoStandardUri(Uri mUri) {
        String filePath = null;
        String mUriString = mUri.toString();
        mUriString = Uri.decode(mUriString);
        String pre1 = "file://" + "/sdcard" + File.separator;
        String pre2 = "file://" + "/mnt/sdcard" + File.separator;
        if (mUriString.startsWith(pre1)) {
            filePath = Environment.getExternalStorageDirectory().getPath() + File.separator
                    + mUriString.substring(pre1.length());
        } else if (mUriString.startsWith(pre2)) {
            filePath = Environment.getExternalStorageDirectory().getPath() + File.separator
                    + mUriString.substring(pre2.length());
        }
        return filePath;
    }

    /**
     * 通过uri获取文件的绝对路径
     *
     * @param uri
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getAbsoluteImagePath(Activity context, Uri uri) {
        String imagePath = "";
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.managedQuery(uri, proj, // Which columns to return
                null, // WHERE clause; which list to return (all list)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                imagePath = cursor.getString(column_index);
            }
        }
        return imagePath;
    }

    /**
     * 得到圆形图片
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled())
            return bitmap;

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = bitmap.getWidth() / 2;

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xff424242);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 读取SD卡图片并缩小到相应尺寸
     */
    public static final Bitmap getSizeBitmap(String filepath, int maxWidth, int maxHeight) {
        Bitmap bitmap = null;
        if (!new File(filepath).exists()) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 当为true时，不为图片分配内存，只获取图片的大小，并保存在opts的outWidth和outHeight中
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath, options);
        int srcWidth = options.outWidth;
        int srcHeight = options.outHeight;
        options.inJustDecodeBounds = false;
        if (srcWidth <= maxWidth && srcHeight <= maxHeight) {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            bitmap = BitmapFactory.decodeFile(filepath, options);
            LogUtils.i("未缩放，srcWidth = " + srcWidth + ", srcHeight = " + srcHeight);
        } else {
            LogUtils.i("缩放前，srcWidth = " + srcWidth + ", srcHeight = " + srcHeight);
            float be = 1;
            if (srcWidth / (float) srcHeight >= maxWidth / (float) maxHeight)
                be = srcWidth / (float) maxWidth;
            else
                be = srcHeight / (float) maxHeight;
            options.inSampleSize = (int) be;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            bitmap = BitmapFactory.decodeFile(filepath, options);

            if (bitmap == null) {// 此读图方式只是试试
                try {
                    bitmap = BitmapFactory.decodeFileDescriptor(
                            new FileInputStream(new File(filepath)).getFD(), null,
                            options);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            /** 若只是缩小图片，到此 return bitmap即可，以下内容是返回的固定大小图片 */

            int tempWidth = bitmap.getWidth();
            int tempHeight = bitmap.getHeight();
            float scale_w = ((float) maxWidth) / tempWidth;
            float scale_h = ((float) maxHeight) / tempHeight;
            // 按原图比例缩小
            Matrix matrix = new Matrix();
            float scale = (scale_w < scale_h ? scale_w : scale_h);
            matrix.postScale(scale, scale);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, tempWidth, tempHeight, matrix, true);
            LogUtils.i("tempWidth = " + tempWidth + "tempHeight = " + tempHeight);
        }
        return bitmap;
    }

    /**
     * 把bitmap图像保存到本地
     *
     * @param bitmap
     * @param path
     * @return true-代表保存成功，false-代表保存失败
     */
    public static boolean saveBitmapToSdcard(Bitmap bitmap, String path) {
        if (bitmap != null && !bitmap.isRecycled()) {
            File file = new File(path);
            File dir = file.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (file.exists())
                file.delete();
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file);
                if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                    out.flush();
                    return true;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

}
