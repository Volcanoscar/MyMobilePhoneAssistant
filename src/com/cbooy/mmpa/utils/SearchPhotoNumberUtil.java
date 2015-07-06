package com.cbooy.mmpa.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SearchPhotoNumberUtil {
	
	/**
	 * 拷贝数据库文件到包路径之下
	 * @param src
	 * @param dest
	 */
	public static void copyDBFiles(InputStream src,File dest){
		if(dest.exists() && dest.length() > 0){
			Log.i(StaticDatas.SEARCHPHOTONUMBERUTIL_LOG_TAG, "文件已存在");
		}else{

			Log.i(StaticDatas.SEARCHPHOTONUMBERUTIL_LOG_TAG, "拷贝数据库...");
			
			FileOutputStream fos = null;
			
			try {
				fos = new FileOutputStream(dest);
				
				byte[] b = new byte[1024];
				
				int len = 0;
				
				while((len = src.read(b)) != -1){
					fos.write(b, 0, len);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
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
	 * @param num
	 * @return
	 */
	public static String searchLocation(String dbPath,String num) {
		
		SQLiteDatabase addressDB = SQLiteDatabase.openDatabase(dbPath , null, SQLiteDatabase.OPEN_READONLY);
		
		// 判断号码是否为 手机号码
		if(num.matches("^1[34568]\\d{9}$")){
			
			String tmp = null;
			
			Cursor cursor = addressDB.rawQuery(
					"select location from data2 where id=(select outkey from data1 where id=?)", 
					new String[]{num.substring(0, 7)});
			
			if(cursor.getCount() == 1 && cursor.moveToNext()){
				tmp = cursor.getString(0);
			}
			
			cursor.close();
			
			return tmp;
		}
		
		
		return null;
	}
}
