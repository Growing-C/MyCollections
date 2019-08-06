package com.cgy.mycollections.functions.mediamanager.mediaimageui;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.cgy.mycollections.functions.mediamanager.MediaHelper;
import com.cgy.mycollections.functions.mediamanager.images.ThumbnailInfo;

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

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<ThumbnailInfo>> getThumbnails(final Context context) {
        return Transformations.map(mThumbnailsInfo, new Function<List<ThumbnailInfo>, List<ThumbnailInfo>>() {
            @Override
            public List<ThumbnailInfo> apply(List<ThumbnailInfo> input) {
                if (input == null || input.isEmpty()) {
                    List<ThumbnailInfo> infoList = MediaHelper.getThumbnailsList(context);
                    mThumbnailsInfo.setValue(infoList);
                }
                return mThumbnailsInfo.getValue();
            }
        });
    }
}