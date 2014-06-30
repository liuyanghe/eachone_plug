package com.hf.zigbee;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import com.hf.module.ModuleException;
import com.hf.zigbee.Info.IeeeAddrInfo;

public class SceneGetAllNodesRsp extends Response{
	private byte sceneId;
	private byte num;
	private ArrayList<IeeeAddrInfo> ieeeadds = new ArrayList<IeeeAddrInfo>();
	
	public byte getSceneId(){
		return this.sceneId;
	}
	
	public byte getNum(){
		return this.num;
	}
	
	public ArrayList<IeeeAddrInfo> getIeeeadds(){
		return this.ieeeadds;
	}
	@Override
	public void unpack(byte[] cmd) throws ModuleException {
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(cmd.length);
		bf.put(cmd);
		setLen(bf.getShort(0));
		setCmd(bf.get(2));
		this.sceneId = bf.get(3);
		this.num = bf.get(4);
		for (int i = 0; i < num; i++) {
			byte[] ieeeaddr = Arrays.copyOfRange(cmd,5 + i*8, 5+i*8+8);
			IeeeAddrInfo info = new IeeeAddrInfo();
			info .setIeee_addr(ieeeaddr);
			ieeeadds.add(info);
		}
		}catch(Exception e){
			throw new ModuleException();
		}
	}

}
