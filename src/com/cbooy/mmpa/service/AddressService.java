package com.cbooy.mmpa.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.activity.seniortools.AddressHandlerCallBack;
import com.cbooy.mmpa.utils.SearchPhotoNumberUtil;

public class AddressService extends Service {
	
	private TelephonyManager tm;
	
	private WindowManager wm;
	
	private PhoneStateListener phoneStateListener;
	
	private PhoneCalloutReceiver phoneReceiver;
	
	private View toastView = null;
	
	private int [] ids = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		initViews();
		
		initDatas();
	}

	private void initDatas() {
		tm.listen(phoneStateListener,  PhoneStateListener.LISTEN_CALL_STATE);
		
		IntentFilter filter = new IntentFilter();
		
		filter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);

		registerReceiver(phoneReceiver, filter);
		
		ids = new int[]{R.drawable.call_locate_white,
				R.drawable.call_locate_orange,
				R.drawable.call_locate_blue
			    ,R.drawable.call_locate_gray,
			    R.drawable.call_locate_green};
	}

	private void initViews() {
		phoneStateListener = new PhoneListener();
		
		phoneReceiver = new PhoneCalloutReceiver();
		
		wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		// cancel : call in
		tm.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
		phoneStateListener = null;
		
		// cancel : call out
		unregisterReceiver(phoneReceiver);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	private void toast(String text){
		toastView = View.inflate(this, R.layout.self_toast_item	,null);
		
		TextView tvShowAddress = (TextView) toastView.findViewById(R.id.tv_show_address);
		
		SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
		toastView.setBackgroundResource(ids[sp.getInt("which", 0)]);
	    tvShowAddress.setText(text);
		
		//窗体的参数就设置好了
		 WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		 
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        
        wm.addView(toastView, params);
	}
	
	private void showAddress(String num) {
		new SearchPhotoNumberUtil().searchLocation(num,
				new AddressHandlerCallBack() {
					@Override
					public void call(String address) {
						//Toast.makeText(AddressService.this, address,Toast.LENGTH_LONG).show();
						toast(address);
					}
				});
	}
	
	private class PhoneCalloutReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			String num = getResultData();
			Log.i("TAG", "call out ... "+num);
			showAddress(num);
		}
	}
	
	private class PhoneListener extends PhoneStateListener{
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				
				showAddress(incomingNumber);
				
				break;
			case TelephonyManager.CALL_STATE_IDLE:
				
				if(toastView != null){
					wm.removeView(toastView);
				}
				
				break;
			default:
				break;
			}
		}
	}
}
