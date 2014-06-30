package com.hf.zigbee;

import java.nio.ByteBuffer;

import com.hf.module.ModuleException;
import com.hf.zigbee.Info.RspStatus;

public class ZigbeeNodeRemoveRsp extends Response{
	private RspStatus stat;
	
	public RspStatus getStat(){
		return this.stat;
	}
	
	@Override
	public void unpack(byte[] cmd) throws ModuleException {
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(cmd.length);
		bf.put(cmd);
		setLen(bf.getShort(0));
		setCmd(bf.get(2));
		this.stat = new RspStatus(bf.get(3));
		}catch(Exception e){
			throw new ModuleException();
		}
	}
}
