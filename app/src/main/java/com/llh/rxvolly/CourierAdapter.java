package com.llh.rxvolly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 项目名:    RxVolly
 * 包名:      com.llh.rxvolly
 * 文件名:    CourierAdapter
 * 创建者:    LLH
 * 创建时间:  2019/8/18 19:20
 * 描述:      TODO
 */
public class CourierAdapter extends BaseAdapter {
    //上下文
    private Context mContext;
    private List<Courier> mList;
    private LayoutInflater inflater;
    private Courier courier;
    public CourierAdapter(Context mContext,List<Courier> mList){
        this.mContext = mContext;
        this.mList = mList;
        //获取系统服务
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //创建实例
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            //绑定布局
            convertView = inflater.inflate(R.layout.item_courier,null);
            viewHolder.tv_datetime = convertView.findViewById(R.id.tv_datetime);
            viewHolder.tv_remark = convertView.findViewById(R.id.tv_remark);
            viewHolder.tv_zone = convertView.findViewById(R.id.tv_zone);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        courier = mList.get(position);
        //设置数据
        viewHolder.tv_datetime.setText(courier.getDatetime());
        viewHolder.tv_remark.setText(courier.getRemark());
        viewHolder.tv_zone.setText(courier.getZone());
        //ListView优化
        return convertView;
    }
    class ViewHolder{
        private TextView tv_datetime;
        private TextView tv_remark;
        private TextView tv_zone;
    }
}
