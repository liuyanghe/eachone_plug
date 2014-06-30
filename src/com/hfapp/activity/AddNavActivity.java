package com.hfapp.activity;

import com.example.palytogether.R;
import com.example.palytogether.R.color;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class AddNavActivity extends Activity{
	private Button nextStep;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_nav_layout);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		nextStep = (Button) findViewById(R.id.next_step);
		nextStep.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(AddNavActivity.this,Smartlink.class);
				startActivity(i);
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
				finish();
			}
		});
		nextStep.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setBackgroundResource(R.color.gray);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.setBackgroundResource(color.registbtnbg);
				}
				return false;
			}
		});
	}
}
