package com.cbooy.mmpa.db.dao.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cbooy.mmpa.db.NumBlackListDBHelper;
import com.cbooy.mmpa.db.dao.NumBlackListDao;
import com.cbooy.mmpa.model.NumBlackListPojo;

public class NumBlackListDaoImpl implements NumBlackListDao {
	
	private static final String TABLE = "black_list";
	
	private NumBlackListDBHelper dbHelper;
	
	public NumBlackListDaoImpl(Context context){
		dbHelper = new NumBlackListDBHelper(context);
	}

	@Override
	public long add(NumBlackListPojo pojo) {
		SQLiteDatabase dataBase = dbHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("photo_num", pojo.getNum());
		
		values.put("mod", pojo.getMod());
		
		long res = dataBase.insert(TABLE, null, values);
		
		dataBase.close();
		
		return res;
	}

	@Override
	public int delete(String num) {
		SQLiteDatabase dataBase = dbHelper.getWritableDatabase();
		
		int res = dataBase.delete(TABLE, "photo_num = ?", new String[]{num});
		
		dataBase.close();
		
		return res;
	}

	@Override
	public int update(NumBlackListPojo pojo) {
		SQLiteDatabase dataBase = dbHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("photo_num", pojo.getNum());
		
		values.put("mod", pojo.getMod());
		
		int res = dataBase.update(TABLE, values , "photo_num = ?", new String[]{pojo.getNum()});
		
		dataBase.close();
		
		return res;
	}

	@Override
	public NumBlackListPojo findOneByNum(String num) {
		
		SQLiteDatabase dataBase = dbHelper.getReadableDatabase();
		
		NumBlackListPojo pojo = new NumBlackListPojo();
		
		Cursor cursor = dataBase.query(TABLE, 
				new String[]{"photo_num","mod"}, 
				"photo_num = ?", 
				new String[]{num}, 
				null, 
				null, 
				null);
		
		if(cursor.moveToFirst()){
			pojo.setNum(cursor.getString(0));
			pojo.setMod(cursor.getString(1));
		}
		
		cursor.close();
		
		dataBase.close();
		
		return pojo;
	}

	@Override
	public List<NumBlackListPojo> findAll() {
		
		List<NumBlackListPojo> pojos = new ArrayList<NumBlackListPojo>();
		
		SQLiteDatabase dataBase = dbHelper.getReadableDatabase();
		
		Cursor cursor = dataBase.query(TABLE, new String[]{"photo_num","mod"}, null, null, null, null, null);
		
		while(cursor.moveToNext() && cursor.getColumnCount() > 0){
			NumBlackListPojo pojo = new NumBlackListPojo();
			pojo.setNum(cursor.getString(0));
			pojo.setMod(cursor.getString(1));
			
			pojos.add(pojo);
		}
		
		cursor.close();
		
		dataBase.close();
		
		return pojos;
	}

	@Override
	public boolean isNumExists(String num) {
		
		if(findOneByNum(num) == null){
			return false;
		}
		
		return true;
	}
}
