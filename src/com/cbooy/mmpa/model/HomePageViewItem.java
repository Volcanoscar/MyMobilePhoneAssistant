package com.cbooy.mmpa.model;

/**
 * HomePage 展示列表时的domain类
 * @author chenhao24
 *
 */
public class HomePageViewItem {
	private String name;
	
	private int resourceId;
	
	public HomePageViewItem() {
		
	}
	
	public HomePageViewItem(String name, int resourceId) {
		this.name = name;
		this.resourceId = resourceId;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getResourceId() {
		return resourceId;
	}
	
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
}
