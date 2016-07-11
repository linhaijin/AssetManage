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
public class BasicMsgFragment extends BaseFragment{
    private TextView tvName;
    private TextView tvNumber;
    private TextView tvBrand;
    private TextView tvStatus;
    @Override
    protected void setContentView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_basic_msg,null);
    }

    @Override
    public void findView() {
        tvName = (TextView) rootView.findViewById(R.id.name);
        tvNumber = (TextView) rootView.findViewById(R.id.number);
        tvBrand = (TextView) rootView.findViewById(R.id.brand);
        tvStatus = (TextView) rootView.findViewById(R.id.status);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void underCreate() {
        Bundle data = getArguments();
        String number  = data.getString("number");
        String name = data.getString("name");
        String brand = data.getString("brand");
        String status = data.getString("status");
        tvNumber.setText(number);
        tvBrand.setText(brand);
        tvName.setText(name);
        tvStatus.setText(status);

    }
}
