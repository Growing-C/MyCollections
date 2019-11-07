package appframe.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.fragment.app.Fragment;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RB-cgy on 2016/8/10.
 */
public class PermissionUtils {
    private PermissionUtils() {
    }

    /**
     * whether sdk version is 23 or higher
     *
     * @return true-if sdk version is above 23,false-otherwise
     */
    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * get denied permissions from argument
     *
     * @param activity
     * @param permission
     * @return
     */
    @TargetApi(value = Build.VERSION_CODES.M)
    public static List<String> findDeniedPermissions(Activity activity, String... permission) {
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permission) {
            if (activity.checkSelfPermission(value) != PackageManager.PERMISSION_GRANTED) {
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }


    /**
     * get activity instance from object
     *
     * @param object
     * @return
     */
    public static Activity getActivity(Object object) {
        if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof Activity) {
            return (Activity) object;
        }
        return null;
    }

    /**
     * find the specific annotation method from clazz ,
     * the annotation of the method must be the same type with argument annotation
     * and the requestCode of the method should also equals the argument requestCode
     *
     * @param clazz       where to find the annotation method
     * @param annotation  which type the annotation method belongs to
     * @param requestCode the appointed request code of the annotation method
     * @param <A>
     * @return
     */
    public static <A extends Annotation> Method findMethodWithRequestCode(Class clazz,
                                                                          Class<A> annotation, int requestCode) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                if (isEqualRequestCodeFromAnntation(method, annotation, requestCode)) {
                    return method;
                }
            }
        }
        return null;
    }

    public static boolean isEqualRequestCodeFromAnntation(Method m, Class clazz, int requestCode) {
        if (clazz.equals(PermissionDenied.class)) {
            return requestCode == m.getAnnotation(PermissionDenied.class).requestCode();
        } else if (clazz.equals(PermissionGranted.class)) {
            return requestCode == m.getAnnotation(PermissionGranted.class).requestCode();
        } else {
            return false;
        }
    }
}
