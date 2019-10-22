package com.cgy.mycollections.functions.jetpack.camerax;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cgy.mycollections.R;

import butterknife.ButterKnife;

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
        View root = inflater.inflate(R.layout.fragment_room, container, false);
        ButterKnife.bind(this, root);


        return root;
    }
}