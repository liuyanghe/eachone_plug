/**
 * 0x12
 */
package com.hf.zigbee;

import java.nio.ByteBuffer;

import com.hf.module.ModuleException;
import com.hf.zgbee.util.Tool;

public class ZigbeeSceneSetReq extends Request{
	
	private byte sceneId;
	

	public ZigbeeSceneSetReq(){
		setCmd((byte) 0x12);
		setLen((short) 0x02);
	}
	
	public void setSceneId(byte sceneId){
		this.sceneId = sceneId;
	}
	@Override
	public byte[] pack() throws ModuleException {
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(getLen()+2);
		bf.putShort(getLen());
		bf.put(getCmd());
		bf.put(sceneId);
		return bf.array();
		}catch(Exception e){
			throw new ModuleException();
		}
	}
	public static void main(String[] args) {
		ZigbeeSceneSetReq z = new ZigbeeSceneSetReq();
		z.setSceneId((byte) 0x09);
		try {
			Tool.printHex(z.pack());
		} catch (ModuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
