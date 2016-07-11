package com.cowry.assetmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cowry.assetmanage.R;
import com.cowry.assetmanage.bean.Bean;
import com.cowry.assetmanage.bean.SearchBean;
import com.cowry.assetmanage.widgets.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/6/27.
 */
public class SearchAdapter extends BaseAdapter {
    private List<Bean> recordBeans;
    private Context context;


    public SearchAdapter(Context context) {
        this.context = context;
        recordBeans = new ArrayList<>();
    }

    public void refresh(List<Bean> recordBeans){
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
            viewHolder.status = (TextView) convertView.findViewById(R.id.status);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Bean bean = recordBeans.get(position);
        viewHolder.user.setText(bean.getbUser());
        viewHolder.name.setText(bean.getbName());
        viewHolder.date.setText(DateUtils.ToYMD(bean.getbInTime()));
        viewHolder.imageView.setImageResource(bean.getbImg());
        viewHolder.status.setText(bean.getbStatus());
        return convertView;
    }

    private static class ViewHolder {
        private ImageView imageView;
        private TextView name;
        private TextView user;
        private TextView date;
        private TextView status;
    }
}
