package com.cgy.mycollections.functions.mediamanager.mediaimageui;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.cgy.mycollections.MyApplication;
import com.cgy.mycollections.functions.mediamanager.MediaHelper;
import com.cgy.mycollections.functions.mediamanager.images.ThumbnailInfo;
import com.cgy.mycollections.utils.L;

import org.web3j.abi.datatypes.Bool;

import java.util.List;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private MutableLiveData<List<ThumbnailInfo>> mThumbnailsInfo = new MutableLiveData<>();

    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Hello world from section: " + input;
        }
    });

    private LiveData<List<ThumbnailInfo>> mThumbnails = Transformations.map(mIndex, new Function<Integer, List<ThumbnailInfo>>() {
        @Override
        public List<ThumbnailInfo> apply(Integer input) {
            L.e("getThumbnails apply:" + input);
            if (input != null && input == 0) {
                List<ThumbnailInfo> infoList = MediaHelper.getThumbnailsList(MyApplication.getInstance());
                mThumbnailsInfo.setValue(infoList);
            }
            return mThumbnailsInfo.getValue();
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<ThumbnailInfo>> getThumbnails() {
        return mThumbnails;
    }

    public LiveData<List<ThumbnailInfo>> test() {
        return  Transformations.map(mThumbnailsInfo, new Function<List<ThumbnailInfo>, List<ThumbnailInfo>>() {
            @Override
            public List<ThumbnailInfo> apply(List<ThumbnailInfo> input) {
                L.e("test  apply:" + (input == null));
                return mThumbnailsInfo.getValue();
            }
        });
    }

}