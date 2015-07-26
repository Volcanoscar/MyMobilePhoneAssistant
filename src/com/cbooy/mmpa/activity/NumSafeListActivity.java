package com.cbooy.mmpa.activity;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cbooy.mmpa.R;
import com.cbooy.mmpa.db.dao.NumBlackListDao;
import com.cbooy.mmpa.db.dao.impl.NumBlackListDaoImpl;
import com.cbooy.mmpa.model.NumBlackListPojo;
import com.cbooy.mmpa.utils.StaticDatas;

public class NumSafeListActivity extends Activity implements OnClickListener{
	
	/**
	 * 展示 黑名单的 ListView
	 */
	private ListView lvNumSafeList;
	
	/**
	 * 黑名单数据
	 */
	private List<NumBlackListPojo> numLists;
	
	/**
	 * ListView 的 数据适配器
	 */
	private NumListAdapter numListAdapter;
	
	/**
	 * 操作黑名单的数据库接口操作类
	 */
	private NumBlackListDao blackListDao;
	
	/**
	 * 添加黑名单按钮
	 */
	private Button btnAddNum;
	
	/**
	 * 黑名单的 对话框
	 */
	private AlertDialog dialog = null;
	
	/**
	 * 对话框输入的 手机号码
	 */
	private EditText edtNum = null;
	
	/**
	 * 号码拨打的禁用模式
	 */
	private CheckBox chkBoxPhoto = null;
	
	/**
	 * 短信的禁用模式
	 */
	private CheckBox chkBoxSMS = null;
	
	/**
	 * 确定保存
	 */
	private Button btnOK = null;
	
	/**
	 * 取消保存
	 */
	private Button btnCancel = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.num_safe_list_activity);
		
		initDatas();
		
		initViews();
	}
	
	// 初始化 View
	private void initViews() {
		lvNumSafeList = (ListView) this.findViewById(R.id.lv_num_list);
		
		lvNumSafeList.setAdapter(numListAdapter);
		
		btnAddNum = (Button) this.findViewById(R.id.btn_add_black_num_list);
		
		btnAddNum.setOnClickListener(this);
		
		// 初始化 黑名单添加的对话框
		dialog = new Builder(this).create();
		
		View dialogView = View.inflate(this, R.layout.add_num_safe_list_dialog, null);
		
		edtNum = (EditText) dialogView.findViewById(R.id.et_enter_num);
		
		chkBoxPhoto = (CheckBox) dialogView.findViewById(R.id.chk_box_photo);
		
		chkBoxSMS = (CheckBox) dialogView.findViewById(R.id.chk_box_sms);
		
		btnOK = (Button) dialogView.findViewById(R.id.btn_ok_num);
		
		btnOK.setOnClickListener(this);
		
		btnCancel = (Button) dialogView.findViewById(R.id.btn_cancel_num);
		
		btnCancel.setOnClickListener(this);
		
		dialog.setView(dialogView);
	}
	
	// 初始化数据
	private void initDatas() {
		
		numListAdapter = new NumListAdapter();
		
		blackListDao = new NumBlackListDaoImpl(this);
		
		numLists = blackListDao.findAll();
	}
	
	@Override
	public void onClick(View v) {
		
		// 添加黑名单
		if(v.getId() == R.id.btn_add_black_num_list){
			dialog.show();
		}
		
		// 确定添加
		if(v.getId() == R.id.btn_ok_num){
			
			addNumberForClick();
		}
		
		// 取消 黑名单的 对话款 添加
		if(v.getId() == R.id.btn_cancel_num){
			dialog.dismiss();
			
			clearDialog();
		}
	}

	/**
	 * 点击时添加黑名单号码
	 */
	private void addNumberForClick() {
		String photoNumber = edtNum.getText().toString().trim();
		
		// 验证号码输入正确
		if("".equals(photoNumber)){
			Toast.makeText(this, "号码不可为空", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if(! photoNumber.matches("^1[34568]\\d{9}$")){
			Toast.makeText(this, "号码错误请重新输入", Toast.LENGTH_SHORT).show();
			return;
		}
		
		// 获取设置模式
		int mod = (chkBoxPhoto.isChecked() == true ? 1 : 0) + 
				(chkBoxSMS.isChecked() == true ? 2 : 0);
		
		if(mod == 0){
			Toast.makeText(this, "请选择模式", Toast.LENGTH_SHORT).show();
			return;
		}
		
		// 取消对话框
		dialog.dismiss();
		
		clearDialog();
		
		// 号码添加
		addNumber(photoNumber,String.valueOf(mod));
	}
	
	private void clearDialog() {
		edtNum.setText("");
		
		if(chkBoxPhoto.isChecked()){
			chkBoxPhoto.setChecked(false);
		}
		
		if(chkBoxSMS.isChecked()){
			chkBoxSMS.setChecked(false);
		}
	}

	/**
	 * 添加 黑名单
	 * @param photoNumber
	 */
	private void addNumber(String photoNumber,String mod) {
		
		// 插数据库
		NumBlackListPojo pojo = new NumBlackListPojo();
		pojo.setNum(photoNumber);
		pojo.setMod(mod);
		blackListDao.add(pojo);
		
		// 添加ListView的数据
		numLists.add(0, pojo);
		
		// 更新 Adapter
		numListAdapter.notifyDataSetChanged();
	}

	private class NumListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return numLists.size();
		}

		@Override
		public Object getItem(int position) {
			return numLists.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			
			ViewHolder viewHolder = null;
			
			if(convertView == null){
				convertView = View.inflate(NumSafeListActivity.this, R.layout.num_safe_list_item, null);
				
				viewHolder = new ViewHolder();
				
				viewHolder.tvNum = (TextView) convertView.findViewById(R.id.tv_num);
				
				viewHolder.tvMod =  (TextView) convertView.findViewById(R.id.tv_mod);
				
				viewHolder.imDel = (ImageView) convertView.findViewById(R.id.iv_del);
				
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			viewHolder.imDel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder confirmDialog = new Builder(NumSafeListActivity.this);
					
					confirmDialog.setTitle("删除提示");
					
					confirmDialog.setMessage("确定删除此黑名单号码吗?");
					
					confirmDialog.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,int which) {
									// 删除条目
									delNumber(position);
								}
							});
					
					confirmDialog.setNegativeButton("取消", null);
					
					confirmDialog.show();
				}
			});
			
			viewHolder.tvNum.setText(numLists.get(position).getNum());
			
			String mod = numLists.get(position).getMod();
			
			viewHolder.tvMod.setText(parse(mod));
			
			return convertView;
		}
		
		private String parse(String mod){
			if(StaticDatas.BLACK_LIST_PHONE_MOD.equals(mod)){
				return "电话拦截";
			}else if(StaticDatas.BLACK_LIST_SMS_MOD.equals(mod)){
				return "短信拦截";
			}else{
				return "全部拦截";
			}
		}
	}
	
	/**
	 * 删除条目
	 */
	private void delNumber(int position) {
		
		// 删除数据，数据库和ListView
		blackListDao.delete(numLists.remove(position).getNum());
		
		//更新 ListView数据
		numListAdapter.notifyDataSetChanged();
	}
	
	static class ViewHolder{
		TextView tvNum;
		TextView tvMod;
		ImageView imDel;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		lvNumSafeList = null;
		
		numLists = null;
		
		numListAdapter = null;
		
		blackListDao = null;
		
		btnAddNum = null;
		
		dialog = null;
		
		edtNum = null;
		
		chkBoxPhoto = null;
		
		chkBoxSMS = null;
		
		btnOK = null;
		
		btnCancel = null;
	}
}
