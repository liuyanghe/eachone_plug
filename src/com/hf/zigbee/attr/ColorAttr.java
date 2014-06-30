package com.hf.zigbee.attr;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.hf.module.ModuleException;

public class ColorAttr extends BaseAttr{
	private byte[] color_x;
	private byte[] color_y;
	public ColorAttr(byte[] color_x,byte[] color_y){
		this.color_x =color_x;
		this.color_y = color_y;
	}
	
	
	public byte[] getColor_x() {
		return color_x;
	}
	
	public byte[] getColor_y() {
		return color_y;
	}
	
	public byte[] pack() {
		// TODO Auto-generated method stub
		ByteBuffer bf = ByteBuffer.allocate(4);
		bf.put(color_x);
		bf.put(color_y);
		return bf.array();
	}


	public void unpack(byte[] cmd) throws ModuleException {
		// TODO Auto-generated method stub
		try{
		color_x = Arrays.copyOfRange(cmd, 0, 1);
		color_y = Arrays.copyOfRange(cmd, 2, 3);
		}catch(Exception e){
			throw new ModuleException();
		}
	}	
}
