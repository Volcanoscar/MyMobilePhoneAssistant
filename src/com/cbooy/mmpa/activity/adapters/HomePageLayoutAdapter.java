package com.cbooy.mmpa.activity.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.model.HomePageViewItem;

public class HomePageLayoutAdapter extends BaseAdapter {
	
	private LayoutInflater layoutInflater;
	
	private List<HomePageViewItem> homePageViewItems;
	
	public HomePageLayoutAdapter(Context context,List<HomePageViewItem> homePageViewItems) {
		layoutInflater = LayoutInflater.from(context);
		this.homePageViewItems = homePageViewItems;
	}

	@Override
	public int getCount() {
		return homePageViewItems.size();
	}

	@Override
	public Object getItem(int position) {
		return homePageViewItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		
		if(convertView == null){
			viewHolder = new ViewHolder();
			
			convertView = layoutInflater.inflate(R.layout.home_func_list_item, null);

			viewHolder.imageItem = (ImageView) convertView.findViewById(R.id.img_func_list_item);
			
			viewHolder.textItem = (TextView) convertView.findViewById(R.id.tv_func_list_item);

			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.imageItem.setImageResource(homePageViewItems.get(position).getResourceId());
		
		viewHolder.textItem.setText(homePageViewItems.get(position).getName());
		
		return convertView;
	}
	
	class ViewHolder{
		ImageView imageItem;
		
		TextView textItem;
	}
}
