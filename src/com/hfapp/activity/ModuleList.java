package com.hfapp.activity;

import java.util.ArrayList;

import com.example.palytogether.R;
import com.example.palytogether.R.color;
import com.hf.module.impl.LocalModuleInfoContainer;
import com.hf.module.info.ModuleInfo;
import com.hfapp.view.ModuleListView;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ModuleList extends Activity {
	private ImageButton addbtn;
	private ModuleListView mlistView;
	private RotateAnimation anim;
	private ImageView okBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.module_list_layout);
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
		okBtn = (ImageView) findViewById(R.id.ok);
		TextView title = (TextView) findViewById(R.id.tv_title);
		title.setText(R.string.setting_mymodule);
		okBtn.setImageResource(R.drawable.refrash_icon);
		backBtn.setImageResource(R.drawable.menu_icon);
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/**
				 * ��ȡ��ģ���״̬
				 */
				okBtn.startAnimation(anim);
				mlistView.getAllModuleSatus();
			}
		});
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ModuleList.this, Setting.class);
				i.putExtra(Setting.FROM, Setting.FROM_MODULE_LIST);
				startActivity(i);
				overridePendingTransition(R.anim.in_from_left,
						R.anim.out_to_right);
				finish();
			}
		});

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mlistView
				.setModuleList(LocalModuleInfoContainer.getInstance().getAll());
		mlistView.getAllModuleSatus();
	}

	private void initView() {
		anim = new RotateAnimation(0f, 1440f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		anim.setDuration(3200);
		addbtn = (ImageButton) findViewById(R.id.add_btn);
		mlistView = (ModuleListView) findViewById(R.id.module_list);

		addbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ModuleList.this, AddNavActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}
		});
		addbtn.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setBackgroundResource(R.color.gray);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.setBackgroundResource(color.add);
				}
				return false;
			}
		});
	}

	long fristPressTime = 0;
	long secondPressTime = 0;
	boolean isDoublepress = false;

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (!isDoublepress) {
			fristPressTime = System.currentTimeMillis();
			Toast.makeText(this, "�ڰ�һ���˳�", Toast.LENGTH_SHORT).show();
			isDoublepress = true;
		} else {
			secondPressTime = System.currentTimeMillis();
			if (secondPressTime - fristPressTime < 1000) {
				// System.exit(0);
				finish();
			}
			isDoublepress = false;
		}
	}

	// ----------------------------\\
}
