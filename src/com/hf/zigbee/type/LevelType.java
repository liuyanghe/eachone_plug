package com.hf.zigbee.type;

import java.nio.ByteBuffer;

public class LevelType extends BaseType{
	private byte onoff;
	private byte level;
	
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
	
	public byte[] pack(){
		ByteBuffer bf = ByteBuffer.allocate(2);
		bf.put(onoff);
		bf.put(level);
		return bf.array();
	}
	
	public void unpack(byte[] cmd){
		this.level = cmd[1];
		this.onoff = cmd[0];
	}
	
}
