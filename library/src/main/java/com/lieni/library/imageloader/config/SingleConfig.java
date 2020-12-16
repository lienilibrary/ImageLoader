package com.lieni.library.imageloader.config;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.lieni.library.imageloader.callback.BitmapCallback;
import com.lieni.library.imageloader.util.ImageUtil;

import java.io.File;

public class SingleConfig {
    private View target;
    private Context context;
    private String url;
    private File file;
    private int resId;
    private String assertsPath;  //asserts路径
    private boolean isGif;
    private int errorResId;
    private int placeholderResId;
    private Bitmap bitmap;
    private int shapeMode;//默认矩形,可选直角矩形,圆形/椭圆
    private int rectRoundRadius;//圆角矩形时圆角的半径
    private boolean needBlur;//是否需要模糊
    private int oWidth;
    private int oHeight;
    private boolean skipMemory = false;

    private boolean asBitmap;//只获取bitmap
    private BitmapCallback callback;


    public SingleConfig(ConfigBuilder builder) {
        this.context = builder.context;
        this.url = builder.url;
        this.file = builder.file;
        this.resId = builder.resId;
        this.assertsPath = builder.assertsPath;
        this.placeholderResId = builder.placeholderResId;
        this.isGif = builder.isGif;
        this.needBlur = builder.needBlur;
        this.bitmap = builder.bitmap;
        this.target = builder.target;
        this.asBitmap = builder.asBitmap;
        this.callback = builder.callback;
        this.errorResId = builder.errorResId;
        this.shapeMode = builder.shapeMode;
        if (shapeMode == ShapeMode.RECT_ROUND) {
            this.rectRoundRadius = builder.rectRoundRadius;
        }
        this.oWidth = builder.oWidth;
        this.oHeight = builder.oHeight;
        this.skipMemory = builder.skipMemory;
    }

    public boolean isNeedBlur() {
        return needBlur;
    }

    public int getoWidth() {
        return oWidth;
    }

    public int getoHeight() {
        return oHeight;
    }

    public View getTarget() {
        return target;
    }

    public Context getContext() {
        return context;
    }

    public String getUrl() {
        return url;
    }

    public File getFile() {
        return file;
    }

    public int getResId() {
        return resId;
    }

    public String getAssertsPath() {
        return assertsPath;
    }

    public int getPlaceholderResId() {
        return placeholderResId;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getErrorResId() {
        return errorResId;
    }

    public BitmapCallback getCallback() {
        return callback;
    }

    public boolean isAsBitmap() {
        return asBitmap;
    }

    public int getShapeMode() {
        return shapeMode;
    }

    public int getRectRoundRadius() {
        return rectRoundRadius;
    }

    private void show() {
        GlobalConfig.getLoader().request(this);
    }

    public boolean isGif() {
        return isGif;
    }

    public boolean isSkipMemory() {
        return skipMemory;
    }

    public static class ConfigBuilder {
        private View target;
        private Context context;
        private String url;
        private File file;
        private int resId;
        private boolean isGif = false;
        private String assertsPath;
        private int placeholderResId;
        private Bitmap bitmap;
        private int errorResId;
        private int shapeMode;
        private int rectRoundRadius;//圆角矩形时圆角的半径
        private boolean needBlur = false;//是否需要模糊
        private int oWidth; //选择加载分辨率的宽
        private int oHeight; //选择加载分辨率的高
        private boolean skipMemory = false;

        private boolean asBitmap;
        private BitmapCallback callback;

        public void into(View targetView) {
            this.target = targetView;
            new SingleConfig(this).show();
        }

        public void asBitmap(BitmapCallback bitmapCallback) {
            this.callback = bitmapCallback;
            this.asBitmap = true;
            new SingleConfig(this).show();
        }

        public ConfigBuilder asGif() {
            this.isGif = true;
            return this;
        }

        public ConfigBuilder(Context context) {
            this.context = context;
        }

        public ConfigBuilder setNeedBlur(boolean needBlur) {
            this.needBlur = needBlur;
            return this;
        }

        /**
         * 设置网络路径
         */
        public ConfigBuilder url(String url) {
            this.url = url;
            return this;
        }

        /**
         * 加载SD卡资源
         */
        public ConfigBuilder file(File file) {
            this.file = file;
            return this;
        }

        /**
         * 加载drawable资源
         */
        public ConfigBuilder res(int resId) {
            this.resId = resId;
            return this;
        }

        /**
         * 加载asserts资源
         */
        public ConfigBuilder asserts(String assertsPath) {
            this.assertsPath = assertsPath;
            if (assertsPath.contains("gif")) {
                isGif = true;
            }
            return this;
        }

        /**
         * 加载bitmap
         */
        public ConfigBuilder bitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
            return this;
        }

        /**
         * error图
         */
        public ConfigBuilder error(int errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        /**
         * 占位图
         */
        public ConfigBuilder placeholder(int placeholderResId) {
            this.placeholderResId = placeholderResId;
            return this;
        }

        /**
         * 加载图片的分辨率
         */
        public ConfigBuilder override(int oWidth, int oHeight) {
            this.oWidth = ImageUtil.dip2px(oWidth);
            this.oHeight = ImageUtil.dip2px(oHeight);
            return this;
        }

        /**
         * 圆形
         */
        public ConfigBuilder asCircle() {
            this.shapeMode = ShapeMode.OVAL;
            return this;
        }

        /**
         * 圆角矩形
         */
        public ConfigBuilder roundCorner(int roundRadius) {
            this.rectRoundRadius = ImageUtil.dip2px(roundRadius);
            this.shapeMode = ShapeMode.RECT_ROUND;
            return this;
        }

        public ConfigBuilder skipMemory(boolean skipMemory) {
            this.skipMemory = skipMemory;
            return this;
        }

    }
}
