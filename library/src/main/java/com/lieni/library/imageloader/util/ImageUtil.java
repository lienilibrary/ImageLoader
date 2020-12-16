package com.lieni.library.imageloader.util;

import android.content.res.Resources;
import android.util.TypedValue;

public class ImageUtil {
    public static int dip2px(float dipValue) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dipValue,
                Resources.getSystem().getDisplayMetrics());
    }
}
