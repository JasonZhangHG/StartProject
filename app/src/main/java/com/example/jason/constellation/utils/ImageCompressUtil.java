package com.example.jason.constellation.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ImageCompressUtil {

    public static Bitmap compressScale(Bitmap image, float width, float height) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        // 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
        if (baos.toByteArray().length / 1024 > 1024) {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        }
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        int w = image.getWidth();
        int h = image.getHeight();
        int be = 1;// be=1表示不缩放
        if (w > h && w > width) {
            be = (int) (w / width);
        } else if (w < h && h > height) {
            be = (int) (h / height);
        }
        if (be <= 0) {
            be = 1;
        }
        newOpts.inSampleSize = be; // 设置缩放比例
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        image.recycle();

        return bitmap;
    }

}
