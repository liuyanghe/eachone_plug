package com.hfapp.view;

import java.util.ArrayList;
import java.util.HashMap;

import com.hf.module.ModuleException;
import com.hf.module.impl.KeyValueHelper;
import com.hf.module.info.GPIO;
import com.hf.module.info.ModuleInfo;
import com.hf.zgbee.util.Payload;
import com.hf.zgbee.util.ZigbeeConfig;
import com.hf.zgbee.util.zigbeeModuleHelper;
import com.hf.zigbee.Info.StatusInfo;
import com.hf.zigbee.Info.ZigbeeNodeInfo;
import com.hfapp.activity.ImageContentor;
import com.hfapp.activity.ZigbeeModuleInfoActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

public class ZigbeeModuleView extends BaseModuleView{


	private Handler hand = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				setImage();
				break;
			case 2:
				Toast.makeText(getContext(), "ctrl err", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};
	
	private boolean isonline = false;
	private boolean isopen = false;
	
	private ArrayList<ZigbeeNodeInfo> nodes = new ArrayList<ZigbeeNodeInfo>();
	
	public ZigbeeModuleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public ZigbeeModuleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ZigbeeModuleView(Context context, boolean which) {
		super(context, which);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	protected void updateStatus() {
		// TODO Auto-generated method stub
		//moduleImage.setImageResource(resId);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					nodes = new zigbeeModuleHelper(m_moduleinfo.getMac()).getAllNodes();
					ZigbeeConfig.znodes.put(m_moduleinfo.getMac(), nodes);
					for(ZigbeeNodeInfo z:nodes){
						isopen|=z.getOnoffstat();
					}
					isonline = true;
				} catch (ModuleException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//hand.sendEmptyMessage(2);
					isonline = false;
				}
				
				StatusInfo s = new StatusInfo();
				s.isonline = isonline;
				s.isopen = isopen;
				ModuleListView.stats.put(m_moduleinfo.getMac(), s);
				hand.sendEmptyMessage(1);
			}
		}).start();
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		super.initView();
		moduleImage.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getContext(),ZigbeeModuleInfoActivity.class);
				i.putExtra("mac", m_moduleinfo.getMac());
				i.putExtra("NS", isopen);
				i.putExtra("NL", isonline);
				getContext().startActivity(i);
				return false;
			}
		});
		moduleImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				changeAllNodeStat();
			}
		});
	}

	@Override
	public void onEvent(String mac, byte[] t2data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCloudLogin(boolean loginstat) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCloudLogout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNewDevFind(ModuleInfo mi) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGPIOEvent(String mac, HashMap<Integer, GPIO> GM) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTimerEvent(String mac, byte[] t2data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUARTEvent(String mac, byte[] userData, boolean chanle) {
		// TODO Auto-generated method stub
		
	}
	private void setImage(){
		if(isonline){
			if(isopen){
				moduleImage.setBackgroundResource(ImageContentor.getOpenImageRs(KeyValueHelper.getInstence().get(m_moduleinfo.getMac()).getIndex()));
			}else{
				moduleImage.setBackgroundResource(ImageContentor.getCloseImageRs(KeyValueHelper.getInstence().get(m_moduleinfo.getMac()).getIndex()));
			}
		}else{
			moduleImage.setBackgroundResource(ImageContentor.getOutLineImageRs(KeyValueHelper.getInstence().get(m_moduleinfo.getMac()).getIndex()));
		}
	}
	
	private void changeAllNodeStat(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Payload pl = new Payload();
				pl.setAttr((byte) 0x00);
				if(nodes.size()<=0)
				{
					 isopen = false;
					 hand.sendEmptyMessage(1);
					 return;
				}
				try{
					if(isopen){
						for (int i = 0; i < nodes.size(); i++) {
							pl.setOnoff((byte) 0);
							pl.setNw_addr(nodes.get(i).getNw_addr());
							new zigbeeModuleHelper(m_moduleinfo.getMac()).setNode(pl);
						}
						isopen = false;
					}else{
						for (int i = 0; i < nodes.size(); i++) {
							pl.setOnoff((byte) 1);
							pl.setNw_addr(nodes.get(i).getNw_addr());
							new zigbeeModuleHelper(m_moduleinfo.getMac()).setNode(pl);
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
	
}
