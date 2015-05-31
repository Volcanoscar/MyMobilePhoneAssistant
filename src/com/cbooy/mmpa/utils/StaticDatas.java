package com.cbooy.mmpa.utils;

/**
 * 为了减少类的静态变量的定义而将全部的静态变量定义全部抽出来放置在一个类中
 * @author chenhao24
 *
 */
public class StaticDatas {
	// 版本信息返回码,需要更新
	public final static int VERSION_NEED_UPDATE = 10001;

	// 版本信息返回码,不需要更新
	public final static int VERSION_NO_NEED_UPDATE = 10002;
	
	// 访问网络的URL出错
	public static final int URL_ERROR = 10003;
	
	// 下载进度的 状态码
	public static final int DOWNLOAD_PROCESSING = 10004;
	
	// URL链接出错
	public static final int URL_CONNECTION_ERROR = 10004;
	
	// JSON转换出错
	public static final int JSON_CONVERTOR_ERROR = 10005;
	
	// 是否下载文件状态码
	public static final int IS_DOWNLOAD_NEW_VERSION = 10006;
	
	// com.cbooy.mmpa.utils.HttpUtil 日志 TAG
	public static final String HTTPUTIL_LOG_TAG = "HttpUtil";
	
	// com.cbooy.mmpa.BootActivity 日志TAG
	public static final String BOOTACTIVITY_LOG_TAG = "BootActivity";
}
