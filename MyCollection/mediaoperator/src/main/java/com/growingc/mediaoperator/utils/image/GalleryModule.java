package com.growingc.mediaoperator.utils.image;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.module.LibraryGlideModule;

/**
 * Ensures that Glide's generated API is created for the Gallery sample.
 * 初始化glide Generated API，仅仅应该在app中使用，library中不应该使用
 * 一个glide源码分析博客
 * https://www.maoqitian.com/2019/02/19/%E4%BB%8E%E6%BA%90%E7%A0%81%E8%A7%92%E5%BA%A6%E6%B7%B1%E5%85%A5%E7%90%86%E8%A7%A3glide%EF%BC%88%E4%B8%8A%EF%BC%89/
 */
@GlideModule
public final class GalleryModule extends LibraryGlideModule {
    // Intentionally empty.
}
