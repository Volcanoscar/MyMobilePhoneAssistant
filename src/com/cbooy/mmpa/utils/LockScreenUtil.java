package com.cbooy.mmpa.utils;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.cbooy.mmpa.receiver.MyAdmin;

public class LockScreenUtil {

	private static Context context = null;
	
	/**
	 * 设置策略服务
	 */
	private static DevicePolicyManager dpm = null;
	
	private LockScreenUtil(){
		
	}

	public static LockScreenUtil inst(Context ct) {
		
		context = ct;
		
		dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
		
		return new LockScreenUtil();
	}
	
	/**
	 * 开启锁屏,先判断是否开启权限
	 */
	public void lockScreen(){
		
		ComponentName who = new ComponentName(context,MyAdmin.class);
		
		if(dpm.isAdminActive(who)){
			
			// 锁屏
			dpm.lockNow();
			
			//设置屏蔽密码
			dpm.resetPassword("123", 0);
			
			//清除Sdcard上的数据
			// dpm.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
		}else{
			// 没有权限,需要先开通
			Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			
	        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, who);
	        
	        //开启管理员权限
	        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"一键锁屏");
	        
	        context.startActivity(intent);
		}
	}
	
	public void removePermission() {
		
		// 1.先清除管理员权限
		ComponentName mDeviceAdminSample = new ComponentName(context,MyAdmin.class);
		
		dpm.removeActiveAdmin(mDeviceAdminSample);
		
		// 2.普通应用的卸载
		Intent intent = new Intent();
		
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		
		intent.setAction("android.intent.action.VIEW");
		
		intent.addCategory("android.intent.category.DEFAULT");
		
		intent.setData(Uri.parse("package:" + context.getPackageName()));
		
		context.startActivity(intent);
	}
}
