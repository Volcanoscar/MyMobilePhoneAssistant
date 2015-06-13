package com.cbooy.mmpa.activity.antithefts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cbooy.mmpa.R;

public class SetupOneActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.setup_one_activity);
		
	}
	
	public void next(View v){
		Intent intent = new Intent(this,SetupTwoActivity.class);
		
		startActivity(intent);
		
		finish();
	}
}
