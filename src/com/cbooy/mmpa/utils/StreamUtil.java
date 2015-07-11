package com.cbooy.mmpa.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * 流相关处理工具类
 * @author chenhao24
 *
 */
public class StreamUtil {
	
	/**
	 * @param is 输入流
	 * @return String 返回的字符串
	 * @throws IOException
	 */
	public static String readFromStream(InputStream is) throws IOException {
		return readFromStream(is,"utf8");
	}
	
	public static String readFromStream(InputStream is,String destCode) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		byte[] buffer = new byte[1024];
		
		int len = 0;
		
		while ((len = is.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
		}
		
		is.close();
		
		String result = baos.toString(destCode);
		
		baos.close();
		
		return result;
	}
	
	/**
	 * 字符串编码转换
	 * @param resource
	 * @param resCode
	 * @param destCode
	 * @return
	 */
	public static String decodeString(String resource,String resCode,String destCode){
		try {
			return new String(resource.getBytes(resCode),destCode);
		} catch (UnsupportedEncodingException e) {
			return resource;
		}
	}
}
