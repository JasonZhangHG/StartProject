package com.example.jason.constellation.utils;

import java.security.MessageDigest;


public class MD5Util {
    /**
     * 字符串md5编码
     *
     * @param string
     * @return
     */
    public static String md5Encode(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10) {
                    hex.append("0");
                    hex.append(Integer.toHexString(b & 0xFF));
                }
            }
            return hex.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return string;
        }
    }
}
