package com.cbooy.mmpa.db.dao.impl;

import java.util.List;

import android.content.Context;

import com.cbooy.mmpa.db.NumBlackListDBHelper;
import com.cbooy.mmpa.db.dao.NumBlackListDao;
import com.cbooy.mmpa.model.NumBlackListPojo;

public class NumBlackListDaoImpl implements NumBlackListDao {
	
	private NumBlackListDBHelper dbHelper;
	
	public NumBlackListDaoImpl(Context context){
		dbHelper = new NumBlackListDBHelper(context);
	}

	@Override
	public void add(NumBlackListPojo pojo) {
		
	}

	@Override
	public void delete(int id) {

	}

	@Override
	public void update(NumBlackListPojo pojo) {

	}

	@Override
	public NumBlackListPojo findOneById(int id) {
		return null;
	}

	@Override
	public NumBlackListPojo findOneByNum(String num) {
		return null;
	}

	@Override
	public List<NumBlackListPojo> finnAll() {
		return null;
	}

	@Override
	public boolean isNumExists(String num) {
		// TODO Auto-generated method stub
		return false;
	}

}
