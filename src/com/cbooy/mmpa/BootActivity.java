package com.cbooy.mmpa;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cbooy.mmpa.activity.HomeActivity;
import com.cbooy.mmpa.model.UpdateVersionInfo;
import com.cbooy.mmpa.utils.DialogUtil;
import com.cbooy.mmpa.utils.HttpUtil;
import com.cbooy.mmpa.utils.JsonForObjectConverter;
import com.cbooy.mmpa.utils.PackageManagerUtil;
import com.cbooy.mmpa.utils.SearchPhotoNumberUtil;
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
	
	// 配置文件
	private SharedPreferences sp;
	
	// 默认是否 检查配置
	private boolean isConfigCheckUpdate;
	
	// 不同线程之间通信
	private Handler handler = new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
				
				// 需要更新
				case StaticDatas.VERSION_NEED_UPDATE:
					
					checkUpdateFiles((String)msg.obj);
					
					break;
					
				// 是否下载
				case StaticDatas.IS_DOWNLOAD_NEW_VERSION:
					
					boolean isUpdate = (Boolean) msg.obj;
					
					if(isUpdate){
						new HttpUtil(BootActivity.this,handler).downloadFiles(updateVersioInfo.getUpdate_url(),tvDisplayDownloadProcess);
					}else{
						
						// 进入Home 页面
						goHomeActivity();
					}
					
					break;
				
				// 升级对话框取消
				case StaticDatas.DIALOG_DISMISS :
					
					// 进入主页面
					goHomeActivity();
					
					break;
				default:
					
					// 进入主页面
					goHomeActivity();
					
					break;
			}
		}
	};
	
	// 检查更新
	private void checkUpdateFiles(String res) {
		updateVersioInfo = JsonForObjectConverter.StringToObject(res,UpdateVersionInfo.class);
		
		// 弹出对话框提示是否更新
		new DialogUtil(this,handler).alertUpdateInfos(updateVersioInfo.getDesc());
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.boot_activity);
		
		// 复制数据
		try {
			new SearchPhotoNumberUtil().copyDBFiles(getAssets().open("address.db"), new File(getFilesDir(),"address.db"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
		
		sp = this.getSharedPreferences(StaticDatas.SP_CONFIG_FILE, MODE_PRIVATE);
		
		isConfigCheckUpdate = sp.getBoolean(StaticDatas.CONFIG_IS_UPDATE, true);
		
		if(isConfigCheckUpdate){
			// 检查版本信息是否需要更新
			new HttpUtil(this,handler).checkUpdateInfos(versionName,start);
		}else{
			
			handler.postDelayed(new Runnable(){
				@Override
				public void run() {
					// 进入主页
					goHomeActivity();
				}}, 2000);
		}
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
