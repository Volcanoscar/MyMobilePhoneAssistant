package com.cbooy.mmpa.activity.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cbooy.mmpa.R;

public class ToastShowView extends RelativeLayout {

	private TextView textTitle;
	
	private TextView textDesc;
	
	private String title;
	
	private String desc;
	
	public ToastShowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		String namespace = "http://schemas.android.com/apk/res/com.cbooy.mmpa";
		
		title = attrs.getAttributeValue(namespace ,"title");
		
		desc = attrs.getAttributeValue(namespace ,"desc");
		
		initView(context);
	}

	private void initView(Context context) {
		View view = View.inflate(context, R.layout.check_address_item, this);
		
		textTitle = (TextView) view.findViewById(R.id.tv_title);
		
		textTitle.setText(title);
		
		textDesc = (TextView) view.findViewById(R.id.tv_desc);
		
		textDesc.setText(desc);
	}
	
	public void setDesc(String desc){
		textDesc.setText(desc);
	}
}
