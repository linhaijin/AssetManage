package com.cowry.assetmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cowry.assetmanage.R;
import com.cowry.assetmanage.bean.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuliangchun on 2016/7/7.
 */
public class AllocationListAdapter extends BaseAdapter{
    private List<Bean> beans;
    private Context context;

    public AllocationListAdapter(Context context) {
        this.context = context;
        beans = new ArrayList<>();
    }

    public void refresh(List<Bean> beans){
        this.beans = beans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        Bean bean = beans.get(position);
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_allocation,null);
            holder = new Holder();
            holder.tvId = (TextView) convertView.findViewById(R.id.tvId);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvNum = (TextView) convertView.findViewById(R.id.tvNum);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        holder.tvName.setText(bean.getbName());
        holder.tvId.setText(bean.getbId());
        holder.tvNum.setText(bean.getbAmount());
        return convertView;
    }

    private static class Holder {
        private TextView tvId;
        private TextView tvName;
        private TextView tvNum;
    }
}
