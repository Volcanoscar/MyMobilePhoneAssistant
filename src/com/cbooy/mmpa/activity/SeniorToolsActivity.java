package com.cbooy.mmpa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.activity.seniortools.SearchPhotoNumberLocal;

public class SeniorToolsActivity extends Activity implements OnClickListener{

	private TextView tvSearchPhotoLocal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.senior_tools_activity);
		
		tvSearchPhotoLocal = (TextView) this.findViewById(R.id.tv_find_photonum_local);
		
		tvSearchPhotoLocal.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// 号码归属地查询
		if(v.getId() == R.id.tv_find_photonum_local){
			Intent intent = new Intent(this,SearchPhotoNumberLocal.class);
			
			startActivity(intent);
		}
	}
}
