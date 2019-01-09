package com.cgy.mycollections.functions.androiddesign.recyclerview.contact;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cgy.mycollections.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description :
 * Author :cgy
 * Date :2018/11/26
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactHolder> {

    List<ContactModel> mContactList = new ArrayList<>();

    public ContactListAdapter() {

    }

    public void setData(List<ContactModel> contactList) {
        this.mContactList = contactList;
        notifyDataSetChanged();
    }

    public ContactModel getItem(int position) {
        return mContactList.get(position);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0, count = getItemCount(); i < count; i++) {
            String sortStr = mContactList.get(i).getNameFirstLetter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_recycler_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        holder.setData(mContactList.get(position));
    }

    @Override
    public int getItemCount() {
        return mContactList == null ? 0 : mContactList.size();
    }

    class ContactHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text)
        TextView text;

        public ContactHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(ContactModel model) {
            text.setText(model.name + "---" + model.mobile);
        }
    }
}
