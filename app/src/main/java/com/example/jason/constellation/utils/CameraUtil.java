package com.example.jason.constellation.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CameraUtil {

    private final static String TAG = "CameraUtil";

    private final static String RAW = "rawData";

    private static CameraUtil cameraUtil;

    private Thread thread = null;

    private int width, height = 0;

    private List<byte[]> frameList = new ArrayList<byte[]>();

    private RecordRunnable recordRunnable;

    private boolean isPictureNeedUpdate;

    private byte[] pictureBytes;

    private List<String> segments = new ArrayList<String>();

    public String filePath;

    public RECORD_STATUS recordStatus;

    public enum RECORD_STATUS {
        STOP,
        PAUSE,
        RECORD,
        FINISH
    }

    private class RecordRunnable implements Runnable {

        public String getPath() {
            return path;
        }

        public void setPath(String filePath) {
            this.path = filePath;
        }

        private String path;

        @Override
        public void run() {
            if (!TextUtils.isEmpty(path)) {
                File file = new File(path);
                if (file != null) {
                    try {
                        FileOutputStream outputStream = new FileOutputStream(file);
                        while (frameList != null && (recordStatus == RECORD_STATUS.RECORD)) {
                            if (frameList.size() <= 0) {
                                Thread.yield();
                            }
                            //write to file
                            if (frameList.size() > 0) {
                                byte[] srcBytes = frameList.get(0);
                                frameList.remove(0);
                                if ((srcBytes != null) && (srcBytes.length > 0) && (width > 0) && (height > 0)) {
                                    byte[] destBytes = new byte[srcBytes.length];
                                    YUVUtils.rotateYUV420Degree90(srcBytes, destBytes, width, height);
                                    outputStream.write(destBytes);
                                }
                            }
                        }
                        outputStream.flush();
                        outputStream.close();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } finally {
                        recordStatus = RECORD_STATUS.STOP;
                        frameList.clear();
                    }
                }
            }
        }
    }

    private void CameraUtil() {
    }

    public static synchronized CameraUtil getInstance() {
        if (cameraUtil == null) {
            cameraUtil = new CameraUtil();
            cameraUtil.recordStatus = RECORD_STATUS.STOP;
            cameraUtil.isPictureNeedUpdate = false;
        }
        return cameraUtil;
    }

    public byte[] getPictureBytes() {
        return pictureBytes;
    }

    public void setPictureBytes(byte[] pictureBytes) {
        this.pictureBytes = pictureBytes;
    }

    public String getFilePath() {
        return filePath;
    }

    public void startRecord(Context context) {
        recordStatus = RECORD_STATUS.RECORD;
        isPictureNeedUpdate = true;
        recordRunnable = null;
        recordRunnable = new RecordRunnable();
        String filePathPartial = StorageUtil.getRawCachePath(context);
        if (!TextUtils.isEmpty(filePathPartial)) {
            segments.add(filePathPartial);
            recordRunnable.setPath(filePathPartial);
            thread = new Thread(recordRunnable);
            thread.start();
        }
    }

    public void stopRecord() {
        recordStatus = RECORD_STATUS.STOP;
    }

    public void finishRecord() {
        recordStatus = RECORD_STATUS.FINISH;
    }

    public void deleteLastSegment() {
        if (segments != null && segments.size() > 0) {
            int lastSegment = segments.size() - 1;
            String path = segments.get(lastSegment);
            if (!TextUtils.isEmpty(path)) {
                File file = new File(path);
                if (file.isFile()) {
                    file.delete();
                    segments.remove(lastSegment);
                }
            }
        }
    }

    public void clearRawFolder(Context context) {
        String rawFolder = StorageUtil.getRawFolder(context);
        if (!TextUtils.isEmpty(rawFolder)) {
            File rawFolderFile = new File(rawFolder);
            if (rawFolderFile.isDirectory()) {
                for (File file : rawFolderFile.listFiles()) {
                    file.delete();
                }
            }
        }
    }

    public void mergeFile(Context context) {
        String rawDataFolder = StorageUtil.getRawCacheFolder(context);
        if (!TextUtils.isEmpty(rawDataFolder)) {
            File dir = new File(rawDataFolder);
            if (dir.isDirectory() && dir.listFiles() != null && dir.listFiles().length > 0) {
                String mergedFilePath = StorageUtil.getFormatRawPath(context);
                if (!TextUtils.isEmpty(mergedFilePath)) {
                    File mergedFile = new File(mergedFilePath);
                    if (mergedFile != null) {
                        clearRawFolder(context);//清除以前的旧数据
                        File[] files = dir.listFiles();
                        FileOutputStream fileOutputStream = null;
                        FileInputStream fileInputStream = null;
                        try {
                            fileOutputStream = new FileOutputStream(mergedFile);
                            for (File file : files) {
                                if (file.exists()) {
                                    fileInputStream = new FileInputStream(file);

                                    byte[] data = new byte[2048];
                                    int length = data.length;
                                    while (fileInputStream.read(data) != -1) {
                                        fileOutputStream.write(data, 0, length);
                                    }
                                    fileInputStream.close();
                                    file.delete();//delete file after merged
                                }
                            }
                            fileOutputStream.flush();
                            fileOutputStream.close();
                            filePath = mergedFilePath;
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            filePath = null;
                        } finally {
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void storeFrame(byte[] frame, int rawDataWidth, int rawDataHeight) {
        if (isPictureNeedUpdate && getPictureData(frame)) {
            isPictureNeedUpdate = false;
        }
        if (recordStatus == RECORD_STATUS.RECORD) {
            if (width != rawDataWidth || height != rawDataHeight) {
                width = rawDataWidth;
                height = rawDataHeight;
            }
            byte[] bytes = new byte[frame.length];
            System.arraycopy(frame, 0, bytes, 0, frame.length);
            frameList.add(bytes);
        }
    }

    private boolean getPictureData(byte[] frame) {
        if (frame != null && frame.length > 0) {
            pictureBytes = new byte[frame.length];
            System.arraycopy(frame, 0, pictureBytes, 0, frame.length);
            return true;
        }
        return false;
    }

    public Bitmap getThumbnailBitmap(int width, int height, int angle) {
        Bitmap bitmap = null;
        if ((width > 0) && (height > 0) && (pictureBytes != null) && (pictureBytes.length > 0)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            YuvImage yuvImage = new YuvImage(pictureBytes, ImageFormat.NV21, width, height, null);
            yuvImage.compressToJpeg(new Rect(0, 0, width, height), 50, outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            Bitmap srcBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            bitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
            srcBitmap.recycle();
        }
        return bitmap;
    }
}
