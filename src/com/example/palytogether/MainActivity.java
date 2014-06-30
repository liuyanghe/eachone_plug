package com.example.palytogether;
/**
 * �ж��Ƿ��ǵ�һ�ε�½
 * 	if��true�� ������������
 *  esle ���м����û�����
 */
import com.example.config.Userconfig;
import com.hf.module.ModuleConfig;
import com.hf.module.ModuleException;
import com.hf.module.impl.adaptor.AndroidAdaptor;
import com.hfapp.activity.ConfigSetting;
import com.hfapp.activity.Login;
import com.hfapp.activity.ModuleList;
import com.hfapp.work.InitThread;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Handler hand = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				startLocalCtrlModuListActivity();
				Toast.makeText(MainActivity.this, "�������Ӵ���", Toast.LENGTH_SHORT).show();
				break;
			case 5: //�û����������
				Toast.makeText(MainActivity.this, "�û����������", Toast.LENGTH_SHORT).show();
				break;
			case 2: //�������
				Toast.makeText(MainActivity.this, "�������", Toast.LENGTH_SHORT).show();
				break;
			case 3: //��½�ɹ�
				startModuleListActivity();
				Toast.makeText(MainActivity.this, "��½�ɹ�", Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(MainActivity.this, "ʲô���綼û�� ��ȥ��ɶ��", Toast.LENGTH_SHORT).show();
				break;
			case -103:
				Toast.makeText(MainActivity.this, "�û���������", Toast.LENGTH_SHORT).show();
				break;
			case -101:
				Toast.makeText(MainActivity.this, "�������", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setModuleConfig();
		if(Userconfig.isFristLogin()){
			/*
			 * ����ǵ�һ�ε�½ ��ȥ��������
			 *  */
			Intent i = new Intent(this, Login.class);
			startActivity(i);
			finish();
		}else{
			/*
			 * ������ǵ�һ�ε�½���� ȱ���û������� �Ǿ�ȥ��½����
			 */
			if(ModuleConfig.cloudPassword==null||ModuleConfig.cloudUserName==null){
				Intent logintent = new Intent(this, Login.class);
				startActivity(logintent);
				finish();
			}else{
				/**
				 * ��� ���û������� ��ô У���û��������Ƿ���ȷ  
				 * 
				 */
				new Thread(new InitThread(hand)).start();
			}
		}
	}

	
	private void InitConfig(){
		ModuleConfig.appcontext = getApplicationContext();
		ModuleConfig.accessKey="8a21049f466068f90146607eaaa1022a";
		ModuleConfig.cloudServiceUrl = "http://115.29.164.59/usvc/";
		ModuleConfig.broadcastPort = 38899;
		ModuleConfig.pulseInterval = 3000;
		try {
			ModuleConfig.broadcastIp = new AndroidAdaptor().getBroadCast();
		} catch (ModuleException e) {
			// TODO Auto-generated catch block
			ModuleConfig.broadcastIp = "255.255.255.255";
		}
	}
	
	
	private void startModuleListActivity(){
		Intent i = new Intent(this,ModuleList.class);
		startActivity(i);
		finish();
	}
	private void startLocalCtrlModuListActivity(){
		if(isWifiConnect()){
			startModuleListActivity();
		}else{
			hand.sendEmptyMessage(4);
		}
	}
	private boolean isWifiConnect(){
		WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
		if(wm.isWifiEnabled()){
			if(wm.getConnectionInfo() != null){
				return true;
			}
		}
		return false;
	}
	
	private void setModuleConfig() {
		// TODO Auto-generated method stub
		if(ModuleConfig.DEBUG){
			SharedPreferences sp = getSharedPreferences(ConfigSetting.CONFIG, MODE_PRIVATE);
			ModuleConfig.cloudsericeIp = sp.getString(ConfigSetting.CONFIG_SERVIP, ModuleConfig.cloudsericeIp);
			ModuleConfig.appcontext = getApplicationContext();
			ModuleConfig.accessKey=	sp.getString(ConfigSetting.CONFIG_ACCESSKEY, ModuleConfig.accessKey);
			ModuleConfig.cloudServiceUrl = sp.getString(ConfigSetting.CONFIG_SERVURL, ModuleConfig.cloudServiceUrl);
			ModuleConfig.broadcastPort = sp.getInt(ConfigSetting.CONFIG_LOCALPORT, ModuleConfig.broadcastPort);
			ModuleConfig.pulseInterval = 3000;
			try {
				ModuleConfig.broadcastIp = new AndroidAdaptor().getBroadCast();
			} catch (ModuleException e) {
				// TODO Auto-generated catch block
				ModuleConfig.broadcastIp = "255.255.255.255";
			}
		}else{
			InitConfig();
		}
	}
}
