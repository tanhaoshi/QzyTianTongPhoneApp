package com.tt.qzy.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tt.qzy.view.R;
import com.tt.qzy.view.bean.AllArea;

import java.util.List;

/**
 * Created by qzy009 on 2018/8/22.
 */

public class AllAreaAdapter extends BaseExpandableListAdapter {

    private List<AllArea> mAllAreaList;
    private Context mContext;

    public AllAreaAdapter(List<AllArea> list , Context context){
        this.mAllAreaList = list;
        this.mContext = context;
    }

    @Override
    public int getGroupCount() {
        return mAllAreaList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mAllAreaList.get(i).getOffLineMaps().size();
    }

    @Override
    public Object getGroup(int i) {
        return mAllAreaList.get(i);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mAllAreaList.get(groupPosition).getOffLineMaps().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_offline_group,viewGroup,false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.mapName = (TextView)view.findViewById(R.id.mapName);
            groupViewHolder.mapSize = (TextView)view.findViewById(R.id.mapSize);
            groupViewHolder.download = (ImageView)view.findViewById(R.id.download);
            view.setTag(groupViewHolder);
        }else{
            groupViewHolder = (GroupViewHolder)view.getTag();
        }
        groupViewHolder.mapName.setText(mAllAreaList.get(i).getAddresName());
        groupViewHolder.mapSize.setText(mAllAreaList.get(i).getMapSize());
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_offline_child,viewGroup,false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.child_mapName = (TextView)view.findViewById(R.id.child_mapName);
            childViewHolder.child_mapSize = (TextView)view.findViewById(R.id.child_mapSize);
            childViewHolder.child_download = (ImageView)view.findViewById(R.id.child_download);
            view.setTag(childViewHolder);
        }else{
            childViewHolder = (ChildViewHolder)view.getTag();
        }
        childViewHolder.child_mapName.setText(mAllAreaList.get(groupPosition).getOffLineMaps().get(childPosition).getAreaName());
        childViewHolder.child_mapSize.setText(mAllAreaList.get(groupPosition).getOffLineMaps().get(childPosition).getPackageSize());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    static class GroupViewHolder{
        TextView mapName;
        TextView mapSize;
        ImageView download;
    }

    static class ChildViewHolder{
        TextView child_mapName;
        TextView child_mapSize;
        ImageView child_download;
    }
}
