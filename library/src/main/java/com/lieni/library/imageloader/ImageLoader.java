package com.lieni.library.imageloader;

import android.content.Context;

import com.lieni.library.imageloader.config.SingleConfig;

public class ImageLoader {

    public static SingleConfig.ConfigBuilder with(Context context) {
        return new SingleConfig.ConfigBuilder(context);
    }
}
