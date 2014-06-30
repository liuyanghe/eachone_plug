package com.hf.zigbee;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import com.hf.module.ModuleException;
import com.hf.zigbee.Info.SceneIdInfo;

public class GetAllScenesRsp extends Response{
	private byte num;
	private ArrayList<SceneIdInfo>  scinfos = new ArrayList<SceneIdInfo>();
	
	public byte getNum(){
		return this.num;
	}
	
	public ArrayList<SceneIdInfo> getscInfo(){
		return this.scinfos;
	}
	@Override
	public void unpack(byte[] cmd)  throws Exception{
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(cmd.length);
		bf.put(cmd);
		setLen(bf.getShort(0));
		setCmd(bf.get(2));
		this.num = bf.get(3);
		for (int i = 0; i < num; i++) {
			SceneIdInfo scinfo  = new SceneIdInfo();
			scinfo.setSceneId(cmd[i+4]);
			scinfos.add(scinfo);
		}
		}
		catch(Exception e){
			throw new ModuleException();
		}
	}

}
