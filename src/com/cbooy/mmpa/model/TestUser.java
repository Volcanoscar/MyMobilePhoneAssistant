package com.cbooy.mmpa.model;

import org.litepal.crud.DataSupport;

public class TestUser extends DataSupport{
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "TestUser [id=" + id + ", name=" + name + "]";
	}
}
