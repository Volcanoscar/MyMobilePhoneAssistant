package com.cbooy.mmpa.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.IBinder;

public class GPSMonitorService extends Service {

	// 声明位置服务
	private LocationManager lm;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		
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
