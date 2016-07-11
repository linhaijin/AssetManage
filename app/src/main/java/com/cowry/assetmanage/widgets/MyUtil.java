package com.cowry.assetmanage.widgets;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by acer on 2016/6/11.
 */
public class MyUtil {
    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public static void backgroundAlpha(Activity activity,float bgAlpha)
    {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }
    /**
     *  此方法只是关闭软键盘
     */

    public static void hintKbTwo(Activity activity) {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()&&activity.getCurrentFocus()!=null){
            if (activity.getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
