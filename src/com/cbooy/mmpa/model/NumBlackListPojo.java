package com.cbooy.mmpa.model;

public class NumBlackListPojo {

	private int id;
	
	private String num;
	
	private String mod;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	/**
	 * 1.电话
	 * 2.短信
	 * 3.全部
	 * @return
	 */
	public String getMod() {
		return mod;
	}

	public void setMod(String mod) {
		this.mod = mod;
	}

	@Override
	public String toString() {
		return "NumBlackListPojo [id=" + id + ", num=" + num + ", mod=" + mod
				+ "]";
	}
}
