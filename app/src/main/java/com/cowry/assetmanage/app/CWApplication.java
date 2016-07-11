package com.cowry.assetmanage.app;

import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by xuliangchun on 2016/6/24.
 */
public class CWApplication extends Application {
    /** Activity管理 */
    private static List<Activity> mActivityList = new LinkedList<Activity>();
    private static CWApplication  instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setResolution();//首次启动应用初始化分辨率
    }
    public static CWApplication getInstance() {
        return instance;
    }

    private void setResolution(){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Resolution resolution = new Resolution(metrics.widthPixels,metrics.heightPixels,metrics.density);
    }

    /**
     * 添加一个 activity到数组中
     */
    public void pushActivity(Activity activity) {
        mActivityList.add(activity);
    }

    /**
     *去除当前数组的中的一个activity
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            mActivityList.remove(activity);
        }
    }

    /**
     * 关闭数组中名字是itName以外的activity
     * @param itName
     */
    public void closeOtherActivities(String itName) {
        for (Activity iter : mActivityList) {
            if (null != iter) {
                String activityName = iter.getClass().getSimpleName();

                if (!activityName.equalsIgnoreCase(itName))
                    iter.finish();
            }
        }
    }


    /**
     *  关闭所有的 activity
     */
    public void killAllActivity() {
        for (Activity iter : mActivityList) {
            if (null != iter)
                iter.finish();
        }
    }
}
