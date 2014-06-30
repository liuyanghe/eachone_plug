package com.hf.zigbee;

import java.nio.ByteBuffer;

import com.hf.module.ModuleException;
import com.hf.zigbee.Info.NetWorkAddr;

public class ZigbeeNodeRemoveReq extends Request{
	private NetWorkAddr nw_addr;
	
	public ZigbeeNodeRemoveReq(){
		setLen((short) 3);
		setCmd((byte) 0x02);
	}
	public void setNwAddr(NetWorkAddr nw_addr){
		this.nw_addr = nw_addr;
	}
	@Override
	public byte[] pack() throws ModuleException {
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(5);
		bf.putShort(getLen());
		bf.put(getCmd());
		bf.put(nw_addr.getNw_addr());
		return bf.array();
		}catch(Exception e){
			throw new ModuleException();
		}
	}
}
