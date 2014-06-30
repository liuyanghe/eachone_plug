/**
 * 0x0e
 */
package com.hf.zigbee;

import java.nio.ByteBuffer;

import com.hf.module.ModuleException;

public class SceneRemoveReq extends Request{
	public SceneRemoveReq(){
		setLen((short) 1);
		setCmd((byte) 0x0e);
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
