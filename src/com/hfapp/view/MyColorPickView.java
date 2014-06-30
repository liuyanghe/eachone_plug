package com.hfapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class MyColorPickView extends ImageView{
	Bitmap bm;
	public MyColorPickView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		initView();
	}

	public MyColorPickView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	public MyColorPickView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		this.setScaleType(ScaleType.FIT_XY);
		bm = Bitmap.createBitmap(765, 255, Config.RGB_565);
		for (int i = 0; i < 765; i++) {
			for (int j = 0; j < 255; j++) {
				bm.setPixel(i, 254-j, getColor(i, j));
			}
		}	
		this.setImageBitmap(bm);
	}
	
	private  int getColor(int x,int y){
		double r = 0 , g =0,b =0;
		if(x<255){
			r = 255 - x;
			g = x;
			b=0;
			
		}else if(x<510){
			r = 0;
			g = 510 - x;
			b = x - 255;
		}else if(x< 765){
			r = x - 510;
			g = 0;
			b = 765 - x;
		}
		r = r + (255 - r)/255*y;
		g = g + (255 - g)/255*y;
		b = b + (255 - b)/255*y;
		double v = getVal(r, g, b);
		return Color.rgb((int)(r*v), (int)(g*v), (int)(b*v));
	}
	
	private double getVal(double r,double g,double b){
		if(r>g){
			if(r>b){
				return 255/r;
			}else{
				return 255/b;
			}
		}else{
			if(g>b){
				return 255/g;
			}else{
				return 255/b;
			}
		}
	}
}
