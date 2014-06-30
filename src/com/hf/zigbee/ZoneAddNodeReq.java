/**
 * 0x05
 */
package com.hf.zigbee;

import java.nio.ByteBuffer;

import com.hf.module.ModuleException;
import com.hf.zgbee.util.Tool;

public class ZoneAddNodeReq extends Request{

	private byte[] zoneId;
	private byte[] nw_addr;
	private byte[] zoneName;
	
	public ZoneAddNodeReq(){
		setCmd((byte) 0x05);
	}
	
	public void setZoneId(byte[] zoneId) {
		this.zoneId = zoneId;
	}



	public void setNw_addr(byte[] nw_addr) {
		this.nw_addr = nw_addr;
	}



	public void setZoneName(byte[] zoneName) {
		this.zoneName = zoneName;
	}



	@Override
	public byte[] pack() {
		// TODO Auto-generated method stub
		ByteBuffer bf = null;
		if(zoneName != null)
		{
			bf = ByteBuffer.allocate(8+zoneName.length);
			bf.putShort((short) (6+zoneName.length));
		}
		else 
		{
			bf = ByteBuffer.allocate(8);
			bf.putShort((short) (6));
		}
		bf.put(getCmd());
		bf.put(zoneId);
		bf.put(nw_addr);
		if(zoneName == null){
			bf.put((byte) 0);
		}else{
			bf.put((byte) zoneName.length);
			bf.put(zoneName);
		}
		
		return bf.array();
	}
	public static void main(String[] args) throws ModuleException {
		try{
		ZoneAddNodeReq s = new ZoneAddNodeReq();
		s.setNw_addr(new byte[]{0x11,0x22});
		s.setZoneId(new byte[]{0x02,0x03});
		//s.setZoneName("hahaha".getBytes());
		Tool.printHex(s.pack());
		s.setZoneName("nihao".getBytes());
		Tool.printHex(s.pack());
		}catch(Exception e){
			throw new ModuleException();
		}
	}

}
