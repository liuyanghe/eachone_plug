package com.example.palytogether;

import java.util.ArrayList;

import noting.ViewPagerAdapter;

import com.example.config.Userconfig;
import com.example.palytogether.R;
import com.hfapp.activity.Login;
import com.hfapp.activity.ModuleList;
import com.hfapp.activity.SmartCtrler;
import com.hfapp.view.ViewPager0;
import com.hfapp.view.ViewPager1;
import com.hfapp.view.ViewPager2;
import com.hfapp.view.ViewPager3;
import com.hfapp.view.ViewPager4;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class NavViews extends Activity {
	
	//����ViewPager����
	private ViewPager viewPager;
	
	//����ViewPager������
	private ViewPagerAdapter vpAdapter;
	
	//����һ��ArrayList�����View
	private ArrayList<View> views;

	//����ͼƬ��Դ
//    private static final int[] pics = {R.drawable.view0_bg,R.drawable.view1_bg,R.drawable.view2_bg,R.drawable.view3_bg,R.drawable.view4_bg};
    
    //�ײ�С���ͼƬ
    private ImageView[] points;
    
    //��¼��ǰѡ��λ��
    private int currentIndex;
    
    private Handler hand = new Handler(){
    	
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_nav_activity);
		
		initView();
		initData();
	}
	
	/**
	 * ��ʼ�����
	 */
	private void initView(){
		//ʵ����ArrayList����
		views = new ArrayList<View>();
		
		//ʵ����ViewPager
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		
		//ʵ����ViewPager������
		vpAdapter = new ViewPagerAdapter(views);
	}
	
	/**
	 * ��ʼ������
	 */
	private void initData(){
//        LayoutInflater inflater = getLayoutInflater();
//        
//        for(int i=0; i<pics.length; i++) {
//        	View v = inflater.inflate(R.layout.item_view, null);
//            ImageView image = (ImageView)v.findViewById(R.id.image);
//            image.setImageResource(pics[i]);
//            views.add(v);
//        }
		
		View view0 = new ViewPager0(this);
		View view1 = new ViewPager1(this);
		View view2 = new ViewPager2(this);
		View view3 = new ViewPager3(this);
		View view4 = new ViewPager4(this);
       
//		viewPager.addView(view0);
		views.add(view0);
//        viewPager.addView(view1);
        views.add(view1);
//        viewPager.addView(view2);
        views.add(view2);
//        viewPager.addView(view3);
        views.add(view3);
//        viewPager.addView(view4);
        views.add(view4);
        
        //��������
        viewPager.setAdapter(vpAdapter);
        //���ü���
        viewPager.setOnPageChangeListener(new pageListener());
        
        //��ʼ���ײ�С��
        initPoint();
        
        
        Button btn = (Button) view4.findViewById(R.id.go);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Userconfig.isFristLogin()){
					Intent i = new Intent(NavViews.this,Login.class);
					startActivity(i);
					finish();
				}else{
					Intent i = new Intent(NavViews.this,ModuleList.class);
					startActivity(i);
					finish();
				}
			}
		});
	}
	
	/**
	 * ��ʼ���ײ�С��
	 */
	private void initPoint(){
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll);       
		
        points = new ImageView[views.size()];

        //ѭ��ȡ��С��ͼƬ
        for (int i = 0; i < views.size(); i++) {
        	//�õ�һ��LinearLayout�����ÿһ����Ԫ��
        	points[i] = (ImageView) linearLayout.getChildAt(i);
        	//Ĭ�϶���Ϊ��ɫ
        	points[i].setEnabled(true);
        	//��ÿ��С�����ü���
        	points[i].setOnClickListener(new pointListener());
        	//����λ��tag������ȡ���뵱ǰλ�ö�Ӧ
        	points[i].setTag(i);
        }
        
        //���õ���Ĭ�ϵ�λ��
        currentIndex = 0;
        //����Ϊ��ɫ����ѡ��״̬
        points[currentIndex].setEnabled(false);
	}

	private class pageListener implements OnPageChangeListener{

		/**
		 * ������״̬�ı�ʱ����
		 */
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}
		/**
		 * ����ǰҳ�汻����ʱ����
		 */
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		/**
		 * ���µ�ҳ�汻ѡ��ʱ����
		 */
		@Override
		public void onPageSelected(int position) {
			// ���õײ�С��ѡ��״̬
			setCurDot(position);
		}
		
	}
	
	private class pointListener implements OnClickListener{
		/**
		 * ͨ������¼����л���ǰ��ҳ��
		 */
		@Override
		public void onClick(View v) {
			int position = (Integer) v.getTag();
			setCurView(position);
			setCurDot(position);
		}
		
	}
	/**
	 * ���õ�ǰҳ���λ��
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= views.size()) {
			return;
		}
		viewPager.setCurrentItem(position);
	}
	
    /**
     * ���õ�ǰ��С���λ��
     */
    private void setCurDot(int positon){
         if (positon < 0 || positon > views.size() - 1 || currentIndex == positon) {
             return;
         }
         points[positon].setEnabled(false);
         points[currentIndex].setEnabled(true);
         currentIndex = positon;
     }

}

