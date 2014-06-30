/**
 * 0x09
 */
package com.hf.zigbee;

import java.nio.ByteBuffer;

import com.hf.module.ModuleException;

public class ZoneRemoveReq extends Request{
	public ZoneRemoveReq(){
		setLen((short) 1);
		setCmd((byte) 0x09);
	}
	@Override
	public byte[] pack() throws ModuleException {
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(3);
		bf.putShort(getLen());
		bf.put(getCmd());
		return bf.array();
		}catch(Exception e){
			throw new ModuleException();
		}
	}

}
