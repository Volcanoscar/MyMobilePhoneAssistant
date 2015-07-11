package com.cbooy.mmpa.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class ServicesUtil {

	public static boolean isServiceRun(Context context,String serviceName){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		
		List<RunningServiceInfo> rsis = am.getRunningServices(300);
		
		for (RunningServiceInfo runningServiceInfo : rsis) {
			if(runningServiceInfo.service.getClassName().equals(serviceName)){
				return true;
			}
		}
		
		return false;
	}
}
