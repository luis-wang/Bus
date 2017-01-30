package com.scrat.app.bus;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.scrat.app.bus.utils.Utils;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

/**
 * Created by yixuanxuan on 16/4/19.
 */
public class BusApp extends Application {
    private static BusApp mApp;

    public static Context getContext() {
        return mApp.getApplicationContext();
    }

    private static final String APP_ID = "2882303761517474662";
    private static final String APP_KEY = "5551747492662";

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        if (shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
            String alias = Utils.getAndroidID(mApp.getApplicationContext());
            MiPushClient.setAlias(mApp.getApplicationContext(), alias, null);
        }
        com.squareup.leakcanary.LeakCanary.install(this);
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
