package com.cbooy.mmpa.activity.antithefts;

import com.cbooy.mmpa.R;

public class SetupTwoActivity extends BaseSetupActivity {
	@Override
	public void init() {
		setContentView(R.layout.setup_two_activity);
		
		nextActivity = SetupThreeActivity.class;
		
		preActivity = SetupOneActivity.class;
	}
}
