package com.cbooy.mmpa.activity.antithefts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.activity.AntiTheftActivity;

public class SetupFourActivity extends BaseSetupActivity {
	
	private SharedPreferences sp ;
	
	
	public void next(View v){
		
		Editor editor = sp.edit();
		
		editor.putBoolean("is_setup", true);
		
		editor.commit();
		
		Intent intent = new Intent(this,AntiTheftActivity.class);
		
		startActivity(intent);
		
		finish();
	}
	
	@Override
	public void init() {
		setContentView(R.layout.setup_four_activity);
		
		sp = getSharedPreferences("config", MODE_PRIVATE);

		preActivity = SetupThreeActivity.class;
	}
}
