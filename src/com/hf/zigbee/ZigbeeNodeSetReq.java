

/**
 * 0x11
 */
package com.hf.zigbee;

import java.nio.ByteBuffer;

import com.hf.module.ModuleException;
import com.hf.zgbee.util.Payload;

public class ZigbeeNodeSetReq extends Request{

	private byte flag;
	private Payload pl;
	
	public ZigbeeNodeSetReq(){
		this.cmd = 0x11;
		this.flag = 0x00;
	}
	
	public void setPayload(Payload pl){
		this.pl = pl;
	}
	@Override
	public byte[] pack() throws ModuleException {
		// TODO Auto-generated method stub
		try{
		setLen((short) (2+pl.pack().length));
		ByteBuffer bf = ByteBuffer.allocate(2+getLen());
		bf.putShort(getLen());
		bf.put(getCmd());
		bf.put(flag);
		bf.put(pl.pack());
		return bf.array();
		}catch(Exception e){
			e.printStackTrace();
			throw new ModuleException();
		}
	}
	
	
	
	
	
//	public static void main(String[] args) {
//		ZigbeeNodeSetReq z = new ZigbeeNodeSetReq();
//		Payload pl = new Payload();
//		pl.setNw_addr(new byte[]{0x01,0x02});
//		pl.setAttr((byte) 0x01);
//		pl.setAttrdata(new byte[]{(byte) 0x8F});
//		z.setPayload(pl);
//		try {
//			Tool.printHex(z.pack());
//		} catch (ModuleException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
