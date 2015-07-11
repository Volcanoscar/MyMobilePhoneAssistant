package com.cbooy.mmpa.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.cbooy.mmpa.activity.seniortools.AddressHandlerCallBack;

public class SearchPhotoNumberUtil {

	private final static String ADDRESS_URL = "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=";
	
	@SuppressLint("SdCardPath")
	private final static String path = "/data/data/com.cbooy.mmpa/files/address.db";
	
	private static Map<String,String> initData = new HashMap<String,String>();
	
	static{
		initData.put("110", "报警电话");
		initData.put("120", "医院电话");
		initData.put("119", "火警电话");
		initData.put("10086", "中国移动客服");
	}
	/**
	 * 拷贝数据库文件到包路径之下
	 * 
	 * @param src
	 * @param dest
	 */
	public void copyDBFiles(InputStream src, File dest) {
		if (dest.exists() && dest.length() > 0) {
			Log.i(StaticDatas.SEARCHPHOTONUMBERUTIL_LOG_TAG, "文件已存在");
		} else {

			Log.i(StaticDatas.SEARCHPHOTONUMBERUTIL_LOG_TAG, "拷贝数据库...");

			FileOutputStream fos = null;

			try {
				fos = new FileOutputStream(dest);

				byte[] b = new byte[1024];

				int len = 0;

				while ((len = src.read(b)) != -1) {
					fos.write(b, 0, len);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fos.close();
					src.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获取号码的归属地
	 * 
	 * @param num
	 * @return
	 */
	public void searchLocation(String num,AddressHandlerCallBack cb) {

		SQLiteDatabase addressDB = SQLiteDatabase.openDatabase(path, null,SQLiteDatabase.OPEN_READONLY);

		// 判断号码是否为 手机号码
		if (num.matches("^1[34568]\\d{9}$")) {

			Cursor cursor = addressDB.rawQuery("select location from data2 where id=(select outkey from data1 where id=?)",new String[] { num.substring(0, 7) });

			if (cursor.getCount() == 1 && cursor.moveToNext()) {
				cb.call(cursor.getString(0));
			}else{
				// 请求网络进行获取
				new Thread(new AccessNetworkForAddress(num, cb)) {}.start();
			}
			
			cursor.close();
		}else if(num.startsWith("0")){
			// 座机号码,第二位是2,1属于直辖市
			String area = null;
			
			int secondIndex = Integer.valueOf(num.substring(1, 2));

			if(secondIndex == 1 || secondIndex == 2){
				area = num.substring(1, 3);
			}else{
				area = num.substring(1, 4);
			}
			
			String sql = "select location from data2 where area=" + area;
			
			Cursor cursor = addressDB.rawQuery(sql, new String[]{});
			
			if(cursor.moveToNext()){
				String address = cursor.getString(0);
				cb.call(address.substring(0, address.length() - 2));
			}
		}else if(num.length() == 3){
			cb.call(initData.get(num));
		}else if(num.length() == 4){
			cb.call("模拟器");
		}else if(num.length() == 5){
			cb.call(initData.get(num));
		}else{
			cb.call("暂未存储");
		}
	}

	class AccessNetworkForAddress implements Runnable {

		private String num;

		private AddressHandlerCallBack cb;

		public AccessNetworkForAddress(String num,AddressHandlerCallBack cb) {
			this.num = num;
			this.cb = cb;
		}

		@Override
		public void run() {

			HttpURLConnection conn = null;

			URL url = null;

			try {
				url = new URL(ADDRESS_URL + num);

				conn = (HttpURLConnection) url.openConnection();

				conn.setConnectTimeout(5 * 1000);

				conn.setRequestMethod("GET");

				int code = conn.getResponseCode();

				if (code / 200 == 1) {
					String val = StreamUtil.readFromStream(conn.getInputStream(),"GBK");

					if (val != null && !"".equals(val)) {
						Matcher m = Pattern.compile("carrier:\'(.*?)\'").matcher(val);
						if(m.find()){
							cb.call(m.group(1));
						}
					}
					
					//cb.call();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				conn.disconnect();
			}
		}
	}
}
