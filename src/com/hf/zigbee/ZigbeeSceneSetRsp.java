/**
 * 0x12
 */
package com.hf.zigbee;

import java.nio.ByteBuffer;

import com.hf.module.ModuleException;

public class ZigbeeSceneSetRsp extends Response{
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
		setCmd(bf.get(2));
		this.stat = bf.get(3);
		}catch(Exception e){
			throw new ModuleException();
		}
	}

}
