package com.cbooy.mmpa.service;

import java.lang.reflect.Method;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;
import com.cbooy.mmpa.db.dao.NumBlackListDao;
import com.cbooy.mmpa.db.dao.impl.NumBlackListDaoImpl;
import com.cbooy.mmpa.model.NumBlackListPojo;
import com.cbooy.mmpa.utils.StaticDatas;

public class BlackNumberMonitorService extends Service {
	
	private NumBlackListDao numBlackListDao;
	
	private SMSInterceptReceiver receiver;
	
	private TelephonyManager tm;
	
	private BlackNumPhoneListener phoneStateListener;

	@Override
	public void onCreate() {
		super.onCreate();
		
		// 初始化数据
		initDatas();
	}
	
	/**
	 * 初始化数据
	 */
	private void initDatas() {
		numBlackListDao = new NumBlackListDaoImpl(this);
		
		receiver = new SMSInterceptReceiver();
		
		IntentFilter filter = new IntentFilter();
		
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		
		registerReceiver(receiver, filter );
		
		phoneStateListener = new BlackNumPhoneListener();
		
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		
		tm.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		unregisterReceiver(receiver);
		
		receiver = null;
		
		tm.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
		
		phoneStateListener = null;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	private class BlackNumPhoneListener extends PhoneStateListener{
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			
			// 响铃状态
			if(TelephonyManager.CALL_STATE_RINGING == state){
				NumBlackListPojo blackNum = numBlackListDao.findOneByNum(incomingNumber);
				if((blackNum != null) && (StaticDatas.BLACK_LIST_PHONE_MOD.equals(blackNum.getMod()) || StaticDatas.BLACK_LIST_ALL_MOD.equals(blackNum.getMod()))){
					Log.i("TAG", "黑名单电话打入 挂断" + incomingNumber);
					finishCall();
				}
			}
		}
	}
	
	/**
	 * 挂断电话
	 */
	private void finishCall() {
		try {
			Class<?> clazz = BlackNumberMonitorService.class.getClassLoader().loadClass("android.os.ServiceManager");
			Method method = clazz.getDeclaredMethod("getService", String.class);
			IBinder ibinder = (IBinder) method.invoke(null, TELEPHONY_SERVICE);
			ITelephony.Stub.asInterface(ibinder).endCall();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 短信拦截的广播接受者
	 * @author chenhao24
	 *
	 */
	private class SMSInterceptReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			Object[] datas = (Object[]) intent.getExtras().get("pdus");
			for (Object obj : datas) {
				SmsMessage sms = SmsMessage.createFromPdu((byte[])obj);
				
				String sender = sms.getOriginatingAddress();
				
				NumBlackListPojo numBlack = numBlackListDao.findOneByNum(sender);
				
				// 非黑名单 则不进行处理
				if(numBlack == null){
					return;
				}
				
				String mod = numBlack.getMod();
				
				// 短信 和 全部的 拦截模式时 进行拦截
				if(StaticDatas.BLACK_LIST_SMS_MOD.equals(mod) || StaticDatas.BLACK_LIST_ALL_MOD.equals(mod)){
					
					Log.i("TAG", "短信被拦截,拦截号码为 " + sender);
					
					abortBroadcast();
				}
			}
		}
	}
}
