package com.hf.zigbee;

import java.nio.ByteBuffer;

import com.hf.module.ModuleException;

public class ZoneRemoveRsp extends Response{
	private byte stat;
	
	public byte getStat(){
		return this.stat;
	}
	@Override
	public void unpack(byte[] cmd) throws ModuleException {
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(cmd.length);
		bf.put(cmd);
		setLen(bf.getShort(0));
		setCmd(cmd[2]);
		this.stat = cmd[3];
		}catch(Exception e){
			throw new ModuleException();
		}
	}
}
