package com.cbooy.mmpa.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.cbooy.mmpa.activity.seniortools.AddressHandlerCallBack;
import com.cbooy.mmpa.utils.SearchPhotoNumberUtil;

public class AddressService extends Service {
	
	private TelephonyManager tm;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		
		tm.listen(new PhoneStateListener(){

			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				super.onCallStateChanged(state, incomingNumber);
				
				switch (state) {
				case TelephonyManager.CALL_STATE_RINGING:
					new SearchPhotoNumberUtil().searchLocation(incomingNumber,new AddressHandlerCallBack(){
						@Override
						public void call(String address) {
							Toast.makeText(AddressService.this, address, Toast.LENGTH_LONG).show();
						}});
					
					break;
				case TelephonyManager.CALL_STATE_IDLE:
					
					break;
				default:
					break;
				}
			}
			
		},  PhoneStateListener.LISTEN_CALL_STATE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
