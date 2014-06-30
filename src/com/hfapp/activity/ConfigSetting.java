package com.hfapp.activity;

import com.example.palytogether.R;
import com.hf.module.ModuleConfig;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ConfigSetting extends Activity{
	public static final String CONFIG = "MODULE_CONFIG";
	public static final String CONFIG_SERVIP = "CONFIG_SERVIP";
	public static final String CONFIG_ACCESSKEY = "CONFIG_ACCESSKEY";
	public static final String CONFIG_LOCALPORT = "CONFIG_LOCALPORT";
	public static final String CONFIG_BROATCAST = "CONFIG_BROATCAST";
	public static final String CONFIG_SERVPORT = "CONFIG_SERVPORT";
	public static final String CONFIG_SERVURL = "CONFIG_SERVURL";
	
	private SharedPreferences sp ;
	private EditText serverip;
	private EditText sccesskey;
	private EditText localport;
	private EditText broadCast;
	private EditText couldservport;
	private EditText couldservurl;
	private Button submit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config_activity);
		sp = getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		serverip = (EditText) findViewById(R.id.et1);
		serverip.setText(ModuleConfig.cloudsericeIp);
		sccesskey = (EditText) findViewById(R.id.et2);
		sccesskey.setText(ModuleConfig.accessKey);
		localport = (EditText) findViewById(R.id.et3);
		localport.setText(ModuleConfig.broadcastPort+"");
		broadCast = (EditText) findViewById(R.id.et4);
		broadCast.setText(ModuleConfig.broadcastIp);
		couldservport = (EditText) findViewById(R.id.et5);
		couldservport.setText(ModuleConfig.cloudservicePort+"");
		couldservurl = (EditText) findViewById(R.id.et6);
		couldservurl.setText(ModuleConfig.cloudServiceUrl);
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Editor e = sp.edit();
				e.putString(CONFIG_SERVIP, serverip.getText().toString().trim());
				e.putString(CONFIG_ACCESSKEY, sccesskey.getText().toString().trim());
				e.putInt(CONFIG_LOCALPORT, Integer.valueOf(localport.getText().toString().trim()));
				e.putString(CONFIG_BROATCAST, broadCast.getText().toString().trim());
				e.putInt(CONFIG_SERVPORT, Integer.valueOf(couldservport.getText().toString().trim()));
				e.putString(CONFIG_SERVURL, couldservurl.getText().toString().trim());
				e.commit();
				
				finish();
			}
		});
	}
}	
