package com.cbooy.mmpa.db.dao;

import java.util.List;

import com.cbooy.mmpa.model.NumBlackListPojo;

public interface NumBlackListDao {
	
	public long add(NumBlackListPojo pojo);
	
	public int delete(String num);
	
	public int update(NumBlackListPojo pojo);
	
	public NumBlackListPojo findOneByNum(String num);
	
	public List<NumBlackListPojo> findAll();
	
	public boolean isNumExists(String num);
}
