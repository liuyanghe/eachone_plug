package com.hfapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.config.Userconfig;
import com.example.palytogether.MainActivity;
import com.example.palytogether.R;
import com.example.palytogether.R.color;
import com.hf.module.IModuleManager;
import com.hf.module.ManagerFactory;
import com.hf.module.ModuleConfig;
import com.hf.module.ModuleException;
import com.hf.module.info.CaptchaImageInfo;
import com.hf.module.info.UserInfo;

public class Regist extends Activity{
	private EditText username;//用户名
	private EditText userdisplayName;//昵称
	private EditText userpswd;//密码
	private EditText reuserpswd;//重复密码
	private EditText email;//邮箱
	private EditText capcha;//验证码
	private ImageView imageCapcha;//验证码图片
	private Button ok;//注册按钮
	private boolean isinPutOk = false;//判断是否合法
	private IModuleManager manager;//管理者
	
	String strusername;
	String struserpswd;
	String strcapcha;
	String strcapchatoken;
	String stremail;
	
	
	
	
	private Handler hand = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
//				Toast.makeText(Regist.this, "network err", Toast.LENGTH_SHORT).show();
				Toast.makeText(Regist.this, "网络异常", Toast.LENGTH_SHORT).show();
				break;
			case 1:
//				Toast.makeText(Regist.this, "input err", Toast.LENGTH_SHORT).show();
				Toast.makeText(Regist.this, "输入有误", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(Regist.this, "regist ok", Toast.LENGTH_SHORT).show();
				ModuleConfig.cloudPassword = struserpswd;
				ModuleConfig.cloudUserName = strusername;
				Userconfig.saveUserInfo();
				Intent i = new Intent(Regist.this,MainActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
				finish();
				break;
			case -114:
//				Toast.makeText(Regist.this, "capcha timeout", Toast.LENGTH_SHORT).show();
				Toast.makeText(Regist.this, "验证码超时", Toast.LENGTH_SHORT).show();
				break;
			case -104:
//				Toast.makeText(Regist.this, "usrname is used", Toast.LENGTH_SHORT).show();
				Toast.makeText(Regist.this, "用户名已经注册", Toast.LENGTH_SHORT).show();
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
		setContentView(R.layout.register_layout);
		manager = ManagerFactory.getInstance().getManager();
		initView();
	}

	private void initView() {
		// 获取用户名----------------------------------------------------------------------------------
		username = (EditText) findViewById(R.id.username);
		username.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.length()<6){
					username.setTextColor(Color.RED);
					isinPutOk = false;
				}else{
					strusername = s.toString();
					username.setTextColor(Color.BLACK);
					isinPutOk = true;
				}
			}
		});
		// 获取昵称----------------------------------------------------------------------------------
		userdisplayName = (EditText) findViewById(R.id.userdisplayName);
		// 获取密码----------------------------------------------------------------------------------
		userpswd = (EditText) findViewById(R.id.userpswd);
		userpswd.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.length()<6){
					userpswd.setTextColor(Color.RED);
					isinPutOk = false;
				}else{
					userpswd.setTextColor(Color.BLACK);
					isinPutOk = true;
				}
			}
		});
		// 获取重复----------------------------------------------------------------------------------
		reuserpswd = (EditText) findViewById(R.id.reuserpswd);
		reuserpswd.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.length()<6||!userpswd.getText().toString().equalsIgnoreCase(s.toString())){
					reuserpswd.setTextColor(Color.RED);
					isinPutOk = false;
				}else{
					struserpswd = s.toString();
					reuserpswd.setTextColor(Color.BLACK);
					isinPutOk = true;
				}
			}
		});
		// 获取邮箱----------------------------------------------------------------------------------
		email = (EditText) findViewById(R.id.email);
		email.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(!s.toString().contains("@")||!s.toString().contains(".")){
					email.setTextColor(Color.RED);
					isinPutOk = false;
				}else{
					stremail = s.toString();
					email.setTextColor(Color.BLACK);
					isinPutOk = true;
				}
			}
		});
		// 获取验证码----------------------------------------------------------------------------------
		capcha = (EditText) findViewById(R.id.capcha);
		capcha.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.length()!=4){
					capcha.setTextColor(Color.RED);
					isinPutOk = false;
				}else{
					strcapcha = s.toString();
					capcha.setTextColor(Color.BLACK);
					isinPutOk = true;
				}
			}
		});
		// 设置验证图片----------------------------------------------------------------------------------
		imageCapcha = (ImageView) findViewById(R.id.imageCacha);
		imageCapcha.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setCpacha();
			}
		});
		// 登录按钮----------------------------------------------------------------------------------
		ok = (Button) findViewById(R.id.ok);
		setCpacha();
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isinPutOk = true)
					doRegister();
				else if(isinPutOk = false)
					hand.sendEmptyMessage(1);
			}
		});
		// 注册按钮添加效果------------------------------------------------------------------------
		ok.setOnTouchListener(new OnTouchListener() {

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
	
	// 注册
	private void doRegister(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				UserInfo ui = new UserInfo();
				ui.setAccessKey(ModuleConfig.accessKey);
				ui.setUserName(strusername);
				ui.setDisplayName(userdisplayName.getText().toString().trim());
				ui.setPassword(struserpswd);
				ui.setEmail(stremail);
				String c = strcapchatoken+strcapcha;
				try {
					manager.registerUser(ui, c);
					hand.sendEmptyMessage(2);
				} catch (ModuleException e) {
					// TODO Auto-generated catch block
					hand.sendEmptyMessage(e.getErrorCode());
				}
			}
		}).start();
	}
	
	// 设置验证图片
	private void setCpacha() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				try {
					final CaptchaImageInfo icap = manager.captchaImage();
					strcapchatoken = icap.getCaptchaToken();
					hand.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							imageCapcha.setImageBitmap(icap.getBitmap());
						}
					});
					
				} catch (ModuleException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
	}
}
