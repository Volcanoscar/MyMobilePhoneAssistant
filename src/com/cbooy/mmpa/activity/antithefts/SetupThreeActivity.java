package com.cbooy.mmpa.activity.antithefts;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.utils.StaticDatas;

public class SetupThreeActivity extends BaseSetupActivity implements OnClickListener{

	private Button btnSelectContacts;
	
	private EditText sendSmsText;
	
	private String bindPhone = null;
	
	@Override
	public void init() {
		setContentView(R.layout.setup_three_activity);
		
		bindPhone = sp.getString(StaticDatas.CONFIG_SAFE_PHONE, null);

		preActivity = SetupTwoActivity.class;

		sendSmsText = (EditText) this.findViewById(R.id.send_sms_phone);
		
		if(!TextUtils.isEmpty(bindPhone)){
			sendSmsText.setText(bindPhone);
		}
		
		btnSelectContacts = (Button) this.findViewById(R.id.btn_select_contacts);
		
		btnSelectContacts.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this,ContactsReaderActivity.class);
		startActivityForResult(intent, 0);
	}
	
	@Override
	public void nextStep() {
		if(TextUtils.isEmpty(bindPhone)){
			Toast.makeText(this, "°²È«ºÅÂëÎ´°ó¶¨", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Intent intent = new Intent(this,SetupFourActivity.class);
		
		startActivity(intent);
		
		finish();
		
		overridePendingTransition(R.anim.next_tran_in, R.anim.next_tran_out);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == 0 && resultCode == 1){
			bindPhone = data.getStringExtra("phone");
			
			if(!TextUtils.isEmpty(bindPhone)){
				sendSmsText.setText(bindPhone);
				
				Editor editor = sp.edit();
				
				editor.putString(StaticDatas.CONFIG_SAFE_PHONE, new StringBuilder().append("+86").append(bindPhone).toString());
				
				editor.commit();
			}
		}
	}
}
