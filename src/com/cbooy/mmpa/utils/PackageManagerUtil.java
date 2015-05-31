package com.cbooy.mmpa.utils;

import com.cbooy.mmpa.R;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class PackageManagerUtil {
	
	/**
	 * 获取版本号的信息
	 * @param context
	 * @return
	 */
	public static String getVersion(Context context){
		
		PackageManager pm = null;
		
		PackageInfo packageInfo = null;
		
		try {
			pm = context.getPackageManager();
			
			packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
			
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}finally{
			packageInfo = null;
			pm = null;
		}
		
		return context.getString(R.string.default_show_version);
	}
}
