/**
 * 0x07
 */
package com.hf.zigbee;

import java.nio.ByteBuffer;

import com.hf.module.ModuleException;

public class ZoneGetAllNodesReq extends Request{
	private byte[] zoneId;
	
	public ZoneGetAllNodesReq(){
		setCmd((byte) 0x07);
		setLen((short) 3);
	}
	
	public void setZoneId(byte[] zoneId){
		this.zoneId = zoneId;
	}
	
	@Override
	public byte[] pack() throws ModuleException {
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(getLen()+2);
		bf.putShort(getLen());
		bf.put(getCmd());
		bf.put(zoneId);
		return bf.array();
		}catch(Exception e){
			throw new ModuleException();
		}
	}

}
