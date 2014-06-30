package com.hf.zigbee;

import java.nio.ByteBuffer;

import com.hf.lib.util.HexBin;
import com.hf.module.ModuleException;

public class ZoneAddNodeRsp extends Response{
	private byte stat;
	
	public byte getStat(){
		return this.stat;
	}
	@Override
	public void unpack(byte[] cmd) throws ModuleException {
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(cmd.length);
		bf.put(cmd);
		setLen(bf.getShort(0));
		setCmd(bf.get(2));
		this.stat = bf.get(3);
		}catch(Exception e){
			throw new ModuleException();
		}
	}
	public static void main(String[] args) {
		ZoneAddNodeRsp z = new ZoneAddNodeRsp();
		try {
			z.unpack(HexBin.stringToBytes("00020500"));
		} catch (ModuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
