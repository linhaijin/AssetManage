package com.cowry.assetmanage.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cowry.assetmanage.R;
import com.cowry.assetmanage.activity.CheckActivity;
import com.cowry.assetmanage.activity.ManageActivity;
import com.cowry.assetmanage.activity.ScrapActivity;
import com.cowry.assetmanage.activity.SearchActivity;
import com.cowry.assetmanage.activity.SettingActivity;
import com.cowry.assetmanage.activity.UserCenterActivity;
import com.cowry.assetmanage.base.BaseFragment;

/**
 * Created by acer on 2016/6/23.
 */
public class IndexFragment extends BaseFragment implements View.OnClickListener{
    private LinearLayout ly1;
    private LinearLayout ly2;
    private LinearLayout ly3;
    private LinearLayout ly4;
    private LinearLayout ly5;
    private ImageView img;


    @Override
    protected void setContentView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_index,container,false);
    }

    @Override
    public void findView() {
        ly1 = (LinearLayout) rootView.findViewById(R.id.ly1);
        ly2 = (LinearLayout) rootView.findViewById(R.id.ly2);
        ly3 = (LinearLayout) rootView.findViewById(R.id.ly3);
        ly4 = (LinearLayout) rootView.findViewById(R.id.ly4);
        ly5 = (LinearLayout) rootView.findViewById(R.id.ly5);
        img = (ImageView) rootView.findViewById(R.id.img);
    }

    @Override
    public void setListener() {
        ly1.setOnClickListener(this);
        ly2.setOnClickListener(this);
        ly3.setOnClickListener(this);
        ly4.setOnClickListener(this);
        ly5.setOnClickListener(this);
        img.setOnClickListener(this);

    }

    @Override
    public void underCreate() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.ly1:
                intent.setClass(getContext(), ManageActivity.class);
                startActivity(intent);
                break;
            case R.id.ly2:
                intent.setClass(getContext(), CheckActivity.class);
                startActivity(intent);
                break;
            case R.id.ly3:
                intent.setClass(getContext(), ScrapActivity.class);
                startActivity(intent);
                break;
            case R.id.ly4:
                intent.setClass(getContext(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.ly5:
                intent.setClass(getContext(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.img:
                intent.setClass(getContext(), UserCenterActivity.class);
                startActivity(intent);
                break;

        }
    }
}
