package com.cbooy.mmpa.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.cbooy.mmpa.activity.seniortools.AddressHandlerCallBack;

public class SearchPhotoNumberUtil {

	@SuppressLint("SdCardPath")
	private final static String path = "/data/data/com.cbooy.mmpa/files/address.db";
	
	private static Map<String,String> initData = new HashMap<String,String>();
	
	private SQLiteDatabase addressDB = null;
	
	private AddressHandlerCallBack addressCallback;
	
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
		} else {

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

		if(addressDB == null){
			addressDB = SQLiteDatabase.openDatabase(path, null,SQLiteDatabase.OPEN_READONLY);
		}
		
		// 判断号码是否为 手机号码
		if (num.matches("^1[34568]\\d{9}$")) {

			Cursor cursor = addressDB.rawQuery("select location from data2 where id=(select outkey from data1 where id=?)",new String[] { num.substring(0, 7) });

			if (cursor.getCount() == 1 && cursor.moveToNext()) {
				cb.call(cursor.getString(0));
			}else{
				
				addressCallback = cb;
				
				// 请求网络进行获取,num
				new AccessAddressTask().execute(num);
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
	
	class AccessAddressTask extends AsyncTask<String,Void,String>{

		@Override
		protected String doInBackground(String... params) {
			
			InputStream ins = httpGetForAddress(params[0]);
			
			return parseAddress(ins);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			addressCallback.call(result);
		}
	}
	
	private InputStream httpGetForAddress(String num){
		try {
			
			StringBuilder sbf = new StringBuilder();
			
			sbf.append("http://apis.baidu.com/apistore/mobilephoneservice/mobilephone")
				.append("?")
				.append("tel=")
				.append(num.trim());
			
			URL url = new URL(sbf.toString());
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setConnectTimeout(5 * 1000);

			conn.setRequestMethod("GET");
			
			conn.setRequestProperty("apikey",  "bb5054b69475c3188d69d1f376baf1e4");

			conn.connect();
			
			if (conn.getResponseCode() / 200 == 1) {
				return conn.getInputStream();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String parseAddress(InputStream ins){
		
		String res = StreamUtil.readFromStream(ins);
		
		try {
			JSONObject jsonObj = new JSONObject(res);
			
			if("success".equals(jsonObj.getString("errMsg").trim())){
				
				JSONObject temp = jsonObj.getJSONObject("retData");
				
				return URLDecoder.decode(temp.getString("carrier"), "UTF-8");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
