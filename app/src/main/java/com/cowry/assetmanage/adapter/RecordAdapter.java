package com.cowry.assetmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cowry.assetmanage.R;
import com.cowry.assetmanage.bean.RecordBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/6/27.
 */
public class RecordAdapter extends BaseAdapter {
    private List<RecordBean> recordBeans;
    private Context context;


    public RecordAdapter(Context context) {
        this.context = context;
        recordBeans = new ArrayList<>();
    }

    public void refresh(List<RecordBean> recordBeans){
        this.recordBeans = recordBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return recordBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return recordBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_record,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.img);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.user = (TextView) convertView.findViewById(R.id.user);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        RecordBean bean = recordBeans.get(position);
        viewHolder.user.setText(bean.getUser());
        viewHolder.name.setText(bean.getName());
        viewHolder.date.setText(bean.getDate());
        viewHolder.imageView.setImageResource(bean.getImg());
        return convertView;
    }

    private static class ViewHolder {
        private ImageView imageView;
        private TextView name;
        private TextView user;
        private TextView date;
    }
}
