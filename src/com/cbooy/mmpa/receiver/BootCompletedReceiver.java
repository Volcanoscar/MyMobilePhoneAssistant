package com.cbooy.mmpa.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.cbooy.mmpa.utils.StaticDatas;

public class BootCompletedReceiver extends BroadcastReceiver {

	private SharedPreferences sp;
	
	private TelephonyManager tm;
	
	private Context context;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		this.context = context;
		
		init();
	}

	private void init() {
		sp = context.getSharedPreferences(StaticDatas.SP_CONFIG_FILE, Context.MODE_PRIVATE);
		
		boolean is_protected = sp.getBoolean(StaticDatas.CONFIG_IS_PROTECTED, false);
		
		// 没有开启防盗保护则 无视
		if(! is_protected){
			return ;
		}
		
		tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		
		String oldSeriaNum = sp.getString(StaticDatas.CONFIG_SIM_SERIA_NUM, null);
		
		String currentSeriaNum = tm.getSimSerialNumber();
		
		if(! TextUtils.isEmpty(oldSeriaNum)){
			// SIM卡 发生变更
			if(! oldSeriaNum.equals(currentSeriaNum)){
				
				//发短信
				SmsManager.getDefault().sendTextMessage(
						sp.getString(StaticDatas.CONFIG_SAFE_PHONE, ""), 
						null, 
						"手机SIM卡变更", 
						null, 
						null);
			}
		}
	}
}
