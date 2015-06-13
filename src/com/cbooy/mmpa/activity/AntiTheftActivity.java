package com.cbooy.mmpa.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.activity.antithefts.SetupOneActivity;

public class AntiTheftActivity extends Activity {
	
	private SharedPreferences sp = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		sp = getSharedPreferences("config", MODE_PRIVATE);
		
		boolean isSetup = sp.getBoolean("is_setup", false);
		
		if(!isSetup){
			// 进入向导设置页
			Intent intent = new Intent(this,SetupOneActivity.class);
			
			startActivity(intent);
			
			finish();
		}else{
			setContentView(R.layout.antitheft_activity);
		}
	}
}
