package com.cbooy.mmpa.test;

import java.util.List;

import android.test.AndroidTestCase;
import android.util.Log;

import com.cbooy.mmpa.db.NumBlackListDBHelper;
import com.cbooy.mmpa.db.dao.NumBlackListDao;
import com.cbooy.mmpa.db.dao.impl.NumBlackListDaoImpl;
import com.cbooy.mmpa.model.NumBlackListPojo;

public class BlackNumListTestCases extends AndroidTestCase {
	
	private final static String TAG = "BlackNumListTestCases";
	
	private NumBlackListDao blackListDao;

	public void testCreateDB(){
		NumBlackListDBHelper db = new NumBlackListDBHelper(getContext());
		db.getWritableDatabase();
	}
	
	public void testAdd(){
		blackListDao = new NumBlackListDaoImpl(getContext());
		
		for (int i = 100; i < 200; i++) {
			NumBlackListPojo pojo = new NumBlackListPojo();
			
			pojo.setNum("18523470" + i);
			
			pojo.setMod(String.valueOf(i % 3));
			
			blackListDao.add(pojo );
		}
	}
	
	public void testDel(){
		blackListDao = new NumBlackListDaoImpl(getContext());
		
		blackListDao.delete("18523470215");
	}
	
	public void testUpdate(){
		blackListDao = new NumBlackListDaoImpl(getContext());
		
		NumBlackListPojo pojo = new NumBlackListPojo();
		
		pojo.setNum("18523470215");
		
		pojo.setMod("3");
		
		blackListDao.update(pojo );
	}
	
	public void testFindAll(){
		blackListDao = new NumBlackListDaoImpl(getContext());
		
		List<NumBlackListPojo> pojos = blackListDao.findAll();
		
		Log.i(TAG, String.valueOf(pojos.size()));
		Log.i(TAG, pojos.toString());
	}
	
	public void testQueryOne(){
		blackListDao = new NumBlackListDaoImpl(getContext());
		
		NumBlackListPojo pojo = blackListDao.findOneByNum("18523470215");
		
		Log.i(TAG, pojo.toString());
	}
}
