package com.cgy.mycollections.functions.ui.androiddesign.recyclerview.contact;

import android.database.Cursor;
import android.provider.ContactsContract;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
//import androidx.appcompat.widget.DividerItemDecoration;
//import androidx.appcompat.widget.LinearLayoutManager;
//import androidx.appcompat.widget.RecyclerView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgy.mycollections.R;
import appframe.utils.L;
import com.cgy.mycollections.widgets.WaveSideBarView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import appframe.permission.PermissionDenied;
import appframe.permission.PermissionGranted;
import appframe.permission.PermissionManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactListActivity extends AppCompatActivity {

    @BindView(R.id.side_bar)
    WaveSideBarView mSideBar;

    @BindView(R.id.contact_list)
    RecyclerView mContactList;

    ContactItemDecoration mItemDecoration;

    LinearLayoutManager mLayoutManager;
    ContactListAdapter mAdapter;
    List<ContactModel> mContacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        ButterKnife.bind(this);

        //RecyclerView设置manager
        mLayoutManager = new LinearLayoutManager(this);
        mContactList.setLayoutManager(mLayoutManager);
        mAdapter = new ContactListAdapter();
        mContactList.setAdapter(mAdapter);

        //设置右侧SideBar触摸监听
        mSideBar.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(letter.charAt(0));
                if (position != -1) {
                    mLayoutManager.scrollToPositionWithOffset(position, 0);
                }
            }
        });

        PermissionManager.requestReadContactPermission(this, "necessary permission to read contact");
    }


    @PermissionGranted(requestCode = PermissionManager.REQUEST_READ_CONTACT)
    public void onReadContactPermissionGranted() {
        L.e("onReadContactPermissionGranted");
        mContacts = readContacts();
        Collections.sort(mContacts); // 根据a-z进行排序源数据

        mItemDecoration = new ContactItemDecoration(this, mContacts);
        //如果add两个，那么按照先后顺序，依次渲染。
        mContactList.addItemDecoration(mItemDecoration);
        mContactList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mAdapter.setData(mContacts);
    }

    @PermissionDenied(requestCode = PermissionManager.REQUEST_READ_CONTACT)
    public void onReadContactPermissionDenied() {
        L.e("onReadContactPermissionDenied");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    /**
     * 读取通讯录联系人
     *
     * @return
     */
    private List<ContactModel> readContacts() {

        List<ContactModel> contacts = new ArrayList<>();
        Cursor cursor = null;
        try {
            //查询联系人数据,使用了getContentResolver().query方法来查询系统的联系人的数据
            //CONTENT_URI就是一个封装好的Uri，是已经解析过得常量
            cursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
            //对cursor进行遍历，取出姓名和电话号码
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    ContactModel contactModel = new ContactModel();
                    //获取联系人姓名
                    contactModel.name = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                    ));
                    //获取联系人手机号
                    contactModel.mobile = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    ));

                    contacts.add(contactModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //记得关掉cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return contacts;
    }
}
