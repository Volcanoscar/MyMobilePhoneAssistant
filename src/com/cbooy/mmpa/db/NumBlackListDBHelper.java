package com.cbooy.mmpa.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NumBlackListDBHelper extends SQLiteOpenHelper {
	
	public NumBlackListDBHelper(Context context) {
		super(context, "num_black_list.db", null, 1 );
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTableSQL = "create table black_list(" +
				"_id integer primary key autoincrement," +
				"photo_num varchar(15)," +
				"mod varchar(2));";
		
		db.execSQL(createTableSQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
