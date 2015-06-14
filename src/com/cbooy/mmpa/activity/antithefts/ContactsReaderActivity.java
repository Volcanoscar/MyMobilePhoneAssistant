package com.cbooy.mmpa.activity.antithefts;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.utils.ContactsHelperUtil;

public class ContactsReaderActivity extends Activity implements OnItemClickListener{

	private ListView lvSelectContacts;
	
	private List<Map<String, String>> datas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.contacts_select_view);
		
		lvSelectContacts = (ListView) this.findViewById(R.id.lv_select_contact);
		
		datas = ContactsHelperUtil.getLocalContacts(this);
		
		lvSelectContacts.setAdapter(new SimpleAdapter(
				this, 
				datas,
				R.layout.contact_item_view, 
				new String[] { "name", "phone" },
				new int[] { R.id.contacts_name, R.id.contacts_phone }
			)
		);
		
		lvSelectContacts.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		Map<String,String> data = datas.get(position);
		
		String phone = data.get("phone");
		
		Intent result = new Intent();
		
		result.putExtra("phone", phone);
		
		setResult(1, result);
		
		finish();
	}
}
