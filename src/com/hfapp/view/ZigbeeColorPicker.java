package com.hfapp.view;

import com.hf.zigbee.Info.ZigbeeNodeInfo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ZigbeeColorPicker extends View  {
	private Paint mpaint;
	private ZigbeeNodeInfo z ;
	private int x,y,r;
	private int color;
	private int selectColor = Color.RED;
	private int unselectColor = Color.GRAY;
	private int criColor =  Color.GRAY;
	public ZigbeeColorPicker(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}

	public ZigbeeColorPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public ZigbeeColorPicker(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		mpaint = new Paint();
		x = 40;
		y = 40;
		r = 40;
		mpaint.setColor(Color.RED);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		mpaint.reset();
		mpaint.setColor(criColor);
		mpaint.setAntiAlias(true);
		canvas.drawCircle(x, y, r, mpaint);
		mpaint.reset();
		mpaint.setColor(color);
		mpaint.setAntiAlias(true);
		canvas.drawCircle(x, y, r-5, mpaint);
	}


	
	public void setColor(int color){
		this.color = color;
		invalidate();
	}
	
	public void setSelect(){
		criColor = selectColor;
		invalidate();
	}
	public void setUnselect(){
		criColor = unselectColor;
		invalidate();
	}
	public void setZnodeInfo(ZigbeeNodeInfo z){
		this.z = z;
	}
	
	public ZigbeeNodeInfo getZigbeeNodeInfo(){
		return  this.z;
	}
}
