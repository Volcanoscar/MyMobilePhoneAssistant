package com.cbooy.mmpa.test;

import android.test.AndroidTestCase;

import com.cbooy.mmpa.db.NumBlackListDBHelper;

public class BlackNumListTestCases extends AndroidTestCase {

	public void testCreateDB(){
		NumBlackListDBHelper db = new NumBlackListDBHelper(getContext());
		db.getWritableDatabase();
	}
}
