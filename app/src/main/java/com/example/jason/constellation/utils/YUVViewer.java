package com.example.jason.constellation.utils;

import android.text.TextUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;


public class YUVViewer {

    private static final String TAG = "YUVViewer";

    public static final int ERROR_EXCEPTION = -1;
    public static final int ERROR_INVALID_FPS = -2;// fps = 0 或者其它不合理情况

    public interface OnYUVDataListener {
        public void onYuvImageCallback(int index, byte[] yuvData, int width, int height);

        public void onYuvImageListCallback(List<byte[]> yuvDataList, int width, int height);

        public void onYuvThumbnailBitmapCallback(byte[] thumbnailData, int width, int height);
    }

    public interface OnYUVPlayListener {
        public void onFinish();

        public void onError(int errorCode);

        public void onStart();

        public void onPause();

        public void onStop();
    }

    public enum PLAY_MODE {
        MANUAL,
        AUTO,
        RECYCLE
    }

    public enum PLAY_STATUS {
        STOPPED,
        PAUSED,
        PLAYING
    }

    private String filePath = null;
    private int width = 0;
    private int height = 0;
    private float fps;
    private int playIndex = 0;
    private PLAY_MODE playMode;
    private PLAY_STATUS playStatus = PLAY_STATUS.STOPPED;
    private long totalFrames = 0;
    private long frameSize = 0;
    private Thread autoPlayThread = null;

    private OnYUVDataListener onYUVDataListener;
    private OnYUVPlayListener onYUVPlayListener;
    RandomAccessFile randomAccessFile = null;

    public YUVViewer(String filePath, int width, int height) {
        this.filePath = filePath;
        this.width = width;
        this.height = height;
        this.frameSize = (long) (width * height * 1.5);
    }

    public YUVViewer(String filePath, int width, int height, float fps) {
        this.filePath = filePath;
        this.width = width;
        this.height = height;
        this.frameSize = (long) (width * height * 1.5);
        this.fps = fps;
    }

    public PLAY_MODE getPlayMode() {
        return playMode;
    }

    public void setPlayMode(PLAY_MODE playMode) {
        this.playMode = playMode;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public OnYUVDataListener getOnYUVDataListener() {
        return onYUVDataListener;
    }

    public void setOnYUVDataListener(OnYUVDataListener onYUVDataListener) {
        this.onYUVDataListener = onYUVDataListener;
    }

    public OnYUVPlayListener getOnYUVPlayListener() {
        return onYUVPlayListener;
    }

    public void setOnYUVPlayListener(OnYUVPlayListener onYUVPlayListener) {
        this.onYUVPlayListener = onYUVPlayListener;
    }

    public long getTotalFrames() {
        return totalFrames;
    }

    public void play(PLAY_MODE mode) {
        playMode = mode;
        playStatus = PLAY_STATUS.PLAYING;
        openFile();
        autoPlayThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (randomAccessFile != null) {
                    if (onYUVPlayListener != null) {
                        onYUVPlayListener.onStart();
                    }
                    try {
                        byte[] tempData = null;
                        while ((playStatus == PLAY_STATUS.PLAYING) && (playMode != PLAY_MODE.MANUAL) && (fps > 0)) {
                            if (playMode == PLAY_MODE.AUTO) {
                                if (playIndex >= totalFrames) {
                                    break;
                                }
                            } else if (playMode == PLAY_MODE.RECYCLE) {
                                if (playIndex >= totalFrames) {
                                    playIndex = 0;
                                }
                            }
                            synchronized (this) {
                                randomAccessFile.seek(playIndex * frameSize);
                                tempData = new byte[(int) frameSize];
                                randomAccessFile.read(tempData);
                            }
                            if (onYUVDataListener != null) {
                                onYUVDataListener.onYuvImageCallback(playIndex, tempData, width, height);
                            }
                            try {
                                if (fps > 0) {
                                    Thread.sleep((long)(1000 / fps));
                                } else {
                                    if (onYUVPlayListener != null) {
                                        onYUVPlayListener.onError(ERROR_INVALID_FPS);
                                    }
                                    playStatus = PLAY_STATUS.STOPPED;
                                    playIndex = 0;
                                }
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                                playIndex = 0;
                                playStatus = PLAY_STATUS.STOPPED;
                                if (onYUVPlayListener != null) {
                                    onYUVPlayListener.onError(ERROR_EXCEPTION);
                                }
                            }
                            playIndex++;
                        }
                        if (onYUVPlayListener != null) {
                            onYUVPlayListener.onFinish();
                        }
                        playIndex = 0;
                        playStatus = PLAY_STATUS.STOPPED;
                    } catch (IOException ex) {
                        playIndex = 0;
                        ex.printStackTrace();
                        if (onYUVPlayListener != null) {
                            onYUVPlayListener.onError(ERROR_EXCEPTION);
                        }
                        if (onYUVDataListener != null) {
                            onYUVDataListener.onYuvImageCallback(playIndex, null, width, height);
                        }
                        playStatus = PLAY_STATUS.STOPPED;
                    }
                } else {
                    playIndex = 0;
                    playStatus = PLAY_STATUS.STOPPED;
                    if (onYUVPlayListener != null) {
                        onYUVPlayListener.onError(ERROR_EXCEPTION);
                    }
                    if (onYUVDataListener != null) {
                        onYUVDataListener.onYuvImageCallback(playIndex, null, width, height);
                    }
                }
            }
        });
        autoPlayThread.start();
    }

    public void pause() {
        playStatus = PLAY_STATUS.PAUSED;
        if (onYUVPlayListener != null) {
            onYUVPlayListener.onPause();
        }
    }

    public void stop() {
        playIndex = 0;
        playStatus = PLAY_STATUS.STOPPED;
        if (onYUVPlayListener != null) {
            onYUVPlayListener.onStop();
        }
    }

    public void getThumbnailBitmap(final int index) {
        openFile();
        if (index < totalFrames) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (randomAccessFile != null) {
                        try {
                            byte[] tempData = null;
                            synchronized (this) {
                                randomAccessFile.seek(index * frameSize);
                                tempData = new byte[(int) frameSize];
                                randomAccessFile.read(tempData);
                            }
                            if (onYUVDataListener != null) {
                                onYUVDataListener.onYuvThumbnailBitmapCallback(tempData, width, height);
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            if (onYUVDataListener != null) {
                                onYUVDataListener.onYuvThumbnailBitmapCallback(null, width, height);
                            }
                        }
                    } else {
                        if (onYUVDataListener != null) {
                            onYUVDataListener.onYuvThumbnailBitmapCallback(null, width, height);
                        }
                    }
                }
            }).start();
        }
    }

    public void getYUVData(final int index) {
        if (index < totalFrames) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (randomAccessFile != null) {
                        try {
                            byte[] tempData = null;
                            synchronized (this) {
                                randomAccessFile.seek(index * frameSize);
                                tempData = new byte[(int) frameSize];
                                randomAccessFile.read(tempData);
                            }
                            if (onYUVDataListener != null) {
                                onYUVDataListener.onYuvImageCallback(index, tempData, width, height);
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            if (onYUVDataListener != null) {
                                onYUVDataListener.onYuvImageCallback(index, null, width, height);
                            }
                        }
                    } else {
                        if (onYUVDataListener != null) {
                            onYUVDataListener.onYuvImageCallback(index, null, width, height);
                        }
                    }
                }
            }).start();
        }
    }

    public void getYUVDataList(final int listSize) {
        if (listSize > 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (randomAccessFile != null) {
                        List<byte[]> yuvDataList = new ArrayList<byte[]>();
                        int step = (int) ((totalFrames - 1) / listSize);
                        if (step > 0) {
                            try {
                                synchronized (this) {
                                    for (int i = 0; i < totalFrames; i += step) {
                                        byte[] tempData = new byte[(int) frameSize];
                                        randomAccessFile.seek(i * frameSize);
                                        randomAccessFile.read(tempData);
                                        yuvDataList.add(tempData);
                                    }
                                }
                                if (onYUVDataListener != null) {
                                    if ((yuvDataList != null) && (yuvDataList.size() > 0)) {
                                        onYUVDataListener.onYuvImageListCallback(yuvDataList, width, height);
                                    } else {
                                        onYUVDataListener.onYuvImageListCallback(null, width, height);
                                    }
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                                if (onYUVDataListener != null) {
                                    onYUVDataListener.onYuvImageListCallback(null, width, height);
                                }
                            }
                        } else {
                            if (onYUVDataListener != null) {
                                onYUVDataListener.onYuvImageListCallback(null, width, height);
                            }
                        }
                    } else {
                        if (onYUVDataListener != null) {
                            onYUVDataListener.onYuvImageListCallback(null, width, height);
                        }
                    }
                }
            }).start();
        }
    }

    public void openFile() {
        if (!TextUtils.isEmpty(filePath) && (randomAccessFile == null)) {
            try {
                randomAccessFile = new RandomAccessFile(filePath, "r");
                if (frameSize > 0) {
                    totalFrames = randomAccessFile.length() / frameSize;
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                randomAccessFile = null;
            } catch (IOException ex) {
                ex.printStackTrace();
                randomAccessFile = null;
            }
        }
    }

    public void closeFile() {
        if (randomAccessFile != null) {
            try {
                randomAccessFile.close();
                randomAccessFile = null;
                totalFrames = 0;
            } catch (IOException ex) {
                ex.printStackTrace();
                randomAccessFile = null;
            }
        }
    }

}
