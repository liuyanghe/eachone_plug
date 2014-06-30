package com.hf.zigbee.attr;

public class ColorTempAttr extends BaseAttr{
	private byte[] color_temp;
	
	public byte[] getColor_temp() {
		return color_temp;
	}

	public void setColor_temp(byte[] color_temp) {
		this.color_temp = color_temp;
	}

	public byte[] pack() {
		// TODO Auto-generated method stub
		return color_temp;
	}

	public void unpack(byte[] cmd) {
		// TODO Auto-generated method stub
		this.color_temp = cmd;
	}
	
}
