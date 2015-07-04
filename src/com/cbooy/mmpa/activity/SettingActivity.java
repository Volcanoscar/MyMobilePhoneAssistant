package com.cbooy.mmpa.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.activity.views.SettingItemView;
import com.cbooy.mmpa.utils.StaticDatas;

public class SettingActivity extends Activity {

	private SettingItemView settingView;
	
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.setting_activity);
		
		sp = this.getSharedPreferences(StaticDatas.SP_CONFIG_FILE, MODE_PRIVATE);
		
		final Editor editor = sp.edit();
		
		settingView = (SettingItemView) this.findViewById(R.id.item_setting);
		
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
