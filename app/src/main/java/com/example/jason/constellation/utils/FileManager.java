package com.example.jason.constellation.utils;


import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * 1. 用于统计缓存占用的空间,清除缓存
 * 2. 使用的第三方库
 *    网络、图片加载工具的文件缓存地址都用这个
 *    方便管理
 *
 * DiskLruCache
 *
 * LinkedHashMap   LRU
 *
 */
public class FileManager {


    public static File getCacheFloder(Context context){
        File root;
        if(Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED)){
            //sdcard/android/data/包名/cache
            root=context.getExternalCacheDir();
        }else {
            //data/data/包名/cache
            root=context.getCacheDir();
        }
        return root;
    }


    public static File getImageCacheFloder(Context context){
        File root=getCacheFloder(context);
        return new File(root,"img");
    }

    public static File getUrlCacheFloder(Context context){
        File root=getCacheFloder(context);
        return new File(root,"url");
    }

}
