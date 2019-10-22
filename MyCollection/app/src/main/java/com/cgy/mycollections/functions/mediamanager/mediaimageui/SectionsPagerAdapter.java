package com.cgy.mycollections.functions.mediamanager.mediaimageui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import androidx.fragment.app.Fragment;
//import androidx.core.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cgy.mycollections.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> mFragmentList = new ArrayList<>();
    List<String> mTabTitleList = new ArrayList<>();

    //    @StringRes
//    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_all_images, R.string.tab_text_image_files};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    public void setFragmentList(@NonNull List<String> titleList, @NonNull List<Fragment> fragmentList) {
        this.mFragmentList = fragmentList;
        this.mTabTitleList = titleList;
        if (this.mFragmentList.size() != this.mTabTitleList.size())
            throw new IllegalArgumentException("title和fragment个数必须相等");
    }

    public void addFragment(@NonNull String title, @NonNull Fragment fragment) {
        mFragmentList.add(fragment);
        mTabTitleList.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a ImageFilesFragment (defined as a static inner class below).
//        L.e("getItem:" + position);

        return mFragmentList.get(position);

//        if (position == 0)
//            return AllImagesFragment.newInstance(0);
//
//        return ImageFilesFragment.newInstance(1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitleList.get(position);
//        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return mTabTitleList.size();
//        return 2;
    }
}