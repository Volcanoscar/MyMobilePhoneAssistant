package com.cbooy.mmpa.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.os.SystemClock;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.TextView;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.activity.seniortools.AddressHandlerCallBack;
import com.cbooy.mmpa.utils.SearchPhotoNumberUtil;
import com.cbooy.mmpa.utils.StaticDatas;

public class AddressService extends Service {
	
	private TelephonyManager tm;
	
	private WindowManager wm;
	
	private PhoneStateListener phoneStateListener;
	
	private PhoneCalloutReceiver phoneReceiver;
	
	private View toastView = null;
	
	private int [] ids = null;
	
	private SharedPreferences sp;
	
	private long[] clickTimes = new long[2];
	
	//窗体参数
	private WindowManager.LayoutParams params = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		initViews();
		
		initDatas();
	}

	private void initDatas() {
		
		params = new WindowManager.LayoutParams();
		
		tm.listen(phoneStateListener,  PhoneStateListener.LISTEN_CALL_STATE);
		
		IntentFilter filter = new IntentFilter();
		
		filter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);

		registerReceiver(phoneReceiver, filter);
		
		ids = new int[]{R.drawable.call_locate_white,
				R.drawable.call_locate_orange,
				R.drawable.call_locate_blue
			    ,R.drawable.call_locate_gray,
			    R.drawable.call_locate_green};

		sp = getSharedPreferences(StaticDatas.SP_CONFIG_FILE, MODE_PRIVATE);		
	}

	private void initViews() {
		phoneStateListener = new PhoneListener();
		
		phoneReceiver = new PhoneCalloutReceiver();
		
		wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	private void toast(String text){
		toastView = View.inflate(this, R.layout.self_toast_item	,null);
		
		TextView tvShowAddress = (TextView) toastView.findViewById(R.id.tv_show_address);
		
		toastView.setBackgroundResource(ids[sp.getInt(StaticDatas.TOAST_SHOW_INDEX, 0)]);
		
	    tvShowAddress.setText(text);
		
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        
        params.gravity = Gravity.TOP + Gravity.LEFT;
        
        params.x = (int) sp.getFloat(StaticDatas.SELF_TOAST_X, 100.0f);
        
        params.y = (int) sp.getFloat(StaticDatas.SELF_TOAST_Y, 100.0f);
        
		toastView.setOnTouchListener(new OnTouchListener() {
			int initX = 0;
			int initY = 0;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					initX = (int) event.getRawX();
					initY = (int) event.getRawY();
					
					break;
				case MotionEvent.ACTION_MOVE:
					int newX = (int) event.getRawX();
					
					int newY = (int) event.getRawY();
					
					int dx = newX - initX;
					
					int dy = newY - initY;
					
					params.x += dx;
					
					params.y += dy;
					
					wm.updateViewLayout(toastView, params);
					
					initX = (int) event.getRawX();
					
					initY = (int) event.getRawY();
					
					break;
				case MotionEvent.ACTION_UP:
					Editor editor = sp.edit();
					editor.putFloat(StaticDatas.SELF_TOAST_X, event.getRawX());
					editor.putFloat(StaticDatas.SELF_TOAST_Y, event.getRawY());
					editor.commit();
					
					break;
				default:
					break;
				}
				
				return false;
			}
		});
		
		toastView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				System.arraycopy(clickTimes, 1, clickTimes, 0, clickTimes.length - 1);
				
				clickTimes[clickTimes.length - 1] = SystemClock.uptimeMillis();

				if(clickTimes[0] >= (SystemClock.uptimeMillis() - 500)){
					
					DisplayMetrics displayMetrics = new DisplayMetrics();
					
					wm.getDefaultDisplay().getMetrics(displayMetrics);
					
					params.x = displayMetrics.widthPixels/2 - toastView.getWidth()/2;
					
					wm.updateViewLayout(toastView, params);
					
					Editor editor = sp.edit();
					editor.putFloat(StaticDatas.SELF_TOAST_X, params.x);
					editor.commit();
				}
			}});
        
		// WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        
        params.format = PixelFormat.TRANSLUCENT;
        
        // android系统里面具有电话优先级的一种窗体类型需要加加权限
        params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
        //params.type = WindowManager.LayoutParams.TYPE_TOAST;
        
        wm.addView(toastView, params);
	}
	
	private void showAddress(String num) {
		new SearchPhotoNumberUtil().searchLocation(num,
				new AddressHandlerCallBack() {
					@Override
					public void call(String address) {
						toast(address);
					}
				});
	}
	
	private class PhoneCalloutReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String num = getResultData();
			showAddress(num);
		}
	}
	
	private class PhoneListener extends PhoneStateListener {
		
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				
				showAddress(incomingNumber);

				break;
			case TelephonyManager.CALL_STATE_IDLE:

				if (toastView != null) {
					wm.removeView(toastView);
				}

				break;
			default:
				break;
			}
		}
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
}
