package com.hfapp.activity;

import java.util.ArrayList;

import com.example.palytogether.R;
import com.hf.module.ModuleException;
import com.hf.module.impl.KeyValueHelper;
import com.hf.module.impl.LocalModuleInfoContainer;
import com.hf.module.info.ModuleInfo;
import com.hf.zgbee.util.Payload;
import com.hf.zgbee.util.ZigbeeConfig;
import com.hf.zgbee.util.zigbeeModuleHelper;
import com.hf.zigbee.Info.ZigbeeNodeInfo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ZigbeeModuleInfoActivity extends Activity implements OnClickListener{
	private ImageView icon;
	private ImageView scene_ctrl;
	private ImageView light_ctrl;
	private ImageView sys_ctrl;
	private ImageView help;
	private TextView moduleName;
	private ModuleInfo mi;
	private String mac;
	private boolean isonline;
	private boolean isopen;
	private ArrayList<ZigbeeNodeInfo> znodes = new ArrayList<ZigbeeNodeInfo>();
	Handler hand = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				setImage();
				break;
			case 2:
				Toast.makeText(ZigbeeModuleInfoActivity.this, "Ctrl err", Toast.LENGTH_SHORT).show();
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
		setContentView(R.layout.zigbee_info_activity);
		mac = getIntent().getStringExtra("mac");
		isonline = getIntent().getBooleanExtra("NL", false);
		isopen = getIntent().getBooleanExtra("NS", false);
		mi = LocalModuleInfoContainer.getInstance().get(mac);
		initActionbar();
		initView();
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
		TextView title = (TextView) findViewById(R.id.tv_title);
		title.setText(R.string.moduleinfo);
		okBtn.setVisibility(View.INVISIBLE);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
			}
		});
	}
	
	public void initView(){
		icon = (ImageView) findViewById(R.id.zigbee_icon);
		scene_ctrl = (ImageView) findViewById(R.id.scene_ctrl);
		light_ctrl = (ImageView) findViewById(R.id.light_ctrl);
		sys_ctrl = (ImageView) findViewById(R.id.sys_ctrl);
		moduleName = (TextView) findViewById(R.id.moduleinfo_module_name);
		moduleName.setText(mi.getName());
		help = (ImageView) findViewById(R.id.helper);
		icon.setOnClickListener(this);
		scene_ctrl.setOnClickListener(this);
		light_ctrl.setOnClickListener(this);
		sys_ctrl.setOnClickListener(this);
		help.setOnClickListener(this);
		znodes = ZigbeeConfig.znodes.get(mac);
		setImage();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i = new Intent();
		i.putExtra("mac", mi.getMac());
		switch (v.getId()) {
		case R.id.zigbee_icon:
			changeAllNodeStat();
			break;
		case R.id.scene_ctrl:
			break;
		case R.id.light_ctrl:
			i.setClass(this, LightCtrl.class);
			startActivity(i);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.sys_ctrl:
			i.setClass(this, SystemCtrl.class);
			startActivity(i);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.helper:
			i.setClass(this, ModuleModify.class);
			startActivity(i);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mi = LocalModuleInfoContainer.getInstance().get(mac);
		moduleName.setText(mi.getName());
	}
	private void changeAllNodeStat(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Payload pl = new Payload();
				pl.setAttr((byte) 0x00);
				try{
					if(isopen){
						for (int i = 0; i < znodes.size(); i++) {
							pl.setOnoff((byte) 0);
							pl.setNw_addr(znodes.get(i).getNw_addr());
							new zigbeeModuleHelper(mac).setNode(pl);
						}
						isopen = false;
					}else{
						for (int i = 0; i < znodes.size(); i++) {
							pl.setOnoff((byte) 1);
							pl.setNw_addr(znodes.get(i).getNw_addr());
							new zigbeeModuleHelper(mac).setNode(pl);
						}
						isopen = true;
					}
					hand.sendEmptyMessage(1);
				}catch(ModuleException e){
					hand.sendEmptyMessage(2);
				}
			}
		}).start();
		
	}
	
	
	private void setImage(){
		if(isonline){
			if(isopen){
				icon.setImageResource(ImageContentor.getOpenImageRs(KeyValueHelper.getInstence().get(mac).getIndex()));
			}else{
				icon.setImageResource(ImageContentor.getCloseImageRs(KeyValueHelper.getInstence().get(mac).getIndex()));
			}
		}else{
			icon.setImageResource(ImageContentor.getOutLineImageRs(KeyValueHelper.getInstence().get(mac).getIndex()));
		}
	}
}
