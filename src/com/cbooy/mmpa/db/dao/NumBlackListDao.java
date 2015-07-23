package com.cbooy.mmpa.db.dao;

import java.util.List;

import com.cbooy.mmpa.model.NumBlackListPojo;

public interface NumBlackListDao {
	
	public void add(NumBlackListPojo pojo);
	
	public void delete(int id);
	
	public void update(NumBlackListPojo pojo);
	
	public NumBlackListPojo findOneById(int id);
	
	public NumBlackListPojo findOneByNum(String num);
	
	public List<NumBlackListPojo> finnAll();
	
	public boolean isNumExists(String num);
}
