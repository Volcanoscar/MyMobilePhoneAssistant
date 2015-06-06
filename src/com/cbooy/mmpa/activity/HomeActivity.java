package com.cbooy.mmpa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbooy.mmpa.R;

public class HomeActivity extends Activity {

	private GridView gvFuncLists;

	private String[] names = new String[] { 
			"手机防盗", "通讯卫士", "软件管理", 
			"进程管理","流量统计", "手机杀毒", 
			"缓存清理", "高级工具", "设置中心" 
			};
	
	private int[] ids = new int[]{
			R.drawable.safe,R.drawable.callmsgsafe,R.drawable.app,
			R.drawable.taskmanager,R.drawable.netmanager,R.drawable.trojan,
			R.drawable.sysoptimize,R.drawable.atools,R.drawable.settings
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home_activity);

		gvFuncLists = (GridView) this.findViewById(R.id.gv_func_lists);

		gvFuncLists.setAdapter(new MyAdapter());
		
		gvFuncLists.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				
				if(8 == position){
					Intent intent = new Intent(HomeActivity.this,SettingActivity.class);
					
					startActivity(intent);
				}
			}});
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(convertView != null){
				return convertView;
			}
			
			View item = View.inflate(HomeActivity.this, R.layout.home_func_list_item, null);
			
			ImageView imgItem = (ImageView) item.findViewById(R.id.img_func_list_item);
			
			imgItem.setImageResource(ids[position]);
			
			TextView tvItem = (TextView) item.findViewById(R.id.tv_func_list_item);
			
			tvItem.setText(names[position]);
			
			return item;
		}

	}
}
