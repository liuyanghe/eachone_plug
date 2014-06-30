/**
 * 0x01
 */
package com.hf.zigbee;

import java.nio.ByteBuffer;

import com.hf.module.ModuleException;
import com.hf.zgbee.util.Tool;

public class ZigbeeNodeAddReq extends Request{
	private byte flag;
	private byte time;
	public ZigbeeNodeAddReq(){
		setCmd((byte) 0x01);
		setLen((short) 0x03);
	}
	public void setFlag(byte flag){
		this.flag = flag;
	}
	public void setTime(byte time){
		this.time = time;
	}
	@Override
	public byte[] pack() throws ModuleException {
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(getLen()+2);
		bf.putShort(getLen());
		bf.put(getCmd());
		bf.put(this.flag);
		bf.put(this.time);
		return bf.array();
		}catch(Exception e){
			throw new ModuleException();
		}
	}
	public static void main(String[] args) {
		ZigbeeNodeAddReq z = new ZigbeeNodeAddReq();
		z.setFlag((byte) 0x01);
		z.setTime((byte) 0x30);
		try {
			Tool.printHex(z.pack());
		} catch (ModuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
