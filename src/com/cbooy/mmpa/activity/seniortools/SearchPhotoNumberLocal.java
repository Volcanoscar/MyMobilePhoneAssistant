package com.cbooy.mmpa.activity.seniortools;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.utils.SearchPhotoNumberUtil;

public class SearchPhotoNumberLocal extends Activity implements OnClickListener{

	private EditText etNum;
	
	private Button btnSearch;
	
	private TextView tvShowInfo;
	
	private String path = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.senior_tools_search_photonum_activity);
		
		etNum = (EditText) this.findViewById(R.id.et_number);
		
		btnSearch = (Button) this.findViewById(R.id.btn_search);
		
		tvShowInfo = (TextView) this.findViewById(R.id.tv_show_info);
		
		btnSearch.setOnClickListener(this);
		
		path = new StringBuilder(this.getFilesDir().getAbsolutePath()).append("/address.db").toString();
	}

	@Override
	public void onClick(View v) {
		// 点击搜索 按钮
		if(v.getId() == R.id.btn_search){
			String num = etNum.getText().toString().trim();
			
			if(!TextUtils.isEmpty(num)){
				//Log.i(StaticDatas.SEARCHPHOTONUMBERLOCAL_LOG_TAG, "search num is " + num);
				
				String address = SearchPhotoNumberUtil.searchLocation(path, num);
				
				tvShowInfo.setText(address);
				
			}else{
				Toast.makeText(this, "号码不能为空", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
