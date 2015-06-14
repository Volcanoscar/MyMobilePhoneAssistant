package com.cbooy.mmpa.service;

import com.cbooy.mmpa.utils.StaticDatas;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;

public class GPSMonitorService extends Service {

	// 声明位置服务
	private LocationManager lm;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		
		Log.i(StaticDatas.GPSMONITORSERVICE_LOG_TAG, "GPSMonitorService .. onCreate");
		
		super.onCreate();

		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		
		String provider = lm.getBestProvider(criteria, true);
		
		lm.requestLocationUpdates(provider, 60000, 50f, new BestLocationListener(this));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
