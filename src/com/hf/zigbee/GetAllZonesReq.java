/**
 * 0x08
 */
package com.hf.zigbee;

import java.nio.ByteBuffer;

import com.hf.module.ModuleException;

public class GetAllZonesReq extends Request{
	public GetAllZonesReq(){
		setLen((short) 0x01);
		setCmd((byte) 0x08);
	}
	@Override
	public byte[] pack() throws ModuleException {
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(getLen()+2);
		bf.putShort(getLen());
		bf.put(getCmd());
		return bf.array();
		}catch(Exception e){
			throw new ModuleException();
		}
	}

}
