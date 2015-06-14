package com.cbooy.mmpa.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.activity.views.AntiTheftDialog;
import com.cbooy.mmpa.utils.StaticDatas;

@SuppressLint("HandlerLeak")
public class HomeActivity extends Activity {

	private GridView gvFuncLists;

	private SharedPreferences sp;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			// 确认
			if (msg.what == StaticDatas.ANTITHEFT_DIALOG_CONFIRM) {
				boolean isConfirm = (boolean) msg.obj;

				if (isConfirm) {
					goAntiTheftActivity();
				}
			}

			// 输入
			if (msg.what == StaticDatas.ANTITHEFT_DIALOG_ENTER) {
				boolean isEnter = (boolean) msg.obj;

				if (isEnter) {
					goAntiTheftActivity();
				}
			}
		}
	};

	private String[] names = new String[] { "手机防盗", "通讯卫士", "软件管理", "进程管理",
			"流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心" };

	private int[] ids = new int[] { R.drawable.safe, R.drawable.callmsgsafe,
			R.drawable.app, R.drawable.taskmanager, R.drawable.netmanager,
			R.drawable.trojan, R.drawable.sysoptimize, R.drawable.atools,
			R.drawable.settings };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home_activity);

		gvFuncLists = (GridView) this.findViewById(R.id.gv_func_lists);

		gvFuncLists.setAdapter(new MyAdapter());

		sp = getSharedPreferences(StaticDatas.SP_CONFIG_FILE, MODE_PRIVATE);

		gvFuncLists.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// 进入 设置中心
				if (8 == position) {
					Intent intent = new Intent(HomeActivity.this,SettingActivity.class);

					startActivity(intent);
				}

				// 手机防盗
				if (0 == position) {

					// 取出密码
					String oldPasswd = sp.getString(StaticDatas.CONFIG_PASSWD, null);

					// 没有设置,弹出 输入密码并确认
					if (TextUtils.isEmpty(oldPasswd)) {
						new AntiTheftDialog(HomeActivity.this, handler).confirmDialog();
					} else {
						// 已经设置，弹出输入密码
						new AntiTheftDialog(HomeActivity.this, handler).enterPasswd(oldPasswd);
					}
				}
			}
		});
	}

	/**
	 * 切换到 防盗页面
	 */
	protected void goAntiTheftActivity() {
		Intent intent = new Intent(this, AntiTheftActivity.class);

		startActivity(intent);
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView != null) {
				return convertView;
			}

			View item = View.inflate(HomeActivity.this,R.layout.home_func_list_item, null);

			ImageView imgItem = (ImageView) item.findViewById(R.id.img_func_list_item);

			imgItem.setImageResource(ids[position]);

			TextView tvItem = (TextView) item.findViewById(R.id.tv_func_list_item);

			tvItem.setText(names[position]);

			return item;
		}

	}
}
