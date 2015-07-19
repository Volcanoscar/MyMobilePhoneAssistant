package com.cbooy.mmpa.model;

/**
 * 天气信息的封装类
 * @author chenhao24
 *
 */
public class WeatherInfo {
	
	private String city;
	
	private String date;
	
	private String time;
	
	private String weather;
	
	private String lowTemp;
	
	private String highTemp;
	
	private String wd;
	
	private String ws;
	
	private String sunrise;
	
	private String sunset;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getLowTemp() {
		return lowTemp;
	}

	public void setLowTemp(String lowTemp) {
		this.lowTemp = lowTemp;
	}

	public String getHighTemp() {
		return highTemp;
	}

	public void setHighTemp(String highTemp) {
		this.highTemp = highTemp;
	}

	public String getWd() {
		return wd;
	}

	public void setWd(String wd) {
		this.wd = wd;
	}

	public String getWs() {
		return ws;
	}

	public void setWs(String ws) {
		this.ws = ws;
	}

	public String getSunrise() {
		return sunrise;
	}

	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	public String getSunset() {
		return sunset;
	}

	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		
		sb.append("当前城市:").append(city).append(" ")
			.append("天气发布时间:").append("20").append(date).append(":").append(time).append(" ")
				.append("天气情况:").append(weather).append(" ")
				.append("最高气温:").append(highTemp).append("℃").append(" ")
				.append("最低气温:").append(lowTemp).append("℃").append(" ")
				.append("风向:").append(wd).append(" ")
				.append("风力:").append(ws).append(" ")
				.append("日出时间:").append(sunrise).append(" ")
				.append("日落时间:").append(sunset).append(" ");

		return sb.toString();
	}
	
}
