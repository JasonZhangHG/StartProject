package com.example.jason.constellation.base;

import android.app.Activity;
import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.example.jason.constellation.utils.SharePreferenceUtil;
import com.example.jason.constellation.utils.ToastHelper;

import java.util.ArrayList;


public class CCApplication extends Application {

    private static CCApplication app;

    private ArrayList<Activity> activitys = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        SharePreferenceUtil.initInstance(getApplicationContext(), SharePreferenceUtil.MODE_ENCRYPT_ALL);
        ToastHelper.init(this);
        Utils.init(this);
    }


    public void addActivity(Activity activity) {
        if (!activitys.contains(activity)) {
            activitys.add(activity);
        }
    }

    public void removeActiivty(Activity activity) {
        if (activitys.contains(activity)) {
            activitys.remove(activity);
        }
    }

    public void exit() {
        for (Activity a : activitys) {
            a.finish();
        }
    }

    public static CCApplication getInstance() {
        return app;
    }

}
