/**
 * 0x0b
 */
package com.hf.zigbee;

import java.nio.ByteBuffer;

import com.hf.module.ModuleException;
import com.hf.zgbee.util.Tool;

public class SceneRemoveNodeReq extends Request{
	private byte sceneId; 
	private byte[] nw_addr;
	
	public SceneRemoveNodeReq(){
		setCmd((byte) 0x0b);
		setLen((short)4);
	}
	
	
	public void setSceneId(byte sceneId) {
		this.sceneId = sceneId;
	}




	public void setNw_addr(byte[] nw_addr) {
		this.nw_addr = nw_addr;
	}




	@Override
	public byte[] pack() throws ModuleException {
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(getLen()+2);
		bf.putShort(getLen());
		bf.put(getCmd());
		bf.put(sceneId);
		bf.put(nw_addr);
		return bf.array();
		}catch(Exception e){
			throw new ModuleException();
		}
	}
	public static void main(String[] args) {
		SceneRemoveNodeReq s = new SceneRemoveNodeReq();
		s.setSceneId((byte) 0x09);
		s.setNw_addr(new byte[]{0x11,0x22});
		try {
			Tool.printHex(s.pack());
		} catch (ModuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
