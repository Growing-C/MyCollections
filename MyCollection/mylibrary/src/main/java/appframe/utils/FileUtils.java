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
     * @param charsetName The name of a supported {@link java.nio.charset </code>charset<code>}
     * @return if file not exist, return null, else return content of file
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
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return path;
    }

    public static String getSDCardPath(Context context) {
        String path = null;
        if (isSdcardExits()) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
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
     * 获取文件后缀名
     */
    public static String getFileExtensionName(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return "";
        }
        int endP = fileName.lastIndexOf(".");
        return endP > -1 ? fileName.substring(endP + 1) : "";
    }

}
