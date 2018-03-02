package com.example.jason.constellation.utils;

import android.graphics.Bitmap;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import java.io.File;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class MediaUtil {
    public static final String TAG = MediaUtil.class.getName();

    @Nullable
    public static String getDuration(@Nullable String path) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(path);
            return retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            retriever.release();
        }
    }


    // 计算文件的大小
    public static float getTotalSizeOfFilesInDir(final File file) {
        long kb = 1024;
        long mb = kb * 1024;
        if (file.isFile()) {
            return ((file.length()) / mb);
        }
        final File[] children = file.listFiles();
        long total = 0;
        if (children != null) {
            for (final File child : children) {
                total += getTotalSizeOfFilesInDir(child);
            }
        }
        return (float) (total / mb);
    }

    @Nullable
    public static String getWidth(String path) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(path);
            return retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            retriever.release();
        }
    }

    @Nullable
    public static String getHeight(String path) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(path);
            return retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            retriever.release();
        }
    }

    public static Bitmap createVideoThumbnail(String path, long timeUs) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(path);
            return retriever.getFrameAtTime(timeUs, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            retriever.release();
        }
    }

    public static boolean isVideoPortrait(String videoPath) {
        MediaExtractor mediaExtractor = new MediaExtractor();
        try {
            mediaExtractor.setDataSource(videoPath);
            MediaFormat format = mediaExtractor.getTrackFormat(selectTrack(mediaExtractor));
            Integer rotation = format.getInteger(MediaFormat.KEY_ROTATION);
            if (rotation != null && rotation == 90) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    private static int selectTrack(MediaExtractor extractor) {
        int numTracks = extractor.getTrackCount();
        for (int i = 0; i < numTracks; i++) {
            MediaFormat format = extractor.getTrackFormat(i);
            String mime = format.getString(MediaFormat.KEY_MIME);
            if (mime.startsWith("video/")) {
                return i;
            }
        }
        return -1;
    }

}