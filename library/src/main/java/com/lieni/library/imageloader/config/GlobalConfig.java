package com.lieni.library.imageloader.config;

import com.lieni.library.imageloader.loader.GlideLoader;
import com.lieni.library.imageloader.loader.ILoader;

public class GlobalConfig {
    private static ILoader loader;

    public static ILoader getLoader() {

        if (loader == null) {
            loader = new GlideLoader();
        }

        return loader;
    }
}
