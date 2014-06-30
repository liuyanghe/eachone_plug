package com.hf.zigbee;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.hf.module.ModuleException;
import com.hf.zigbee.Info.NodeStateInfo;

public class ZigbeeZoneSetRsp extends Response{
	
	private byte flag;
	private byte stat;
	
	private NodeStateInfo nsinfo;
	public byte getflag(){
		return this.flag;
	}
	
	
	public byte getStat(){
		return this.stat;
	}
	public NodeStateInfo getNsInfo(){
		return this.nsinfo;
	}
	
	@Override
	public void unpack(byte[] cmd) throws ModuleException {
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(cmd.length);
		bf.put(cmd);
		setLen(bf.getShort(0));
		setCmd(bf.get(2));
		this.stat = bf.get(3);
		this.flag = bf.get(4);
		nsinfo = new NodeStateInfo();
		nsinfo.unpack(Arrays.copyOfRange(cmd, 5, cmd.length));
		}catch(Exception e){
			throw new ModuleException();
		}
	}

}
