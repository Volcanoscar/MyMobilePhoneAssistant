package com.cbooy.mmpa.test;

import java.util.List;

import android.test.AndroidTestCase;

import com.cbooy.mmpa.db.DBHelper;
import com.cbooy.mmpa.db.dao.UserDao;
import com.cbooy.mmpa.model.TestUser;

public class UnitTestCases extends AndroidTestCase {
	
	public void initDBTest(){
		DBHelper.initDB();
	}
	
	public void insertUser(){
		TestUser user = new TestUser();
		user.setName("haoc");
		
		UserDao.insert(user);
	}
	
	public void queryUser(){
		List<TestUser> users = UserDao.queryUser("haoc");
		
		if(users.size() > 0){
			
		}
	}
}
