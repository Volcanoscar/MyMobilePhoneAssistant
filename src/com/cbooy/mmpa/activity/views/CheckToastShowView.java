package com.cbooy.mmpa.activity.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cbooy.mmpa.R;

public class CheckToastShowView extends RelativeLayout {

	private TextView textTitle;
	
	private TextView textDesc;
	
	private ImageView imPress;
	
	private String title;
	
	private String clickOn;
	
	private String clickOff;
	
	public CheckToastShowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		String namespace = "http://schemas.android.com/apk/res/com.cbooy.mmpa";
		
		title = attrs.getAttributeValue(namespace ,"title");
		
		clickOn = attrs.getAttributeValue(namespace ,"click_on");
		
		clickOff = attrs.getAttributeValue(namespace ,"click_off");
		
		initView(context);
	}

	private void initView(Context context) {
		View view = View.inflate(context, R.layout.check_address_item, this);
		
		textTitle = (TextView) view.findViewById(R.id.tv_title);
		
		textTitle.setText(title);
		
		textDesc = (TextView) view.findViewById(R.id.tv_desc);
		
		imPress = (ImageView) view.findViewById(R.id.im_press);
	}
	
	/**
	 * 设置是否被点击
	 * @param checked
	 */
	public void setCheckd(boolean checked){
		if(checked){
			textDesc.setText(clickOn);
		}else{
			textDesc.setText(clickOff);
		}
	}
}
