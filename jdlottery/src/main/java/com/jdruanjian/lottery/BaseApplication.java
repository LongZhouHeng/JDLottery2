package com.jdruanjian.lottery;

import java.io.File;
import java.util.Set;

import com.android.volley.toolbox.Volley;
import com.hs.nohttp.Logger;
import com.hs.nohttp.NoHttp;
import com.hs.nohttp.rest.RequestQueue;
import com.jdruanjian.lottery.utils.CrashHandler;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import cn.jpush.android.api.JPushInterface;


public class BaseApplication extends Application {
    // 缓存存放地址
    public static final String CHACHE_FILE = "travel_data";
    private final static int MEMORY_CACHE_SIZE = 10 * 1024 * 1024;
    private final static int MAX_IMG_WIDTH = 480;
    private final static int MAX_IMG_HEIGHT = 800;
    private final static int DISC_CACHE_SIZE = 50 * 1024 * 1024;
    private final static int DISC_CACHE_FILE_COUNT = 100;
    private final static int CONNECT_TIMEOUT = 5 * 1000;
    private final static int READ_TIMEOUT = 30 * 1000;
    private static BaseApplication instance;
    public static com.android.volley.RequestQueue queue;
    private SharedPreferences mPrefs;

    private IWXAPI api;
    // 默认图片属性
    private DisplayImageOptions mDisplayDefaultOptions;
    // 请求队列
    private RequestQueue mRequestQueue;
    // 版本名
    private String versionName;
    // imei
    private String imei;
    private Context mContext;
    public static BaseApplication getInst() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //JPushInterface.setDebugMode(true);
   //     JPushInterface.init(this);
        UMShareAPI.get(this);//初始化sdk
   //     Config.DEBUG = false;//可以弹出对话框告诉我们什么地方出错了，不写这句话的话，要费时间找bug的
        queue = Volley.newRequestQueue(getApplicationContext()); // 实例化RequestQueue对象
        instance = this;
        mPrefs = this.getSharedPreferences(CHACHE_FILE, Context.MODE_PRIVATE);
        try {
            versionName = this.getPackageManager().getPackageInfo(
                    this.getPackageName(), PackageManager.GET_CONFIGURATIONS).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        NoHttp.initialize(instance);
        Logger.setTag(Constants.TAG);
        Logger.setDebug(Constants.DEVELOPER_MODE);// 开始NoHttp的调试模式,
        // 这样就能看到请求过程和日志
        // 捕捉程序崩溃
        CrashHandler.getInstance().init(getApplicationContext(), versionName);
        // 初始化ImageLoader
        initImageLoader();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    {
        PlatformConfig.setWeixin("wxcc915e0f91f89504 ","e1e88b0f1183e470f381bd77077f80c4 ");
        PlatformConfig.setQQZone("1106444980","2mF5UTpkJOVT51WD");
    }
    public static com.android.volley.RequestQueue getHttpQueue() {
        return queue;
    }
    /**
     * 请求队列
     * */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
            mRequestQueue = NoHttp.newRequestQueue();
        return mRequestQueue;
    }

    /**
     * 唯一标志
     * */
    @SuppressLint("MissingPermission")
    public String getDeviceId() {
        if (TextUtils.isEmpty(imei)) {
            TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            imei = TelephonyMgr.getDeviceId();
        }
        return imei;
    }

    public DisplayImageOptions getDisplayImageOptions() {
        if (mDisplayDefaultOptions == null) {
            mDisplayDefaultOptions = new DisplayImageOptions.Builder()
                    .displayer(new FadeInBitmapDisplayer(400))
                    //  .showStubImage(R.drawable.icon)
                    .showImageForEmptyUri(R.drawable.bg_default)
                    .cacheInMemory(true).cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.ARGB_8888).build();
        }
        return mDisplayDefaultOptions;
    }

    // 配置ImageLoader
    private void initImageLoader() {

        File cacheDir = StorageUtils.getOwnCacheDirectory(
                getApplicationContext(), Constants.IMAGE_CACHE_SDCARD_PATH);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .memoryCacheExtraOptions(MAX_IMG_WIDTH, MAX_IMG_HEIGHT)
                .threadPoolSize(3)
                // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(MEMORY_CACHE_SIZE))
                .memoryCacheSize(MEMORY_CACHE_SIZE)
                .discCacheSize(DISC_CACHE_SIZE)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(DISC_CACHE_FILE_COUNT) // 缓存的文件数量
                .discCache(new UnlimitedDiscCache(cacheDir)) // 自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(
                        new BaseImageDownloader(this, CONNECT_TIMEOUT,
                                READ_TIMEOUT)).build();
        ImageLoader.getInstance().init(config);// 全局初始化此配置
    }

    /**
     * 当前应用的文件缓存SharedPreferences引用
     * */
    public SharedPreferences getAppPreferences() {
        return mPrefs;
    }

    /**
     * 默认图片的加载配置
     * */
    public final DisplayImageOptions getDefaultDisplayImageOptions() {
        return mDisplayDefaultOptions;
    }

    /**
     * 新安装应用或者更新版本时，需要显示导航页面一次
     * */
    public boolean showGuide() {
        String version = mPrefs.getString(Constants.CURRENT_VERSION, "");
        return !versionName.equals(version);
    }

    /**
     * 导航页面启动过一次之后，不在显示，除非更新版本。
     * */
    public void finishGuide() {
        mPrefs.edit().putString(Constants.CURRENT_VERSION, versionName)
                .commit();
    }

    /**
     * 获取版本名
     * */
    public String getVersionName() {
        return versionName;
    }
}