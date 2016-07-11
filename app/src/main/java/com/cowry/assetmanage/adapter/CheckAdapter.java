package com.cowry.assetmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cowry.assetmanage.R;
import com.cowry.assetmanage.bean.CheckBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/6/27.
 */
public class CheckAdapter extends BaseAdapter {
    private List<CheckBean> checkBeans;
    private Context context;

    public CheckAdapter(Context context) {
        this.context = context;
        this.checkBeans = new ArrayList<>();
    }

    public void refresh(List<CheckBean> checkBeans){
        this.checkBeans = checkBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return checkBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return checkBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_check,null);
            viewHolder = new ViewHolder();
            viewHolder.number = (TextView) convertView.findViewById(R.id.number);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.category = (TextView) convertView.findViewById(R.id.category);
            viewHolder.status = (TextView) convertView.findViewById(R.id.status);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CheckBean bean = checkBeans.get(position);
        viewHolder.number.setText(String.valueOf(bean.getNumber()));
        viewHolder.date.setText(bean.getDate());
        viewHolder.category.setText(bean.getCategory());
        viewHolder.status.setText(bean.getStatus());
        return convertView;
    }

    private static class ViewHolder {
        private TextView number;
        private TextView date;
        private TextView category;
        private TextView status;
    }
}
