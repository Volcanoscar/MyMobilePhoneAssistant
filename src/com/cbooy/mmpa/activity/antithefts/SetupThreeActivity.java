package com.cbooy.mmpa.activity.antithefts;

import com.cbooy.mmpa.R;

public class SetupThreeActivity extends BaseSetupActivity {

	@Override
	public void init() {
		setContentView(R.layout.setup_three_activity);

		nextActivity = SetupFourActivity.class;

		preActivity = SetupTwoActivity.class;
	}
}
