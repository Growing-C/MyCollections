package com.cgy.mycollections.functions.jetpack.camerax;

import android.content.Intent;
import android.hardware.camera2.CameraMetadata;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cgy.mycollections.R;

import appframe.permission.PermissionDenied;
import appframe.permission.PermissionDialog;
import appframe.permission.PermissionGranted;
import appframe.permission.PermissionManager;
import appframe.utils.Logger;
import butterknife.ButterKnife;
import butterknife.OnClick;

//import androidx.core.app.Fragment;


/**
 * A placeholder fragment containing a simple view.
 */
public class CameraXFragment extends Fragment {


    public static CameraXFragment newInstance() {
        CameraXFragment fragment = new CameraXFragment();
        Bundle bundle = new Bundle();
//        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_camerax, container, false);
        ButterKnife.bind(this, root);


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PermissionManager.requestCameraPermission(this, "camera necessary");
    }

    @OnClick({R.id.open_system, R.id.open_camera})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_system:
                openSystemCamera();
                break;
            case R.id.open_camera:
                openCamera();
                break;
            default:
                break;
        }
    }

    @PermissionGranted(requestCode = PermissionManager.REQUEST_CAMERA_PERMISSION)
    public void externalPermissionGranted() {
        Logger.i("CameraPermissionGranted");
    }

    @PermissionDenied(requestCode = PermissionManager.REQUEST_EXTERNAL_PERMISSION)
    public void externalPermissionDenied() {
        Logger.e("externalPermissionDenied");

        PermissionDialog mPermissionDialog = new PermissionDialog(getActivity(), "需要相机权限");
        mPermissionDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    private void openSystemCamera() {
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //resolveActivity 是防止Intent里面找不到activity导致崩溃
        if (getActivity() != null && it.resolveActivity(getActivity().getPackageManager()) != null) {
//            http://androidxref.com/9.0.0_r3/xref/cts/tests/camera/utils/src/android/hardware/camera2/cts/CameraTestUtils.java
//在上面的源码中 使用 android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT 可以指定相机方向，不过目前测试仅有小米手机有用，其他手机如三星都不行
//        it.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
            it.putExtra("android.intent.extras.CAMERA_FACING", CameraMetadata.LENS_FACING_FRONT);
//            it.putExtra("android.lens.facing", CameraMetadata.LENS_FACING_FRONT);
//        it.putExtra("android.intent.extras.LENS_FACING_FRONT", CameraMetadata.LENS_FACING_FRONT);
//        it.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
            it.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
            startActivityForResult(it, 333);
        }
    }

    private void openCamera() {
        startActivity(new Intent(getActivity(), CameraXDemo.class));
    }
}