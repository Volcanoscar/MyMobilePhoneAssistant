package com.cbooy.mmpa.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	public static void main(String[] args) {
		System.out.println(md5("123"));
	}
	
	/**
	 * MD5加密
	 * @param password
	 * @return
	 */
	public static String md5(String password) {

		// 得到一个信息摘要器
		MessageDigest digest = null;

		try {
			digest = MessageDigest.getInstance("md5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		byte[] result = digest.digest(password.getBytes());

		StringBuilder stringBuilder = new StringBuilder();

		String str = "";

		// 与运算 0xff;
		for (int index = 0; index < result.length; index++) {
			// 加盐
			int number = result[index] & 0xff;

			str = Integer.toHexString(number);

			if (str.length() == 1) {
				stringBuilder.append("0");
			}

			stringBuilder.append(str);
		}

		// 标准的md5加密后的结果
		return stringBuilder.toString();
	}
}
