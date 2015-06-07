package com.cbooy.mmpa.utils;

import android.app.Activity;
import android.content.Intent;

public class ActivityUtil {
	
	@SuppressWarnings("rawtypes")
	public static void jumpNewActivityWithFinished(Activity oldPage, Class clazz) {
		Intent intent = new Intent(oldPage, clazz);

		oldPage.startActivity(intent);

		oldPage.finish();
	}
	
	public static void runOnUIThreadHelper(Object activity,Runnable runnable){
		
		((Activity) activity).runOnUiThread(runnable);
	}
}
