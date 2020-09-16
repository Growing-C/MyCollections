package com.cgy.mycollections.functions.jetpack.room;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.cgy.mycollections.R;
import com.cgy.mycollections.functions.jetpack.room.data.AppDatabase;
import com.cgy.mycollections.functions.jetpack.room.data.User;
import com.cgy.mycollections.functions.mediamanager.mediaimageui.PageViewModel;
import appframe.utils.L;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//import androidx.core.app.Fragment;


/**
 * A placeholder fragment containing a simple view.
 */
public class RoomFragment extends Fragment {

    @BindView(R.id.account_name)
    EditText mUserName;
    @BindView(R.id.password)
    EditText mAge;

    @BindView(R.id.account_list)
    RecyclerView mUserList;


    public static RoomFragment newInstance() {
        RoomFragment fragment = new RoomFragment();
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.e("onActivityCreated");
        if (getContext() != null) {
            AppDatabase db = AppDatabase.getInstance(getContext());

//            List<User> userList = db.userDao().getAll();
        }
    }
}