/**
 * 0x15
 */

package com.hf.zigbee;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.hf.module.ModuleException;
import com.hf.zigbee.Info.NodeStateInfo;

public class NewNodeJoinedEvent extends Event{
	
	private byte flag;
	private NodeStateInfo nsinfo;
	public NewNodeJoinedEvent(){
		setCmd((byte) 0x15);
	}
	
	public byte getflag(){
		return this.flag;
	}
	@Override
	public void unpack(byte[] cmd)  throws Exception {
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(cmd.length);
		bf.put(cmd);
		setLen(bf.getShort(0));
		this.flag = cmd[3];
		nsinfo = new NodeStateInfo();
		nsinfo.unpack(Arrays.copyOfRange(cmd, 4, cmd.length));
		}catch(Exception e){
			throw new ModuleException();
		}
	}
}
