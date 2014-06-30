package com.hf.zigbee.type;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ColorType extends BaseType{
	private byte onoff;
	private byte level;
	private byte[] color_x;
	private byte[] color_y;
	
	
	public byte getOnoff() {
		return onoff;
	}
	public void setOnoff(byte onoff) {
		this.onoff = onoff;
	}
	public byte getLevel() {
		return level;
	}
	public void setLevel(byte level) {
		this.level = level;
	}
	public byte[] getColor_x() {
		return color_x;
	}
	public void setColor_x(byte[] color_x) {
		this.color_x = color_x;
	}
	public byte[] getColor_y() {
		return color_y;
	}
	public void setColor_y(byte[] color_y) {
		this.color_y = color_y;
	}
	
	public byte[] pack(){
		ByteBuffer bf = ByteBuffer.allocate(6);
		bf.put(onoff);
		bf.put(level);
		bf.put(color_x);
		bf.put(color_y);
		return  bf.array();
	}
	
	public void unpack(byte[] cmd){
		this.onoff = cmd[0];
		this.level = cmd[1];
		this.color_x = Arrays.copyOfRange(cmd, 2, 4);
		this.color_y = Arrays.copyOfRange(cmd, 4, 6);
	}
	
}
