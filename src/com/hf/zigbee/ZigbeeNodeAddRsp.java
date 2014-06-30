package com.hf.zigbee;

import java.nio.ByteBuffer;

import com.hf.module.ModuleException;

public class ZigbeeNodeAddRsp extends Response{
	private byte stat;
	private byte flag;
	public byte getStat(){
		return this.stat;
	}
	
	public byte getFlag(){
		return this.flag;
	}
	@Override
	public void unpack(byte[] cmd) throws ModuleException {
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(cmd.length);
		bf.put(cmd);
		setLen(bf.getShort(0));
		setCmd(bf.get(2));
		this.stat = bf.get(4);
		this.flag = bf.get(3);
		}catch(Exception e){
			throw new ModuleException();
		}
	}

}
