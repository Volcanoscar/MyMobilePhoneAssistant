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
	
	// ��һ��ҳ��
	protected Class<?> nextActivity;
	
	// ��һ��ҳ��
	protected Class<?> preActivity;
	
	// ����ʶ����
	private GestureDetector gd = null;
	
	protected SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		sp = getSharedPreferences(StaticDatas.SP_CONFIG_FILE, MODE_PRIVATE);
		
		// ʵ���� ����ʶ����
		gd = new GestureDetector(this, new SimpleOnGestureListener() {
			/**
			 * ���� ���� ����
			 */
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,float velocityX, float velocityY) {
				Log.i(StaticDatas.BASESETUPACTIVITY_LOG_TAG, "event touch ..." + e1.getRawX() + "\t" + e2.getRawX());
				// ���λ�������
				// if (Math.abs(velocityX) < 200) {
				// return true;
				// }
				
				// �������»���
				// if (Math.abs(e1.getRawY() - e2.getRawY()) > 100) {
				// return true;
				// }
				
				// ���󻬶� ��һҳ
				if (e1.getRawX() - e2.getRawX() > 200) {
					Log.i(StaticDatas.BASESETUPACTIVITY_LOG_TAG, "��һҳ");
					nextStep();
				}
				
				// ���һ��� ��һҳ
				if (e2.getRawX() - e1.getRawX() > 200) {
					Log.i(StaticDatas.BASESETUPACTIVITY_LOG_TAG, "��һҳ");
					preStep();
				}
				
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
		
		// ��ʼ��
		init();
	}
	
	/**
	 * ���าд�˷��� ����ʼ��
	 */
	public abstract void init();
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gd.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
	// ��һ��ҳ��
	public void next(View v){
		nextStep();
	}
	
	// ��һ�� ҳ��
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