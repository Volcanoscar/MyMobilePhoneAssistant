package com.cbooy.mmpa.activity.views;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cbooy.mmpa.R;

public class FlowIconView extends LinearLayout {

	private Context context;
	
	private String onLineCount;
	
	private Map<String,Integer> resources = new HashMap<String,Integer>();
	
	public FlowIconView(Context context, AttributeSet attrs, int defStyle) {
		super(context,attrs,defStyle);
		
		this.context = context;
		
		String namespace = "http://schemas.android.com/apk/res/com.cbooy.mmpa";
		
		onLineCount = attrs.getAttributeValue(namespace, "show_num");
		
		// 初始化数据
		initData();
		
		// 初始化 视图
		init();
	}

	public FlowIconView(Context context) {
		this(context,null);
	}

	public FlowIconView(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}
	
	private void initData() {
		resources.put("1", R.id.iv_icon_1);
		resources.put("2", R.id.iv_icon_2);
		resources.put("3", R.id.iv_icon_3);
		resources.put("4", R.id.iv_icon_4);		
	}

	private void init(){
		View view = View.inflate(context, R.layout.flow_icon_view, this);
		
		ImageView imageView = (ImageView) view.findViewById(resources.get(onLineCount));
		
		imageView.setImageResource(android.R.drawable.presence_online);
	}
}
