package com.cbooy.mmpa.activity.antithefts;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.utils.StaticDatas;

public abstract class BaseSetupActivity extends Activity {
	
	// 下一个页面
	protected Class<?> nextActivity;
	
	// 上一个页面
	protected Class<?> preActivity;
	
	// 手势识别器
	private GestureDetector gd = null;
	
	protected SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		sp = getSharedPreferences(StaticDatas.SP_CONFIG_FILE, MODE_PRIVATE);
		
		// 实例化 手势识别器
		gd = new GestureDetector(this, new SimpleOnGestureListener() {
			/**
			 * 覆盖 滑动 方法
			 */
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,float velocityX, float velocityY) {
				Log.i(StaticDatas.BASESETUPACTIVITY_LOG_TAG, "event touch ..." + e1.getRawX() + "\t" + e2.getRawX());
				// 屏蔽滑动缓慢
				// if (Math.abs(velocityX) < 200) {
				// return true;
				// }
				
				// 屏蔽上下滑动
				// if (Math.abs(e1.getRawY() - e2.getRawY()) > 100) {
				// return true;
				// }
				
				// 向左滑动 下一页
				if (e1.getRawX() - e2.getRawX() > 200) {
					Log.i(StaticDatas.BASESETUPACTIVITY_LOG_TAG, "下一页");
					nextStep();
				}
				
				// 向右滑动 上一页
				if (e2.getRawX() - e1.getRawX() > 200) {
					Log.i(StaticDatas.BASESETUPACTIVITY_LOG_TAG, "上一页");
					preStep();
				}
				
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
		
		// 初始化
		init();
	}
	
	/**
	 * 子类覆写此方法 来初始化
	 */
	public abstract void init();
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gd.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
	// 下一个页面
	public void next(View v){
		nextStep();
	}
	
	// 上一个 页面
	public void pre(View v){
		preStep();
	}
	
	public void nextStep(){
		if(nextActivity != null){
			Intent intent = new Intent(this,nextActivity);
			
			startActivity(intent);
			
			finish();
			
			overridePendingTransition(R.anim.next_tran_in, R.anim.next_tran_out);
		}
	}
	
	public void preStep(){
		if(preActivity != null){
			Intent intent = new Intent(this,preActivity);
			
			startActivity(intent);
			
			finish();
			
			overridePendingTransition(R.anim.pre_tran_in,R.anim.pre_tran_out);
		}
	}
}
