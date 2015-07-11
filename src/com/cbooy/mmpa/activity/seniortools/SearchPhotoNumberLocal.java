package com.cbooy.mmpa.activity.seniortools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.utils.SearchPhotoNumberUtil;
import com.cbooy.mmpa.utils.StaticDatas;

@SuppressLint("HandlerLeak")
public class SearchPhotoNumberLocal extends Activity implements OnClickListener{

	private EditText etNum;
	
	private Button btnSearch;
	
	private TextView tvShowInfo;
	
	private String path = null;
	
	private TextView cleanNum;
	
	private Vibrator vibrator; 
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			if(msg.what == StaticDatas.LOAD_ADDRESS_SUCCESS){
				tvShowInfo.setText((String)msg.obj);
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.senior_tools_search_photonum_activity);
		
		etNum = (EditText) this.findViewById(R.id.et_number);
		
		cleanNum = (TextView) this.findViewById(R.id.iv_clean);
		
		cleanNum.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				etNum.setText("");
			}
		});
		
		etNum.addTextChangedListener(new TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,int count) {
				
				cleanNum.setVisibility(View.VISIBLE);
				
				if(String.valueOf(s).trim().length() >= 3){
					
					new SearchPhotoNumberUtil().searchLocation(path, String.valueOf(s),new AddressHandlerCallBack(){

						@Override
						public void call(String address) {
							
							Message msg = Message.obtain();
							
							msg.what = StaticDatas.LOAD_ADDRESS_SUCCESS;
							
							msg.obj = address;
							
							handler.sendMessage(msg);
						}});
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				
			}});
		
		btnSearch = (Button) this.findViewById(R.id.btn_search);
		
		tvShowInfo = (TextView) this.findViewById(R.id.tv_show_info);
		
		btnSearch.setOnClickListener(this);
		
		path = new StringBuilder(this.getFilesDir().getAbsolutePath()).append("/address.db").toString();
		
		vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE); 
	}

	@Override
	public void onClick(View v) {
		// 点击搜索 按钮
		if(v.getId() == R.id.btn_search){
			String num = etNum.getText().toString().trim();
			
			if(!TextUtils.isEmpty(num)){
				//Log.i(StaticDatas.SEARCHPHOTONUMBERLOCAL_LOG_TAG, "search num is " + num);
				
				new SearchPhotoNumberUtil().searchLocation(path, num,new AddressHandlerCallBack(){

					@Override
					public void call(String address) {
						
						Message msg = Message.obtain();
						
						msg.what = StaticDatas.LOAD_ADDRESS_SUCCESS;
						
						msg.obj = address;
						
						handler.sendMessage(msg);
					}});
			}else{
				Toast.makeText(this, "号码不能为空", Toast.LENGTH_SHORT).show();
				
				// 调用震动
				long[] pattern = {200,200,300,300};   
				
		        vibrator.vibrate(pattern,-1);
				
				Animation shake = AnimationUtils.loadAnimation(this,R.anim.shake);
				
				etNum.startAnimation(shake);
			}
		}
	}
}
