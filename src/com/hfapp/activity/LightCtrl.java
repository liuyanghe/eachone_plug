package com.hfapp.activity;

import java.util.ArrayList;
import java.util.Random;

import com.example.palytogether.R;
import com.hf.lib.util.HexBin;
import com.hf.module.ModuleException;
import com.hf.module.info.ModuleInfo;
import com.hf.zgbee.util.Payload;
import com.hf.zgbee.util.ZigbeeConfig;
import com.hf.zgbee.util.zigbeeModuleHelper;
import com.hf.zigbee.Info.ZigbeeNodeInfo;
import com.hfapp.view.ZigbeeColorPicker;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.transition.ChangeBounds;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class LightCtrl extends Activity{
	private final  double RPRA11 = 2.768892;
	private final  double GPRA11 = 1.751748;
	private final  double BPRA11 = 1.13016;
	private final  double RPRA12 = 3.768892;
	private final  double GPRA12 = 6.398956;
	private final  double BPRA12 = 6.784552;
	
	private final  double RPRA21 = 1;
	private final  double GPRA21 = 4.5907;
	private final  double BPRA21 = 0.0601;
	private final  double RPRA22 = 3.768892;
	private final  double GPRA22 = 6.398956;
	private final  double BPRA22 = 6.784552;
	
	
	long levelstartTime;
	long levelnowTime;
	long startTime = 0;
	long nowTime = 0;
	long perTime = 100;
	
	private String mac;
	private ModuleInfo mi;
	private RelativeLayout colorContent;
	private ImageView color;
	private SeekBar level;
	int[] colorlocaltion = new int[2];
	int thiscolor = Color.WHITE;
	private zigbeeModuleHelper zhlper;
	private ArrayList<ZigbeeNodeInfo> znodes = new ArrayList<ZigbeeNodeInfo>();
	private ArrayList<ZigbeeColorPicker> zcps = new ArrayList<ZigbeeColorPicker>();
	private TextView text;
	
	private ZigbeeColorPicker currPIC;
	
	private int range_width;
	private int range_hight;
	private int picker_w,picker_h;
	
	
	private boolean colorisSending = false;
	
	private int leveldefPro = 0;
	
	private Bitmap colorBitmap;
	private  int colorBitmapwidth,colorBitmaphight;
	private Handler hand = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				if(znodes==null)
					break;
				if(znodes.size()>0){
					for(ZigbeeColorPicker zcp :zcps){
						colorContent.removeView(zcp);
					}
					for(int i = 0;i<znodes.size();i++){
						ZigbeeColorPicker zc =  new ZigbeeColorPicker(LightCtrl.this);
						zc.setZnodeInfo(znodes.get(i));
						if(currPIC!=null){
							currPIC.setUnselect();
						}
						currPIC = zc;
						zc.setSelect();
						if(znodes.get(i).getOnline_state()!=3)
							continue;
						
						Log.e("location", HexBin.bytesToStringWithSpace(znodes.get(i).getDeviceStates()));
						Location l = getColorLocation((znodes.get(i).getcolorX()&0x00ffff),(znodes.get(i).getcolorY()&0xffff));
						 RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(80,80);
						 lp1.leftMargin = l.x;
						 lp1.topMargin = l.y;
						 text.setBackgroundColor(getColor(l.x,l.y));
						 zc.setColor(getColor(l.x,l.y));
						 colorContent.addView(zc ,lp1);
						zcps.add(zc);
						if(znodes.get(i).getType()!=1)
						{
							leveldefPro = znodes.get(i).getDeviceStates()[1];
							level.setProgress(leveldefPro);
						}
						zc.setOnTouchListener(new OnTouchListener() {
							int lastX, lastY;//������������	
							
							@Override
							public boolean onTouch(View v, MotionEvent event) {
								

								
								int ea=event.getAction(); 
								int left,right,top,bottom;
								if(range_width==0){//��������
									
									range_width = color.getWidth();
									range_hight = color.getHeight();
									
									picker_h = v.getHeight();
									picker_w = v.getWidth();
								}
								switch(ea){
								case MotionEvent.ACTION_DOWN://����
								//	Log.e("picker", "ACTION_DOWN1");
									
									startTime = System.currentTimeMillis();
									nowTime = System.currentTimeMillis();
									
									
									if(currPIC!=null){
										currPIC.setUnselect();
									}
									currPIC = (ZigbeeColorPicker) v;
									currPIC.setSelect();
									lastX = (int) event.getRawX();
									lastY = (int) event.getRawY(); 
									hand.sendEmptyMessage(3);
									
									//colorContent.requestDisallowInterceptTouchEvent(true);//����������������������touch����
									break;
								case MotionEvent.ACTION_MOVE://����
								//	Log.e("picker", "ACTION_MOVE");
									int dx =(int)event.getRawX() - lastX;
									int dy =(int)event.getRawY() - lastY;	
									
									left = v.getLeft() + dx;
									top = v.getTop() + dy;
									right = v.getRight() + dx;
									bottom = v.getBottom() + dy;	
									
									if(left < color.getLeft() ){
										left = 0;
										right = left+v.getWidth();
									}
									if(top < color.getTop()){
										top = 0;
										bottom = top + v.getHeight();
									}
									
									if(right > color.getRight()){
										right = color.getRight();
										left = right - v.getWidth(); 
									}
									
									if(bottom > color.getBottom()){
										bottom = color.getBottom();
										top = bottom - v.getHeight();
									}
									
									int picker_centreX = right-picker_w/2;
									int picker_centreY = bottom-picker_h/2;
								
									
									int x = picker_centreX - color.getLeft();
									int y = picker_centreY - color.getTop();
									y = y+30; // У׼
									thiscolor = getColor(x, y);
									((ZigbeeColorPicker)v).setColor(thiscolor);
									v.layout(left, top, right, bottom);
									//Log.e("layout", left+","+top+","+right+","+bottom);
									lastX = (int) event.getRawX();
									lastY = (int) event.getRawY();
									//colorContent.requestDisallowInterceptTouchEvent(true);//����������������������touch����
									
									nowTime = System.currentTimeMillis();
									if(nowTime - startTime >perTime){
										startTime = nowTime;
										Log.e("demon","onmove");
										hand.sendEmptyMessage(4);	
										if(!colorisSending)
										{
											colorisSending = true;
											doSendColorConfig();
										}
									}
									
									break;
								case MotionEvent.ACTION_UP:
									Log.e("picker", "ACTION_UP");
									hand.sendEmptyMessage(4);
									doSendColorConfig();
									doSendColorConfig();
									doSendColorConfig();
									double all = Color.red(thiscolor)+Color.green(thiscolor)+Color.blue(thiscolor);
									//Log.e("demon", "r:"+Color.red(thiscolor)/all+" g:"+Color.green(thiscolor)/all+" b:"+Color.blue(thiscolor)/all);
//									colorContent.requestDisallowInterceptTouchEvent(true);
									break;
								default:
									break;
								}
								return true;
							}
						});
						
					}
				}

				break;
			case 2:
			case 3:
				level.setProgress(currPIC.getZigbeeNodeInfo().getLevle()&0x00ff);
				break;
			case 4:
				text.setBackgroundColor(thiscolor);
				//doSendColorConfig();
			default:
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zigbee_lightctrl_activity);
		mac = getIntent().getStringExtra("mac");
		zhlper = new zigbeeModuleHelper(mac);
		initActionbar();
		initViews();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		hand.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				/*
				 * �������� ���������� color������
				 * */
				hand.sendEmptyMessage(1);
			}
		}, 1000);
//		initData();
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
		title.setText(R.string.light_ctrl);
		okBtn.setVisibility(View.INVISIBLE);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	
	public int  getColor(int x ,int y){
		try {
			int width2 = color.getWidth();
			int height2 = color.getHeight();
			
			if(width2 - x <= 40){
				x = width2-1;
			}
			if(x< 40){
				x = 1;
			}
			
			int px = colorBitmapwidth*x/width2;
			int py = colorBitmaphight*y/height2;
			int color = colorBitmap.getPixel(px, py);
			//Log.e("demon", "r:"+Color.red(thiscolor)+" g:"+Color.green(thiscolor)+" b:"+Color.blue(thiscolor));
			return color;
		} catch (Exception e) {
			e.printStackTrace();
			return Color.WHITE;
		}
		
	}
	
	private void initViews(){
		
		
		znodes = ZigbeeConfig.znodes.get(mac);
		
		color = (ImageView) findViewById(R.id.color);
		level = (SeekBar) findViewById(R.id.level);
	//	zc = (ZigbeeColorPicker) findViewById(R.id.zc);
		colorContent = (RelativeLayout) findViewById(R.id.color_content);
		colorContent.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		text = (TextView) findViewById(R.id.text);
		text.setVisibility(View.GONE);
		colorBitmap  = BitmapFactory.decodeResource(getResources(), R.drawable.light_ctrl_2);
		colorBitmapwidth = colorBitmap.getWidth();
		colorBitmaphight = colorBitmap.getHeight();
		Log.d("fbb", "fbb bitmapWidth:"+colorBitmapwidth+" bitmapHeight:"+colorBitmaphight);
		level.setMax(255);
		level.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				doLevelCHange(arg0.getProgress());
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				levelstartTime = System.currentTimeMillis();
				levelnowTime = System.currentTimeMillis();
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				levelnowTime = System.currentTimeMillis();
				if(levelnowTime - levelstartTime > perTime){
					System.out.println(levelnowTime+":"+levelstartTime);
					levelstartTime = levelnowTime;
					doLevelCHange(arg1);
				}
			}
		});
	}
	private  void doLevelCHange(final int progress){
		Log.w("SeekBar", ""+progress);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				changelevel(progress);
			}
		}).start();
	}
	
	private synchronized void changelevel(int progress){
		Log.d("levelsend", "doLevelCHange");
		if(currPIC == null)
			return ;
		Payload pl = new Payload();
		pl.setAttr((byte) 0x01);
		pl.setLevel((byte) progress);
		pl.setNw_addr(currPIC.getZigbeeNodeInfo().getNw_addr());
		try {
			zhlper.setNode(pl);
			currPIC.getZigbeeNodeInfo().setLevel(progress);
			//hand.sendEmptyMessage(2);
		} catch (ModuleException e) {
			// TODO Auto-generated catch block
			//hand.sendEmptyMessage(3);
			e.printStackTrace();
		}
	}
//	private void initData(){
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				try {
//					znodes = zhlper.getAllNodes();
//					hand.sendEmptyMessage(1);
//				} catch (ModuleException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	} 
	
	
	private  void doSendColorConfig(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
					changeColor();
					colorisSending = false;
			}
		}).start();
	}
	
	private synchronized void changeColor(){
		Log.d("colorsend", "doSendColorConfig");
		Payload pl = new Payload();
		pl.setNw_addr(currPIC.getZigbeeNodeInfo().getNw_addr());
		pl.setAttr((byte) 0x03);
		pl.setColorx(getXY(thiscolor)[0]);
		pl.setColory(getXY(thiscolor)[1]);
		try {
			zhlper.setNode(pl);
			currPIC.getZigbeeNodeInfo().setcolorX(getXY(thiscolor)[0]);
			currPIC.getZigbeeNodeInfo().setcolorY(getXY(thiscolor)[1]);
		} catch (ModuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public short[] getXY(int thiscolor){
		short[] out = new short[2];
		int r = Color.red(thiscolor);
		int g = Color.green(thiscolor);
		int b = Color.blue(thiscolor);
		
		short X =   (short) (((r*RPRA11+g*GPRA11+b*BPRA11)/(r*RPRA12+g*GPRA12+b*BPRA12))*0xFFEF);
		short Y =   (short) (((r*RPRA21+g*GPRA21+b*BPRA21)/(r*RPRA22+g*GPRA22+b*BPRA22))*0xFFEF);
		
		out[0] = X;
		out[1] = Y;
		return out;
	}
	public static byte[] shortToByteArray(short s) {  
        byte[] targets = new byte[2];  
        for (int i = 0; i < 2; i++) {  
            int offset = (targets.length - 1 - i) * 8;  
            targets[i] = (byte) ((s >>> offset) & 0xff);  
        }  
        return targets;  
    }  
	
	
	class Location {
		int x;
		int y;
		Location(int x,int y){
			
			Log.e("location", x+":"+y);
			int width2 = color.getWidth()-80;
			int height2 = color.getHeight()-80;
			if(x>width2){
				x = width2;
			}
			if(y>height2)
				y = height2;
			this.x = x;
			this.y = y;
		}
	}
	
	public Location getColorLocation(int x,int y){
		
		int width2 = color.getWidth();
		int height2 = color.getHeight();
		int color;
		short[] xy;
		for (int i = 0; i < width2; i+=10) {
			for (int j = 0; j < height2; j+=40) {
				color = getColor(i, j);
				xy = getXY(color);
				if(x-xy[0]<10&&x-xy[0]>-10&&y-xy[1]>-10&&y-xy[1]<10){
					return new Location(i, j);
				}else if(x-xy[0]<100&&x-xy[0]>-100&&y-xy[1]>-100&&y-xy[1]<100){
					return new Location(i, j);
				}else if(x-xy[0]<500&&x-xy[0]>-500&&y-xy[1]>-500&&y-xy[1]<500){
					return new Location(i, j);
				}
			}
		}
		return new Location(new Random().nextInt(width2), new Random().nextInt(height2));
	}
}
