/**
 * 0x0c
 */
package com.hf.zigbee;

import java.nio.ByteBuffer;

import com.hf.module.ModuleException;
import com.hf.zgbee.util.Tool;

public class SceneGetAllNodesReq extends Request{
	private byte sceneId;
	
	public SceneGetAllNodesReq(){
		setCmd((byte) 0x0c);
		setLen((short) 2);
	}
	public void setsceneId(byte id){
		this.sceneId = id;
	}
	@Override
	public byte[] pack() throws ModuleException {
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(4);
		bf.putShort(getLen());
		bf.put(getCmd());
		bf.put(sceneId);
		return bf.array();
		}catch(Exception e){
			throw new ModuleException();
		}
	}
	public static void main(String[] args) {
		SceneGetAllNodesReq s = new SceneGetAllNodesReq();
		s.setsceneId((byte) 100);
		try {
			Tool.printHex(s.pack());
		} catch (ModuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
