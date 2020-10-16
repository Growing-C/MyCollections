package com.growingc.mediaoperator.medialoader;


import android.database.Cursor;
import android.os.Bundle;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

/**
 * 作者: cgy
 * 创建日期: 2020/10/9 17:27
 * 修改日期: 2020/10/9 17:27
 * 类说明：
 */
public class MediaLoader implements LoaderManager.LoaderCallbacks<Cursor> {


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
