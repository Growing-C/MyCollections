package appframe.permission;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * when you are using android M (android 6.0  target SDK version >= 23),you need to pro grammatically declare permissions.
 * this class is used to setup permission for android M(also compatible with versions below 23).
 * remember you should declare same permission in manifest file as well.
 *
 * @link https://github.com/hongyangAndroid/MPermissions
 * @link https://github.com/lovedise/PermissionGen
 * this is a combination  of the above two project ,use runtime RetentionPolicy and static method.
 * Created by RB-cgy on 2016/8/10.
 */
public class PermissionManager {
    public static final int REQUEST_CAMERA_PERMISSION = 1;//requestCode of camera permission operation
    public static final int REQUEST_CALL_PHONE_PERMISSION = 2;//requestCode of call  permission operation
    public static final int REQUEST_EXTERNAL_PERMISSION = 3;//requestCode of EXTERNAL permission operation
    public static final int REQUEST_PHONE_STATE_PERMISSION = 4;//requestCode of phone state  permission operation
    public static final int REQUEST_INSTALL_PACKAGES = 5;//requestCode of phone state  permission operation

    /**
     * convenient method to request Camera permission
     *
     * @param object should be instance of Activity or Fragment .
     * @see #doRequestPermissions(Object, int, String...)
     */
    public static void requestCameraPermission(Object object) {
        doRequestPermissions(object, REQUEST_CAMERA_PERMISSION, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    /**
     * convenient method to request Call phone permission
     *
     * @param object should be instance of Activity or Fragment
     * @see #doRequestPermissions(Object, int, String...)
     */
    public static void requestCallPhonePermission(Object object) {
        doRequestPermissions(object, REQUEST_CALL_PHONE_PERMISSION, new String[]{Manifest.permission.CALL_PHONE});
    }


    public static void requestExternalPermission(Object object) {
        doRequestPermissions(object, REQUEST_EXTERNAL_PERMISSION, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    /**
     * convenient method to request phone state permission
     *
     * @param object should be instance of Activity or Fragment
     * @see #doRequestPermissions(Object, int, String...)
     */
    public static void requestPhoneStatePermission(Object object) {
        doRequestPermissions(object, REQUEST_PHONE_STATE_PERMISSION, new String[]{Manifest.permission.READ_PHONE_STATE});
    }

    /**
     * used for activities to grant permissions
     *
     * @param object
     * @param requestCode
     * @param permissions
     */
    public static void requestPermissions(Activity object, int requestCode, String... permissions) {
        doRequestPermissions(object, requestCode, permissions);
    }

    /**
     * used for fragments to grant permissions
     *
     * @param object
     * @param requestCode
     * @param permissions
     */
    public static void requestPermissions(Fragment object, int requestCode, String... permissions) {
        doRequestPermissions(object, requestCode, permissions);
    }

    /**
     * TODO:to be completed
     *
     * @param activity
     * @param permission
     * @param requestCode
     * @return
     */
    public boolean shouldShowRequestPermissionRationale(Activity activity, String permission, int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                permission)) {
            return true;
        }
        return false;
    }

    /**
     * every request will finally comes to this method. find denied permissions and request them.
     *
     * @param object
     * @param requestCode
     * @param permissions
     */
    @TargetApi(value = Build.VERSION_CODES.M)
    private static void doRequestPermissions(Object object, int requestCode, String... permissions) {
        if (!PermissionUtils.isOverMarshmallow()) {
            doExecuteSuccess(object, requestCode);
            return;
        }
        List<String> deniedPermissions = PermissionUtils.findDeniedPermissions(PermissionUtils.getActivity(object), permissions);

//        System.out.println("deniedPermissions:"+deniedPermissions.size());
        if (deniedPermissions.size() > 0) {
            if (object instanceof Activity) {
                ((Activity) object).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            } else if (object instanceof Fragment) {
                ((Fragment) object).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            } else {
                throw new IllegalArgumentException(object.getClass().getName() + " is not supported!");
            }
        } else {
            doExecuteSuccess(object, requestCode);
        }
    }


    /**
     * invoked after permission grant succeeded,use the method with runtime annotation in the activity
     *
     * @param activity
     * @param requestCode
     */
    private static void doExecuteSuccess(Object activity, int requestCode) {
        Method executeMethod = PermissionUtils.findMethodWithRequestCode(activity.getClass(),
                PermissionGranted.class, requestCode);

        executeMethod(activity, executeMethod);
    }

    /**
     * invoked after permission grant failed,use the method with runtime annotation in the activity
     *
     * @param activity
     * @param requestCode
     */
    private static void doExecuteFail(Object activity, int requestCode) {
        Method executeMethod = PermissionUtils.findMethodWithRequestCode(activity.getClass(),
                PermissionDenied.class, requestCode);

        executeMethod(activity, executeMethod);
    }

    /**
     * execute activity method
     *
     * @param activity
     * @param executeMethod
     */
    private static void executeMethod(Object activity, Method executeMethod) {
        if (executeMethod != null) {
            try {
                if (!executeMethod.isAccessible()) executeMethod.setAccessible(true);
                executeMethod.invoke(activity, new Object[]{});
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * used in Activity.onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
     *
     * @param activity
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions,
                                                  int[] grantResults) {
        requestResult(activity, requestCode, permissions, grantResults);
    }

    /**
     * used in Fragment.onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
     *
     * @param fragment
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionsResult(Fragment fragment, int requestCode, String[] permissions,
                                                  int[] grantResults) {
        requestResult(fragment, requestCode, permissions, grantResults);
    }

    private static void requestResult(Object obj, int requestCode, String[] permissions,
                                      int[] grantResults) {
        List<String> deniedPermissions = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permissions[i]);
            }
        }
        if (deniedPermissions.size() > 0) {
            doExecuteFail(obj, requestCode);
        } else {
            doExecuteSuccess(obj, requestCode);
        }
    }
}
