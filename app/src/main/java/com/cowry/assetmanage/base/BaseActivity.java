package com.cowry.assetmanage.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cowry.assetmanage.R;
import com.cowry.assetmanage.app.CWApplication;
import com.cowry.assetmanage.widgets.WaitDialog;

/**
 * Created by acer on 2016/6/23.
 */
public abstract class BaseActivity extends FragmentActivity {
    public WaitDialog proDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        CWApplication.getInstance().pushActivity(this);
        setContentView(setLayout());
        findView();
        setListener();
        underCreate();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        CWApplication.getInstance().popActivity(this);
        if (proDialog != null) {
            proDialog.dismiss();
        }
    }

    /**
     * 关闭当前activity
     * @param view 视图
     */
    public void onBack(View view) {
        finish();
    }

    /**
     * 设置标题
     * @param title 文字
     */
    public void setAppTitle(CharSequence title) {
        ((TextView)findViewById(R.id.tv_title)).setText(title);

    }
    /**
     * 设置上下文布局
     */
    public abstract int setLayout();

    /**
     * 初始化控件
     */
    public abstract void findView();

    /**
     * 设置监听器
     */
    public abstract void setListener();

    /**
     * onCreate()事件处理
     */
    public abstract void underCreate();
    /**
     * 初始化等待框
     */
    private void initProDialog() {
        proDialog =new WaitDialog(this, R.anim.frame);
        proDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 显示等待动画
     */
    public void showWaiting(){
        if (proDialog==null){
            initProDialog();
        }
        proDialog.show();
    }

    /**
     * 关闭等待动画
     */
    public void cancelWaiting() {
        if (proDialog!=null&&proDialog.isShowing()){
            proDialog.dismiss();
        }
    }

}

