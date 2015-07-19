package com.cbooy.mmpa.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.cbooy.mmpa.activity.callbacks.FlushWeatherInfoViewHandler;
import com.cbooy.mmpa.model.WeatherInfo;

public class WeatherInfoUtil {
	
	private static String httpUrl = "http://apis.baidu.com/apistore/weatherservice/weather";
	
	private static String charsetName = "utf-8";
	
	private FlushWeatherInfoViewHandler flushWeatherInfo;
	
	public static WeatherInfoUtil build(){
		return new WeatherInfoUtil();
	}
	
	public void getCurrentWeatherInfo(String city,FlushWeatherInfoViewHandler flushWeatherInfo){
		
		if(city == null || "".equals(city.trim())){
			throw new IllegalArgumentException("请求天气信息参数错误,不可为空");
		}
		
		this.flushWeatherInfo = flushWeatherInfo;
		
		// 异步请求网络加载天气数据
		new LoadWeatherInfoTask().execute(city);
	}
	
	private WeatherInfo parseJSON(String json){
		
		if(json == null || "".equals(json.trim())){
			throw new IllegalArgumentException("获取天气结果错误");
		}
		
		WeatherInfo weatherInfo = null;
		
		try {
			JSONObject jsonObj = new JSONObject(json);
			
			String res = jsonObj.getString("errMsg").trim();
			
			if("success".equals(res)){
				JSONObject temp = jsonObj.getJSONObject("retData");
				
				weatherInfo = new WeatherInfo();
				weatherInfo.setCity(URLDecoder.decode(temp.getString("city"), charsetName));
				weatherInfo.setDate(temp.getString("date"));
				weatherInfo.setHighTemp(temp.getString("h_tmp"));
				weatherInfo.setLowTemp(temp.getString("l_tmp"));
				weatherInfo.setSunrise(temp.getString("sunrise"));
				weatherInfo.setSunset(temp.getString("sunset"));
				weatherInfo.setTime(temp.getString("time"));
				weatherInfo.setWd(URLDecoder.decode(temp.getString("WD"), charsetName));
				weatherInfo.setWeather(URLDecoder.decode(temp.getString("weather"), charsetName));
				weatherInfo.setWs(URLDecoder.decode(temp.getString("WS"), charsetName));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return weatherInfo;
	}
	
	/**
	 * 获取网络请求的信息
	 * @param city
	 * @return
	 */
	private InputStream getConnection(String city){
		
	    httpUrl = httpUrl + "?" + "citypinyin=" + city.trim();
	    
	    HttpURLConnection connection = null;
	    
	    try {
	        URL url = new URL(httpUrl);
	        
	        connection = (HttpURLConnection) url.openConnection();
	        
	        connection.setRequestMethod("GET");

	        connection.setRequestProperty("apikey",  "bb5054b69475c3188d69d1f376baf1e4");
	        
	        connection.connect();
	        
	        return connection.getInputStream();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
		return null;
	}
	
	private String parseDataToString(InputStream ins) {
		
	    BufferedReader reader = null;
	    
	    String result = null;
	    
	    StringBuffer sbf = new StringBuffer();

	    try {
	        reader = new BufferedReader(new InputStreamReader(ins, charsetName));
	        
	        String strRead = null;
	        
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            
	            sbf.append(System.getProperty("line.separator"));
	        }
	        
	        result = sbf.toString();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }finally{
	    	try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    return result;
	}
	
	private class LoadWeatherInfoTask extends AsyncTask<String,Void,WeatherInfo>{

		@Override
		protected WeatherInfo doInBackground(String... params) {
			
			InputStream ins = getConnection(params[0]);
			
			String jsonData = parseDataToString(ins);
			
			return parseJSON(jsonData);
		}

		@Override
		protected void onPostExecute(WeatherInfo result) {
			flushWeatherInfo.handler(result.toString());
		}
	}
}
