package com.cbooy.mmpa.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.activity.adapters.HomePageLayoutAdapter;
import com.cbooy.mmpa.activity.callbacks.FlushWeatherInfoViewHandler;
import com.cbooy.mmpa.activity.views.AntiTheftDialog;
import com.cbooy.mmpa.activity.views.FocusedTextView;
import com.cbooy.mmpa.model.HomePageViewItem;
import com.cbooy.mmpa.utils.StaticDatas;
import com.cbooy.mmpa.utils.WeatherInfoUtil;

public class HomeActivity extends Activity {

	private GridView gvFuncLists;

	private SharedPreferences sp;
	
	private FocusedTextView ftv;
	
	private List<HomePageViewItem> homePageViewItems;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			// 确认
			if (msg.what == StaticDatas.ANTITHEFT_DIALOG_CONFIRM) {
				boolean isConfirm = (Boolean) msg.obj;

				if (isConfirm) {
					goAntiTheftActivity();
				}
			}

			// 输入
			if (msg.what == StaticDatas.ANTITHEFT_DIALOG_ENTER) {
				boolean isEnter = (Boolean) msg.obj;

				if (isEnter) {
					goAntiTheftActivity();
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.home_activity);
		
		initDatas();

		initViews();
	}
	
	// 初始化 View 
	private void initViews() {

		// 滚动新闻
		ftv = (FocusedTextView) this.findViewById(R.id.tv_scorll_text);

		// 九宫格显示View
		gvFuncLists = (GridView) this.findViewById(R.id.gv_func_lists);

		gvFuncLists.setAdapter(new HomePageLayoutAdapter(HomeActivity.this,
				homePageViewItems));

		gvFuncLists.setOnItemClickListener(new ItemClickListener());

		// 加载天气信息
		WeatherInfoUtil.build().getCurrentWeatherInfo("beijing",
				new FlushWeatherInfoViewHandler() {

					@Override
					public void handler(String weatherInfos) {
						ftv.setText(weatherInfos);
					}
				});
	}
	
	// 菜单点击 监控器
	private class ItemClickListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			// 手机防盗
			if (0 == position) {

				// 取出密码
				String oldPasswd = sp.getString(StaticDatas.CONFIG_PASSWD, null);

				// 没有设置,弹出 输入密码并确认
				if (TextUtils.isEmpty(oldPasswd)) {
					new AntiTheftDialog(HomeActivity.this, handler).confirmDialog();
				} else {
					// 已经设置，弹出输入密码
					new AntiTheftDialog(HomeActivity.this, handler).enterPasswd(oldPasswd);
				}
			}
			
			// 高级工具
			if(7 == position){
				Intent intent = new Intent(HomeActivity.this,SeniorToolsActivity.class);
				
				startActivity(intent);
			}
			
			// 进入 设置中心
			if (8 == position) {
				Intent intent = new Intent(HomeActivity.this,SettingActivity.class);

				startActivity(intent);
			}
		}
	}
	
	// 初始化数据
	private void initDatas() {
		
		// 加载配置文件
		sp = getSharedPreferences(StaticDatas.SP_CONFIG_FILE, MODE_PRIVATE);
		
		//初始化界面数据
		homePageViewItems = new ArrayList<HomePageViewItem>();
		
		HomePageViewItem item1 = new HomePageViewItem(getString(R.string.home_page_item_01),R.drawable.home_page_item_01);
		HomePageViewItem item2 = new HomePageViewItem(getString(R.string.home_page_item_02),R.drawable.home_page_item_02);
		HomePageViewItem item3 = new HomePageViewItem(getString(R.string.home_page_item_03),R.drawable.home_page_item_03);
		HomePageViewItem item4 = new HomePageViewItem(getString(R.string.home_page_item_04),R.drawable.home_page_item_04);
		HomePageViewItem item5 = new HomePageViewItem(getString(R.string.home_page_item_05),R.drawable.home_page_item_05);
		HomePageViewItem item6 = new HomePageViewItem(getString(R.string.home_page_item_06),R.drawable.home_page_item_06);
		HomePageViewItem item7 = new HomePageViewItem(getString(R.string.home_page_item_07),R.drawable.home_page_item_07);
		HomePageViewItem item8 = new HomePageViewItem(getString(R.string.home_page_item_08),R.drawable.home_page_item_08);
		HomePageViewItem item9 = new HomePageViewItem(getString(R.string.home_page_item_09),R.drawable.home_page_item_09);
		
		homePageViewItems.add(item1);
		homePageViewItems.add(item2);
		homePageViewItems.add(item3);
		homePageViewItems.add(item4);
		homePageViewItems.add(item5);
		homePageViewItems.add(item6);
		homePageViewItems.add(item7);
		homePageViewItems.add(item8);
		homePageViewItems.add(item9);
		
	}

	/**
	 * 切换到 防盗页面
	 */
	protected void goAntiTheftActivity() {
		Intent intent = new Intent(this, AntiTheftActivity.class);

		startActivity(intent);
	}
}
