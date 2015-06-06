package com.cbooy.mmpa;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cbooy.mmpa.activity.HomeActivity;
import com.cbooy.mmpa.model.UpdateVersionInfo;
import com.cbooy.mmpa.utils.DialogUtil;
import com.cbooy.mmpa.utils.HttpUtil;
import com.cbooy.mmpa.utils.InstallerApkUtil;
import com.cbooy.mmpa.utils.JsonForObjectConverter;
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
	
	// 检查更新的版本信息
	private UpdateVersionInfo updateVersioInfo = null;
	
	// 显示下载进度信息
	private TextView tvDisplayDownloadProcess = null;
	
	// 不同线程之间通信
	private Handler handler = new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
				
				// 检查更新
				case StaticDatas.VERSION_NEED_UPDATE:
					
					checkUpdateFiles(msg);
					
					break;
					
				// 是否下载
				case StaticDatas.IS_DOWNLOAD_NEW_VERSION:
					
					boolean isUpdate = (boolean) msg.obj;
					
					if(isUpdate){
						new HttpUtil(BootActivity.this,handler).downloadFiles(updateVersioInfo.getUpdate_url());
					}else{
						
						// 进入Home 页面
						goHomeActivity();
					}
					
					break;
				
				// 下载进度显示
				case StaticDatas.DOWNLOAD_PROCESSING :
					
					tvDisplayDownloadProcess.setText("当前下载进度为:" + msg.obj + "%");
					
					break;
				
				// 安装文件
				case StaticDatas.DOWNLOAD_SUCCESS:
					
					// 安装文件
					new InstallerApkUtil(BootActivity.this,(File)msg.obj).install();;
					
					break;
				default:
					
					// 进入主页面
					goHomeActivity();
					
					break;
			}
		}
	};
	
	// 检查更新
	private void checkUpdateFiles(Message msg) {
		String res = (String) msg.obj;
		
		updateVersioInfo = JsonForObjectConverter.StringToObject(res,UpdateVersionInfo.class);
		
		Log.i(StaticDatas.BOOTACTIVITY_LOG_TAG, "反射对象信息: " + updateVersioInfo);
		
		// 弹出对话框提示是否更新
		new DialogUtil().alertUpdateInfos(BootActivity.this, updateVersioInfo.getDesc(),handler);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.boot_activity);
		
		// 初始化View
		initViews();
		
		// 初始化 数据 
		initDatas();
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
		versionName = new PackageManagerUtil().getVersion(this);
		
		// 设置版本
		tvShowVersion.setText("版本: " + versionName);
		
		// 检查版本信息是否需要更新
		new HttpUtil(this,handler).checkUpdateInfos(versionName,start);
	}

	/**
	 * 对本页面的一些View初始化
	 */
	private void initViews() {
		
		// 版本信息的对象
		tvShowVersion = (TextView) this.findViewById(R.id.tv_show_version);
		
		// 加载布局文件
		layoutBoot = (RelativeLayout) this.findViewById(R.id.boot_activity_layout);
		
		// 显示下载进度
		tvDisplayDownloadProcess = (TextView) this.findViewById(R.id.tv_show_download_process);
		
		// 动画效果
		displayForAnimation();
	}
	
	/**
	 * 动画展示效果
	 */
	private void displayForAnimation() {
		AlphaAnimation animation = new AlphaAnimation(0.1f,1.0f);
		
		animation.setDuration(1500);
		
		layoutBoot.setAnimation(animation);
	}
}
