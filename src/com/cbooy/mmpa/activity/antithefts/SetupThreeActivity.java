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
		
		// bug: 从输入框获取号码判断
		if(TextUtils.isEmpty(sendSmsText.getText())){
			Toast.makeText(this, "安全号码未绑定", Toast.LENGTH_SHORT).show();
			return;
		}
		
		// 下一步之前保存号码
		Editor editor = sp.edit();
		
		editor.putString(StaticDatas.CONFIG_SAFE_PHONE, new StringBuilder().append(sendSmsText.getText()).toString());
		
		editor.commit();
		
		// 跳转到下一个页面
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
			}
		}
	}
}
