package com.hf.zigbee.type;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ColorTempType extends BaseType{
	private byte onoff;
	private byte level;
	private byte[] color_temp;
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
	public byte[] getColor_temp() {
		return color_temp;
	}
	public void setColor_temp(byte[] color_temp) {
		this.color_temp = color_temp;
	}
	
	public byte[] pack(){
		ByteBuffer bf = ByteBuffer.allocate(4);
		bf.put(this.onoff);
		bf.put(this.level);
		bf.put(this.color_temp);
		return bf.array();
	}
	
	public void unpack(byte[] cmd){
		this.onoff = cmd[0];
		this.level = cmd[1];
		this.color_temp = Arrays.copyOfRange(cmd, 2, 4);
	}
	
}
