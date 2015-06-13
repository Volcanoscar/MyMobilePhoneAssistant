package com.cbooy.mmpa.activity.antithefts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cbooy.mmpa.R;

public class SetupTwoActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.setup_two_activity);
	}
	
	public void next(View v){
		Intent intent = new Intent(this,SetupThreeActivity.class);
		
		startActivity(intent);
		
		finish();
	}
	
	public void pre(View v){
		Intent intent = new Intent(this,SetupOneActivity.class);
		
		startActivity(intent);
		
		finish();
	}
}
