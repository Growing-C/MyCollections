package com.cgy.mycamera.camerax.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.cgy.mycamera.R;

/**
 * Description :The sole purpose of this fragment is to request permissions and, once granted, display the
 * camera fragment to the user.
 * <p>
 * Author :cgy
 * Date :2019/11/21
 */
public class PermissionsFragment extends Fragment {

    private final int PERMISSIONS_REQUEST_CODE = 10;
    //    private List<String> PERMISSIONS_REQUIRED = Collections.singletonList(Manifest.permission.CAMERA);
    private String[] PERMISSIONS_REQUIRED = new String[]{Manifest.permission.CAMERA};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!hasPermissions(requireContext())) {
            // Request camera-related permissions
            requestPermissions(PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST_CODE);
        } else {
            // If permissions have already been granted, proceed
            Navigation.findNavController(requireActivity(), R.id.fragment_container).navigate(
                    PermissionsFragmentDirections.actionPermissionsToCamera());
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                // Take the user to the success fragment when permission is granted
                Toast.makeText(getContext(), "Permission request granted", Toast.LENGTH_LONG).show();
                Navigation.findNavController(requireActivity(), R.id.fragment_container).navigate(
                        PermissionsFragmentDirections.actionPermissionsToCamera());
            } else {
                Toast.makeText(getContext(), "Permission request denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Convenience method used to check if all permissions required by this app are granted
     */
    static boolean hasPermissions(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }
}
