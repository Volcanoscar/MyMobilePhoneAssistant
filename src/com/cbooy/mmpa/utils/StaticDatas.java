package com.cbooy.mmpa.utils;

/**
 * 为了减少类的静态变量的定义而将全部的静态变量定义全部抽出来放置在一个类中
 * 
 * @author chenhao24
 * 
 */
public class StaticDatas {
	// 版本信息返回码,需要更新
	public final static int VERSION_NEED_UPDATE = 10001;

	// 版本信息返回码,不需要更新
	public final static int VERSION_NO_NEED_UPDATE = 10002;

	// 访问网络的URL出错
	public final static int URL_ERROR = 10003;

	// 下载进度的 状态码
	public final static int DOWNLOAD_PROCESSING = 10004;

	// URL链接出错
	public final static int URL_CONNECTION_ERROR = 10004;

	// JSON转换出错
	public final static int JSON_CONVERTOR_ERROR = 10005;

	// 是否下载文件状态码
	public final static int IS_DOWNLOAD_NEW_VERSION = 10006;

	// 系统升级对话框被取消
	public final static int DIALOG_DISMISS = 10007;

	// 防盗输入密码确认
	public final static int ANTITHEFT_DIALOG_CONFIRM = 10008;

	// 防盗输入密码输入
	public final static int ANTITHEFT_DIALOG_ENTER = 10009;

	// 文件下载成功
	public final static int DOWNLOAD_SUCCESS = 0;

	// com.cbooy.mmpa.utils.HttpUtil 日志 TAG
	public final static String HTTPUTIL_LOG_TAG = "HttpUtil";

	// com.cbooy.mmpa.BootActivity 日志TAG
	public final static String BOOTACTIVITY_LOG_TAG = "BootActivity";

	// com.cbooy.mmpa.activity.HomeActivity 日志 TAG
	public final static String HOMEACTIVITY_LOG_TAG = "HomeActivity";

	// com.cbooy.mmpa.activity.antithefts.BaseSetupActivity 日志 TAG
	public final static String BASESETUPACTIVITY_LOG_TAG = "BaseSetupActivity";

	// com.cbooy.mmpa.receiver.BootCompletedReceiver 日志 TAG
	public final static String BootCompletedReceiver_LOG_TAG = "BootCompletedReceiver";

	// com.cbooy.mmpa.utils.ContactsHelperUtil 日志 TAG
	public final static String CONTACTSHELPERUTIL_LOG_TAG = "ContactsHelperUtil";
	
	// com.cbooy.mmpa.activity.antithefts.ContactsReaderActivity  日志 TAG
	public final static String CONTACTSREADERACTIVITY_LOG_TAG = "ContactsReaderActivity";
	
	// com.cbooy.mmpa.activity.AntiTheftActivity 日志 TAG
	public final static String ANTITHEFTACTIVITY_LOG_TAG = "AntiTheftActivity";
	
	// com.cbooy.mmpa.receiver.SmsMonitorReceiver 日志 TAG 
	public final static String SMSMONITORRECEIVER_LOG_TAG = "SmsMonitorReceiver";
	
	// 设置安全号码的 标签
	public final static String CONFIG_SAFE_PHONE = "bind_phone";
	
	// 设置 SIM卡 绑定的 序列号
	public final static String CONFIG_SIM_SERIA_NUM = "sim";
	
	// common SharedPreferences config
	public final static String SP_CONFIG_FILE = "config";
	
	// 本地设置的密码
	public final static String CONFIG_PASSWD = "passwd";
	
	// 设置是否自动更新
	public final static String CONFIG_IS_UPDATE = "is_update";
	
	// 设置是否开启保护
	public final static String CONFIG_IS_PROTECTED = "is_procted";
}
