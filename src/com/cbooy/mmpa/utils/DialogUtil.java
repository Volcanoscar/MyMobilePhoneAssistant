package com.cbooy.mmpa.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.Message;

public class DialogUtil {
	
	private Context context;
	
	private Handler handler;
	
	public DialogUtil(Context context,Handler handler) {
		this.context = context;
		this.handler = handler;
	}
	
	/**
	 * 升级的对话框,提示是否需要升级
	 * @param context
	 * @param desc
	 * @param handler
	 */
	public void alertUpdateInfos(String desc){
		
		AlertDialog.Builder dialogBuilder = new Builder(context);
		
		dialogBuilder.setTitle("系统升级");
		
		dialogBuilder.setMessage(desc);
		
		final Message msg = Message.obtain();
		
		msg.what = StaticDatas.IS_DOWNLOAD_NEW_VERSION;
		
		// 添加确定按钮的 监听器
		dialogBuilder.setPositiveButton("立刻升级", new OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				dialog.dismiss();
				
				msg.obj = true;
				
				handler.sendMessage(msg);
			}});
		
		// 添加取消按钮的 监听器
		dialogBuilder.setNegativeButton("下次再说", new OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
				msg.obj = false;
				
				handler.sendMessage(msg);
			}});
		
		// 添加取消按钮的监听
		dialogBuilder.setOnCancelListener(new OnCancelListener(){
			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
				
				msg.what = StaticDatas.DIALOG_DISMISS;
				
				handler.sendMessage(msg);
			}});
		
		dialogBuilder.show();
	}
}
