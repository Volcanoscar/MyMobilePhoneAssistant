package com.cbooy.mmpa.activity.antithefts;

import com.cbooy.mmpa.R;

public class SetupOneActivity extends BaseSetupActivity{
	
	@Override
	public void init() {
		setContentView(R.layout.setup_one_activity);
		nextActivity = SetupTwoActivity.class;
	}
}
