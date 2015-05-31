package com.cbooy.mmpa.db.dao;

import java.util.List;

import org.litepal.crud.DataSupport;

import com.cbooy.mmpa.model.TestUser;

public class UserDao{
	
	public static void insert(TestUser user){
		user.save();
	}
	
	public static List<TestUser> queryUser(String name){
		return DataSupport.where("name=?",name).find(TestUser.class);
	}
}
