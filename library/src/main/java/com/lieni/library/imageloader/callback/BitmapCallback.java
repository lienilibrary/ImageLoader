package com.lieni.library.imageloader.callback;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public interface BitmapCallback {

    void onSuccess(Bitmap bitmap);

    void onFail(Drawable errorDrawable);
}
