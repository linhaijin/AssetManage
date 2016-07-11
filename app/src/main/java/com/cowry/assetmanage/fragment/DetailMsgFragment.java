package com.cowry.assetmanage.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cowry.assetmanage.R;
import com.cowry.assetmanage.base.BaseFragment;

/**
 * Created by acer on 2016/6/27.
 */
public class DetailMsgFragment extends BaseFragment{
    private TextView tvStatus;
    @Override
    protected void setContentView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_detail_msg,null);
    }

    @Override
    public void findView() {
        tvStatus = (TextView) rootView.findViewById(R.id.tvStatus);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void underCreate() {
        Bundle data = getArguments();
        String status = data.getString("status");
        tvStatus.setText(status);
    }
}
