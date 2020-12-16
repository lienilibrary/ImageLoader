package com.lieni.library.imageloader.loader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lieni.library.imageloader.config.ShapeMode;
import com.lieni.library.imageloader.config.SingleConfig;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class GlideLoader implements ILoader {

    @Override
    public void request(final SingleConfig config) {
        if (isInvalidContext(config.getContext())) return;
        if (config.isAsBitmap()) {
            getBitmapRequestBuilder(config).into(new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    if (config.getCallback() != null) {
                        config.getCallback().onSuccess(resource);
                    }
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {

                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    if (config.getCallback() != null) {
                        config.getCallback().onFail(errorDrawable);
                    }
                }
            });

        } else if (config.isGif()) {
            getGifRequestBuilder(config).into((ImageView) config.getTarget());
        } else {
            getDrawableRequestBuilder(config).into((ImageView) config.getTarget());
        }
    }

    private RequestBuilder<Drawable> getDrawableRequestBuilder(SingleConfig config) {
        return getRequestBuilder(config, Glide.with(config.getContext()).as(Drawable.class));
    }

    private RequestBuilder<GifDrawable> getGifRequestBuilder(SingleConfig config) {
        return getRequestBuilder(config, Glide.with(config.getContext()).asGif());
    }

    private RequestBuilder<Bitmap> getBitmapRequestBuilder(SingleConfig config) {
        return getRequestBuilder(config, Glide.with(config.getContext()).asBitmap());
    }

    private <T> RequestBuilder<T> getRequestBuilder(SingleConfig config, RequestBuilder<T> requestBuilder) {

        if (config.getResId() > 0) {
            requestBuilder = requestBuilder.load(config.getResId());
        } else if (config.getFile() != null) {
            requestBuilder = requestBuilder.load(config.getFile());
        } else if (config.getBitmap() != null) {
            requestBuilder = requestBuilder.load(config.getBitmap());
        } else if (!TextUtils.isEmpty(config.getAssertsPath())) {
            requestBuilder = requestBuilder.load(config.getAssertsPath());
        } else {
            requestBuilder = requestBuilder.load(config.getUrl());
        }

        if (config.isSkipMemory()) {
            requestBuilder = requestBuilder
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true);
        } else {
            requestBuilder = requestBuilder
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(false);
        }
        if (config.getErrorResId() > 0) {
            requestBuilder = requestBuilder.error(config.getErrorResId());
        }
        if (config.getoWidth() != 0 && config.getoHeight() != 0) {
            requestBuilder = requestBuilder.override(config.getoWidth(), config.getoHeight());
        }
        return requestBuilder.apply(getOptions(config));
    }

    /**
     * 图片形状配置
     * <p>
     * 占位图
     * 圆角
     * 高斯模糊
     */
    private RequestOptions getOptions(SingleConfig config) {
        RequestOptions options = new RequestOptions();
        if (config.getPlaceholderResId() > 0) {
            options = options.placeholder(config.getPlaceholderResId());
        }
        switch (config.getShapeMode()) {
            case ShapeMode.RECT_ROUND:
                options = options.transform(new RoundedCornersTransformation(config.getRectRoundRadius(), 0));
                break;
            case ShapeMode.OVAL:
                options.circleCrop();
                break;
        }
        if (config.isNeedBlur()) {
            options = options.transform(new BlurTransformation(25, 4), new ColorFilterTransformation(Color.parseColor("#80000000")));
        }
        return options;
    }

    private boolean isInvalidContext(Context context) {
        if (context == null) return true;
        if (context instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) context;
            return fragmentActivity.isDestroyed();
        } else if (context instanceof Activity) {
            Activity activity = (Activity) context;
            return activity.isDestroyed();
        }
        return false;
    }
}
