package com.cbooy.mmpa;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cbooy.mmpa.activity.HomeActivity;
import com.cbooy.mmpa.utils.HttpUtil;
import com.cbooy.mmpa.utils.PackageManagerUtil;
import com.cbooy.mmpa.utils.StaticDatas;

@SuppressLint("HandlerLeak")
public class BootActivity extends Activity {
	
	// 显示版本号的TextView
	private TextView tvShowVersion;
	
	// 版本
	private String versionName;
	
	// 设置时间的起点
	private long start = 0;
	
	// boot activity 的 启动界面
	private RelativeLayout layoutBoot = null;
	
	// 不同线程之间通信
	private Handler handler = new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
				
				// 检查更新
				case StaticDatas.VERSION_NEED_UPDATE:
					
					String res = (String) msg.obj;
					
					Log.i(StaticDatas.BOOTACTIVITY_LOG_TAG, "信息： " + res);
					
					break;
					
				default:
					
					// 进入主页面
					goHomeActivity();
					
					break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.boot_activity);
		
		// 初始化View
		initViews();
		
		// 初始化 数据 
		initDatas();
		
		// 动画效果
		displayForAnimation();
	}
	
	private void displayForAnimation() {
		AlphaAnimation animation = new AlphaAnimation(0.1f,1.0f);
		
		animation.setDuration(1500);
		
		layoutBoot.setAnimation(animation);
	}

	// 进入主页面
	private void goHomeActivity() {
		
		// 跳转到 Home页
		Intent intent = new Intent(this,HomeActivity.class);
		
		startActivity(intent);
		
		finish();
	}

	/**
	 * 初始化数据
	 */
	private void initDatas() {
		
		// 设置时间的起点
		start = System.currentTimeMillis();
		
		// 获取版本号
		versionName = PackageManagerUtil.getVersion(this);
		
		// 设置版本
		tvShowVersion.setText("版本: " + versionName);
		
		// 检查版本信息是否需要更新
		HttpUtil.checkUpdateInfos(this, versionName, handler,start);
	}

	/**
	 * 对本页面的一些View初始化
	 */
	private void initViews() {
		
		// 版本信息的对象
		tvShowVersion = (TextView) this.findViewById(R.id.tv_show_version);
		
		// 加载布局文件
		layoutBoot = (RelativeLayout) this.findViewById(R.id.boot_activity_layout);
	}
}
