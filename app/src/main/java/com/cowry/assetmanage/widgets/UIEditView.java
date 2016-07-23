package com.cowry.assetmanage.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.cowry.assetmanage.R;

/**
 * Created by xuliangchun on 2016/6/18.
 * 输入对话框
 */
public class UIEditView extends Dialog {

    private Context context;
    private String title;
    private String buttonLeftText;
    private String buttonRightText;
    private ClickListenerInterface clickListenerInterface;
    private EditText tvMessage;

    public UIEditView(Context context, String title,
                      String buttonLeftText, String buttonRightText) {
        super(context, R.style.UIAlertViewStyle);

        this.context = context;
        this.title = title;
        this.buttonLeftText = buttonLeftText;
        this.buttonRightText = buttonRightText;
    }

    public interface ClickListenerInterface {
        void doRight();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ui_edit_view, null);
        setContentView(view);

        tvMessage = (EditText) view.findViewById(R.id.etMessage);
        TextView tvLeft = (TextView) view.findViewById(R.id.tvBtnLeft);
        TextView tvRight = (TextView) view.findViewById(R.id.tvBtnRight);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        if ("".equals(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }
        tvLeft.setText(buttonLeftText);
        tvRight.setText(buttonRightText);

        tvLeft.setOnClickListener(new clickListener());
        tvRight.setOnClickListener(new clickListener());

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();

        lp.width = (int) (d.widthPixels * 0.8);
        dialogWindow.setAttributes(lp);
    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            int id = v.getId();
            switch (id) {
                case R.id.tvBtnLeft:
                    dismiss();
                    break;
                case R.id.tvBtnRight:
                    if (TextUtils.isEmpty(tvMessage.getText())){
                        ToastUtils.getInstance().showToast("请输入授权码");
                        return;
                    }
                    if (tvMessage.getText().length()<8){
                        ToastUtils.getInstance().showToast("授权码不得少于8位");
                        return;
                    }

                    clickListenerInterface.doRight();
                    dismiss();
                    break;
                default:
                    break;
            }
        }
    }

}
