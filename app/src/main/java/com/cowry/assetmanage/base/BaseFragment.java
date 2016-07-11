package com.cowry.assetmanage.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by acer on 2016/6/23.
 */
public abstract class BaseFragment extends Fragment {
    public View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            setContentView(inflater, container);
            findView();
            setListener();
            underCreate();
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，
            // 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    /**
     * 设置上下文布局
     */
    protected abstract void setContentView(LayoutInflater inflater, ViewGroup container);

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
}
