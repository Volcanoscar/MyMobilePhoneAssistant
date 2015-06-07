package com.cbooy.mmpa.utils;

import android.os.SystemClock;

public class TimerUtil {
	
	/**
	 * 指定时间睡多久
	 * @param start
	 * @param assertTime
	 */
	public void sleepForThread(long start, long assertTime) {

		long current = System.currentTimeMillis();

		long dtime = current - start;

		if (dtime < assertTime) {
			SystemClock.sleep(assertTime - dtime);
		}
	}
}
