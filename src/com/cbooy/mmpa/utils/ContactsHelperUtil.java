package com.cbooy.mmpa.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class ContactsHelperUtil {
	
	/**
	 * 读取 联系人
	 * @param context
	 * @return
	 */
	public static List<Map<String, String>> getLocalContacts(Context context) {
		
		List<Map<String, String>> datas = new ArrayList<Map<String,String>>();
		
		ContentResolver cr = context.getContentResolver();
		
		Uri uriRawContacts = Uri.parse("content://com.android.contacts/raw_contacts");
		
		Uri uriData = Uri.parse("content://com.android.contacts/data");
		
		Cursor rawContactsCursor = cr.query(uriRawContacts, 
				new String[] { "contact_id"}, 
				null, 
				null, 
				null);
		
		String data1 = null;
		
		String mimetype = null;
		
		while(rawContactsCursor.moveToNext()){
			String contactId = rawContactsCursor.getString(rawContactsCursor.getColumnIndex("contact_id"));
			
			if(contactId != null){
				
				Map<String, String> data = new HashMap<String,String>();
				
				Cursor dataCursor = cr.query(uriData, 
						new String[]{"data1","mimetype"}, 
						"contact_id=?", 
						new String[]{contactId}, 
						null);
				
				while(dataCursor.moveToNext()){
					
					data1 = dataCursor.getString(dataCursor.getColumnIndex("data1"));
					mimetype = dataCursor.getString(dataCursor.getColumnIndex("mimetype"));
					
					if("vnd.android.cursor.item/name".equals(mimetype)){
						data.put("name", data1);
						//Log.i(StaticDatas.CONTACTSHELPERUTIL_LOG_TAG, "name is " + data1);
					}else if("vnd.android.cursor.item/phone_v2".equals(mimetype)){
						data.put("phone", data1);
						//Log.i(StaticDatas.CONTACTSHELPERUTIL_LOG_TAG, "phone is " + data1);
					}
				}
				
				datas.add(data);
				
				dataCursor.close();
			}
		}
		
		rawContactsCursor.close();
		
		return datas;
	}
}
