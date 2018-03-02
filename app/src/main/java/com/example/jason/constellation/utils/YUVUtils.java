package com.example.jason.constellation.utils;


import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.nio.ByteBuffer;

public class YUVUtils {

    public static void rotateYUV420Degree90(byte[] srcData, byte[] destData, int imageWidth, int imageHeight) {
        // Rotate the Y luma
        int i = 0;
        for (int x = 0; x < imageWidth; x++) {
            for (int y = imageHeight - 1; y >= 0; y--) {
                destData[i] = srcData[y * imageWidth + x];
                i++;
            }
        }
        // Rotate the U and V color components
        i = imageWidth * imageHeight * 3 / 2 - 1;
        for (int x = imageWidth - 1; x > 0; x = x - 2) {
            for (int y = 0; y < imageHeight / 2; y++) {
                destData[i] = srcData[(imageWidth * imageHeight) + (y * imageWidth) + x];
                i--;
                destData[i] = srcData[(imageWidth * imageHeight) + (y * imageWidth) + (x - 1)];
                i--;
            }
        }
    }

    public static void nv21To420SP(byte[] data, int width, int height) {
        //NV21  YYYYYYYY VUVU
        //420SP YYYYYYYY UVUV
        int total = width * height;
        byte mTemp;
        for (int i = total; i < data.length; i += 2) {
            mTemp = data[i];
            data[i] = data[i + 1];
            data[i + 1] = mTemp;
        }
    }

    private byte[] tmpImageData;
    private byte[] tmpRowData;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public byte[] yUV_420_888toNV21(Image image) {

        Rect crop = image.getCropRect();
        int format = image.getFormat();
        int width = crop.width();
        int height = crop.height();

        Image.Plane[] planes = image.getPlanes();

        int totalSize = width * height * ImageFormat.getBitsPerPixel(format) / 8;
        if (tmpImageData == null || tmpImageData.length < totalSize) {
            tmpImageData = new byte[totalSize];
        }

        int rowSize = planes[0].getRowStride();
        if (tmpRowData == null || tmpRowData.length < rowSize) {
            tmpRowData = new byte[rowSize];
        }

        int channelOffset = 0;
        int outputStride = 1;
        for (int i = 0; i < planes.length; i++) {
            switch (i) {
                case 0:
                    channelOffset = 0;
                    outputStride = 1;
                    break;
                case 1:
                    channelOffset = width * height + 1;
                    outputStride = 2;
                    break;
                case 2:
                    channelOffset = width * height;
                    outputStride = 2;
                    break;
                default:
                    break;
            }
            ByteBuffer buffer = planes[i].getBuffer();
            int rowStride = planes[i].getRowStride();
            int pixelStride = planes[i].getPixelStride();

            int shift = (i == 0) ? 0 : 1;
            int w = width >> shift;
            int h = height >> shift;
            buffer.position(rowStride * (crop.top >> shift) + pixelStride * (crop.left >> shift));
            for (int row = 0; row < h; row++) {
                int length;
                if (pixelStride == 1 && outputStride == 1) {
                    length = w;
                    buffer.get(tmpImageData, channelOffset, length);
                    channelOffset += length;
                } else {
                    length = (w - 1) * pixelStride + 1;
                    buffer.get(tmpRowData, 0, length);
                    for (int col = 0; col < w; col++) {
                        tmpImageData[channelOffset] = tmpRowData[col * pixelStride];
                        channelOffset += outputStride;
                    }
                }
                if (row < h - 1) {
                    buffer.position(buffer.position() + rowStride - length);
                }
            }
        }
        return tmpImageData;
    }
}

