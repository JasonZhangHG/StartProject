package com.example.jason.constellation.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;


public class GsonUtil {


    private final static Gson gson = new Gson();

    public static <T> T fromJson(String json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }

    public static <T> T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    public static <T> T fromObject(Object obj, Class<T> tClass) {
        String json = toJson(obj);
        return fromJson(json, tClass);
    }
}
