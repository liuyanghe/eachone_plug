/**
 * 0x06
 */
package com.hf.zigbee;

import java.nio.ByteBuffer;

import com.hf.module.ModuleException;

public class ZoneRemoveNodeReq extends Request{
	private byte[] zoneId;
	private byte[] nw_addr;
	
	public ZoneRemoveNodeReq(){
		setCmd((byte) 0x06);
		setLen((short) 0x05);
	}
	
	public void setZoneId(byte[] zoneId){
		this.zoneId = zoneId;
	}
	
	public  void setNw_addr(byte[] nw_addr){
		this.nw_addr = nw_addr;
	}
	@Override
	public byte[] pack() throws ModuleException {
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(getLen()+2);
		bf.putShort(getLen());
		bf.put(getCmd());
		bf.put(zoneId);
		bf.put(nw_addr);
		return bf.array();
		}catch(Exception e){
			throw new ModuleException();
		}
	}

}
