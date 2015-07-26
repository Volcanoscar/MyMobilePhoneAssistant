package com.cbooy.mmpa.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.activity.views.SettingItemView;
import com.cbooy.mmpa.activity.views.ToastShowView;
import com.cbooy.mmpa.service.AddressService;
import com.cbooy.mmpa.service.BlackNumberMonitorService;
import com.cbooy.mmpa.utils.ServicesUtil;
import com.cbooy.mmpa.utils.StaticDatas;

public class SettingActivity extends Activity {

	private SettingItemView settingView;
	
	private SettingItemView listenCallAddress;
	
	private SettingItemView blackNumberSetting;
	
	private ToastShowView toastView;
	
	private SharedPreferences sp;
	
	@Override
	protected void onResume() {
		super.onResume();
		
		// 判断 号码归属地的服务是否开启
		boolean isServiceRun = ServicesUtil.isServiceRun(this, AddressService.class.getName());
		
		if(isServiceRun){
			listenCallAddress.setCheckd(true);
		}else{
			listenCallAddress.setCheckd(false);
		}
		
		// 判断 黑名单 监控服务是否开启
		boolean isBlackListServiceRun = ServicesUtil.isServiceRun(this, BlackNumberMonitorService.class.getName());
		
		if(isBlackListServiceRun){
			blackNumberSetting.setCheckd(true);
		}else{
			blackNumberSetting.setCheckd(false);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.setting_activity);
		
		sp = this.getSharedPreferences(StaticDatas.SP_CONFIG_FILE, MODE_PRIVATE);
		
		final Editor editor = sp.edit();
		
		// 设置开启检查更新
		settingView = (SettingItemView) this.findViewById(R.id.item_setting);
		
		// 号码归属地
		listenCallAddress = (SettingItemView) this.findViewById(R.id.listen_call_address);
		
		// 设置黑名单
		blackNumberSetting = (SettingItemView) this.findViewById(R.id.set_black_num_list);
		blackNumberSetting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(SettingActivity.this,BlackNumberMonitorService.class);
				
				if(blackNumberSetting.isChecked()){
					blackNumberSetting.setCheckd(false);
					stopService(intent);
				}else{
					blackNumberSetting.setCheckd(true);
					startService(intent);
				}
			}
		});
		
		// 设置土司 
		toastView = (ToastShowView) this.findViewById(R.id.toast_show);
		
		final String [] items = {"半透明","活力橙","卫士蓝","金属灰","苹果绿"};
		
		toastView.setOnClickListener(new OnClickListener(){
			
			int selectedIndex = 0;

			@Override
			public void onClick(View v) {
				
				int currentIdx = sp.getInt(StaticDatas.TOAST_SHOW_INDEX, 0);
				
				AlertDialog.Builder builder = new Builder(SettingActivity.this);
				builder.setTitle("归属地提示风格");
				builder.setSingleChoiceItems(items, currentIdx, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						selectedIndex = which;
					}
				});
				
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Editor editor = sp.edit();
						editor.putInt(StaticDatas.TOAST_SHOW_INDEX, selectedIndex);
						editor.commit();
						toastView.setDesc(items[selectedIndex]);
					}});
				
				builder.setNegativeButton("取消", null);
				builder.show();
			}});
		
		final Intent showAddress = new Intent(this,AddressService.class);
		
		listenCallAddress.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listenCallAddress.isChecked()){
					listenCallAddress.setCheckd(false);
					stopService(showAddress);
				}else{
					listenCallAddress.setCheckd(true);
					startService(showAddress);
				}
			}
		});
		
		boolean isUpdate = sp.getBoolean(StaticDatas.CONFIG_IS_UPDATE, true);
		
		if(isUpdate){
			settingView.setCheckd(true);
		}else{
			settingView.setCheckd(false);
		}
		
		settingView.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// 已经被选中
				if(settingView.isChecked()){
					editor.putBoolean(StaticDatas.CONFIG_IS_UPDATE, false);
					
					settingView.setCheckd(false);
				}else{	// 没有被选中
					editor.putBoolean(StaticDatas.CONFIG_IS_UPDATE, true);
					
					settingView.setCheckd(true);
				}
				
				editor.commit();
			}});
	}
}
