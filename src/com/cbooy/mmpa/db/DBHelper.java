package com.cbooy.mmpa.db;

import org.litepal.tablemanager.Connector;

public class DBHelper {
	public static void initDB(){
		Connector.getWritableDatabase();
	}
}
