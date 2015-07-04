package com.cbooy.mmpa.activity.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 自动具有焦点
 * @author chenhao24
 *
 */
public class FocusedTextView extends TextView {

	public FocusedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
	 * 默认获得焦点
	 */
	@Override
	public boolean isFocused() {
		return true;
	}
}
