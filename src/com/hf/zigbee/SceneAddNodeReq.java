/**
 * 0x0a
 */
package com.hf.zigbee;

import java.nio.ByteBuffer;

import com.hf.module.ModuleException;
import com.hf.zgbee.util.Tool;

public class SceneAddNodeReq extends Request{
	private byte sceneId;
	private byte[] nw_addr;
	private byte[] sceneName;
	
	public SceneAddNodeReq(){
		setCmd((byte) 0x0a);
	}
	
	public void setSceneId(byte sceneId) {
		this.sceneId = sceneId;
	}



	public void setNw_addr(byte[] nw_addr) {
		this.nw_addr = nw_addr;
	}



	public void setSceneName(byte[] sceneName) {
		this.sceneName = sceneName;
	}



	@Override
	public byte[] pack() throws ModuleException {
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(7+sceneName.length);
		bf.putShort((short) (3+sceneName.length));
		bf.put(getCmd());
		bf.put(sceneId);
		bf.put(nw_addr);
		if(sceneName.length <= 0){
			bf.put((byte) 0);
		}else{
			bf.put((byte) sceneName.length);
			bf.put(sceneName);
		}
		
		return bf.array();
		}catch(Exception e){
			throw new ModuleException();
		}
	}
	public static void main(String[] args) {
		SceneAddNodeReq s = new SceneAddNodeReq();
		s.setNw_addr(new byte[]{0x11,0x22});
		s.setSceneId((byte) 0x09);
		s.setSceneName("hahaha".getBytes());
		try {
			Tool.printHex(s.pack());
		} catch (ModuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
