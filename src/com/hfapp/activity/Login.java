package com.hfapp.activity;
/**
 * 登录
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.palytogether.R;
import com.example.palytogether.R.color;
import com.hf.module.ModuleConfig;
import com.hfapp.work.InitThread;

public class Login extends Activity implements OnClickListener{
	private EditText userName;//用户名输入框
	private EditText userPswd;//密码输入框
	private ImageButton forgotPswd;//忘记按钮
	private ImageButton regist;//注册按钮
	private Button loginBtn;//登录按钮
	private TextView config;//hiflying模块配置（需要隐藏，调试时候用）s
	private EditText forget;
	
	private Handler hand = new Handler(){//接收线程的信息，做UI处理
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0://username or password is wrong
				Toast.makeText(Login.this, "用户名或密码有误", Toast.LENGTH_SHORT).show();
				break;
			case 5: //please enter the correct username
				Toast.makeText(Login.this, "请输入正确的用户名", Toast.LENGTH_SHORT).show();
				break;
			case 2: //please enter the correct password
				Toast.makeText(Login.this, "请输入密码", Toast.LENGTH_SHORT).show();
				break;
			case 3: //jump to the ModuleList screen
				startModuleListActivity();
				Toast.makeText(Login.this, "login ok", Toast.LENGTH_SHORT).show();
				break;
			case 4: //
//				startModuleListActivity();
				//Toast.makeText(Login.this, "", Toast.LENGTH_SHORT).show();
				break;
			case -103:
				//Toast.makeText(Login.this, "", Toast.LENGTH_SHORT).show();
				break;
			case -101:
				//Toast.makeText(Login.this, "", Toast.LENGTH_SHORT).show();
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
		setContentView(R.layout.login_layout);
		initView();
	}
	
	/*
	 * 初始化组件
	 * */
	private void initView(){
		userName = (EditText) findViewById(R.id.user_name);
		userPswd = (EditText) findViewById(R.id.user_pswd);
		forgotPswd = (ImageButton) findViewById(R.id.forget_pswd);
		regist = (ImageButton) findViewById(R.id.regist);
		loginBtn = (Button) findViewById(R.id.login_btn);
		loginBtn.setOnTouchListener(new OnTouchListener() {//给登录按钮添加点击事件监听。起颜色变化效果

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
		config = (TextView) findViewById(R.id.configconfig);
		forgotPswd.setOnClickListener(this);
		regist.setOnClickListener(this);
		loginBtn.setOnClickListener(this);
		config.setOnClickListener(new OnClickListener() {//hiflying模块配置信息，点击进入模块设置
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Login.this,ConfigSetting.class);
				startActivity(i);
			}
		});
	}

	/*
	 * 为组件设置监听事件
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.forget_pswd){
			doForgotPswd();//忘记密码
		}else if(v.getId() == R.id.regist){
			doRegist();//注册
		}else if(v.getId() == R.id.login_btn){
			doLogin();//登录
		}
	}
	
	/*
	 * click the login button
	 */
	private void doLogin(){
		ModuleConfig.cloudUserName = userName.getText().toString().trim();
		ModuleConfig.cloudPassword = userPswd.getText().toString().trim();
		if(checkInPut()){
			new Thread(new InitThread(hand)).start();
		}
	}
	
	/*
	 * check the value of the input box
	 */
	private boolean checkInPut(){
			if(ModuleConfig.cloudUserName == null||ModuleConfig.cloudUserName.length()<4){
				hand.sendEmptyMessage(5);
				return false;
			}else if(ModuleConfig.cloudPassword == null||ModuleConfig.cloudPassword.length()<4){
				hand.sendEmptyMessage(2);
				return false;
			}
		return true;
	}
	
	/*
	 * jump to the registeration Screen
	 */
	private void doRegist(){
		Intent i = new Intent(this, Regist.class);
		startActivity(i);
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		finish();
		
	}
	
	/*
	 * forget the password
	 */
	private void doForgotPswd(){
		forgotPswd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
				LayoutInflater inflater = getLayoutInflater();
				builder.setView(inflater.inflate(R.layout.forgetpwd, null))
					   .setPositiveButton("确认", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//获取输入框的邮箱密码
							//把密码发送到该邮箱当中
							forget = (EditText)findViewById(R.id.forget);
							String email = forget.getText().toString();
							Toast.makeText(Login.this, email+"发送成功", 1).show();
						}
					})
					   .setNegativeButton("取消", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							//什么事都不做
							Toast.makeText(Login.this, "发送失败", 1).show();
						}
					}).create().show();
			}
		});
	}
	
	/*
	 * login successfully
	 */
	private void startModuleListActivity(){
		Intent i = new Intent(this,ModuleList.class);
		startActivity(i);
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		finish();
	}
}
