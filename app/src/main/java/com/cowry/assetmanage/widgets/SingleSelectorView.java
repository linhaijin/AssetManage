package com.cowry.assetmanage.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.cowry.assetmanage.R;
import com.cowry.assetmanage.adapter.SingleSelectorAdp;

import java.util.List;

/**
 * Created by acer on 2016/5/30.
 * 自定义的一个用于下方弹出的单项选择器
 */
public class SingleSelectorView {
    private Context context;
    private PopupWindow window;
    private View mainView;
    private SingleSelectorAdp adp;
    private onSelectorListener onSelectorListener;

    public SingleSelectorView(Context context, View mainView) {
        this.context = context;
        this.mainView = mainView;
        init();
    }

    public void init(){
        View view = LayoutInflater.from(context).inflate(R.layout.view_single_selector,null,false);
        window = new PopupWindow(view, GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT, true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        window.setBackgroundDrawable(dw);
        window.setOutsideTouchable(true);
        ListView listView = (ListView) view.findViewById(R.id.lv_select);
        adp = new SingleSelectorAdp(context);
        listView.setAdapter(adp);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onSelectorListener.onSelectorClick(position);
                window.dismiss();
            }
        });
        window.setAnimationStyle(R.style.popwin_anim_style);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                MyUtil.backgroundAlpha((Activity) context, 1.0f);
            }
        });
        //防止被华为底部虚拟键挡住
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public void showSelector(List<String> strings,String s){
        if (!window.isShowing()){
            adp.refresh(strings, s);
            window.showAtLocation(mainView, Gravity.BOTTOM, 0, 0);
            MyUtil.backgroundAlpha((Activity) context, 0.7f);
        }
    }

    public void setOnSelectorListener(onSelectorListener listener){
        this.onSelectorListener = listener;
    }

    /**
     * 选项被点击的监听器
     */
    public interface onSelectorListener{
        void onSelectorClick(int position);
    }


}
