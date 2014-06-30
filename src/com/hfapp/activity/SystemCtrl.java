package com.hfapp.activity;

import java.util.ArrayList;

import com.example.palytogether.R;
import com.hf.module.ModuleException;
import com.hf.soundSmartLink.MyAudioTrack;
import com.hf.zgbee.util.ZigbeeConfig;
import com.hf.zgbee.util.zigbeeModuleHelper;
import com.hf.zigbee.Info.ZigbeeNodeInfo;
import com.hfapp.view.ColorPickerDialog;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Paint.Join;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SystemCtrl extends Activity{
	
	private ListView z_list;
	private ImageButton z_add;
	private zigbeeModuleHelper zhelper;
	private ZBaseAdpt z_list_adpt;
	private String mac;
	private ArrayList<ZigbeeNodeInfo> znodes = new ArrayList<ZigbeeNodeInfo>();
	private AlertDialog  jionDialog;
	private Handler hand = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				z_list_adpt.notifyDataSetChanged();
				break;
			case 2:
				postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						jionDialog.dismiss();
					}
				}, 20000);
				break;
			case 3:
				jionDialog.show();
				break;
			case 4:
				jionDialog.dismiss();
				break;
			case 5:
				z_list_adpt.notifyDataSetChanged();
				break;
			case 6:
				Toast.makeText(SystemCtrl.this, "remove failed", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_ctrl_activity);
		mac = getIntent().getStringExtra("mac");
		zhelper = new zigbeeModuleHelper(mac);
		initActionbar();
		initView();
		jionDialog = new AlertDialog.Builder(this).create();
		//jionDialog.setCancelable(false);
		jionDialog.setCanceledOnTouchOutside(false);
		jionDialog.setTitle("Waiting.....");
		znodes = ZigbeeConfig.znodes.get(mac);
		if(znodes == null){
			znodes = new ArrayList<ZigbeeNodeInfo>();
		}
	}
	
	private void initActionbar() {
		// TODO Auto-generated method stub
		ActionBar bar = getActionBar();
		bar.setCustomView(R.layout.layout_actionbar);
		bar.setDisplayShowCustomEnabled(true);
		bar.setDisplayShowHomeEnabled(false);
		bar.setDisplayShowTitleEnabled(false);
		ImageView backBtn = (ImageView) findViewById(R.id.back);
		ImageView okBtn = (ImageView) findViewById(R.id.ok);
		okBtn.setVisibility(View.INVISIBLE);
		TextView title = (TextView) findViewById(R.id.tv_title);
		title.setText(R.string.sys_ctrl);

		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
			}
		});
	}
	
	
	private void initView() {
		// TODO Auto-generated method stub
		z_list = (ListView) findViewById(R.id.z_list);
		z_add = (ImageButton)findViewById(R.id.z_add);
		final ColorPickerDialog cp = new ColorPickerDialog(this, "asdf", null);
		z_list_adpt = new ZBaseAdpt();
		z_list.setItemsCanFocus(true);
		z_list.setAdapter(z_list_adpt);
		z_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//cp.show();
			}
		});
		z_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							hand.sendEmptyMessage(3);
							zhelper.startAddNodes(60);
							hand.sendEmptyMessage(2);
						} catch (ModuleException e) {
							// TODO Auto-generated catch block
							hand.sendEmptyMessage(4);
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
		hand.sendEmptyMessage(1);
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				try {
//					Log.i("SysCtrl", "getNodes");
//					znodes = zhelper.getAllNodes();
//					Log.i("SysCtrl", "getNodes:"+znodes.size());
//					hand.sendEmptyMessage(1);
//				} catch (ModuleException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}).start();
	}
	
	class ZBaseAdpt extends BaseAdapter{
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return znodes.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ZigbeeNodeInfo zinfo = znodes.get(position);
			convertView = LayoutInflater.from(SystemCtrl.this).inflate(R.layout.zigbee_list_item, null);
			ImageView zicon = (ImageView) convertView.findViewById(R.id.z_icon);
			ImageView zdelete = (ImageView) convertView.findViewById(R.id.z_delet);
			TextView zname = (TextView) convertView.findViewById(R.id.z_name);
			final ImageButton ok = (ImageButton) convertView.findViewById(R.id.z_ok);
			final ImageButton cancel= (ImageButton) convertView.findViewById(R.id.z_cancel);
			ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					doDeleteNode(position);
				}
			});
			
			cancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ok.setVisibility(View.INVISIBLE);
					cancel.setVisibility(View.INVISIBLE);
				}
			});
			zdelete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ok.setVisibility(View.VISIBLE);
					cancel.setVisibility(View.VISIBLE);
				}
			});
			return convertView;
		}
		private void doDeleteNode(final int pos) {
			// TODO Auto-generated method stub
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						zhelper.removeNodes(znodes.get(pos).getnw_addr());
						znodes.remove(pos);
						hand.sendEmptyMessage(5);
					} catch (ModuleException e) {
						// TODO Auto-generated catch block
						hand.sendEmptyMessage(6);
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}
