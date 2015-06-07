package com.cbooy.mmpa.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.activity.views.SettingItemRelativeLayout;

public class SettingActivity extends Activity {

	private SettingItemRelativeLayout settingView;
	
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.setting_activity);
		
		sp = this.getSharedPreferences("update_info", MODE_PRIVATE);
		
		final Editor editor = sp.edit();
		
		settingView = (SettingItemRelativeLayout) this.findViewById(R.id.item_setting);
		
		boolean isUpdate = sp.getBoolean("is_update", true);
		
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
					editor.putBoolean("is_update", false);
					
					settingView.setCheckd(false);
				}else{	// 没有被选中
					editor.putBoolean("is_update", true);
					
					settingView.setCheckd(true);
				}
				
				editor.commit();
			}});
	}
}
