package com.hf.zigbee.attr;

import java.nio.ByteBuffer;

public class LevelAttr extends BaseAttr{
	private byte level;

	public byte getLevel() {
		return level;
	}

	public void setLevel(byte level) {
		this.level = level;
	}

	public  byte[] pack() {
		// TODO Auto-generated method stub
		ByteBuffer bf = ByteBuffer.allocate(1);
		bf.put(level);
		return bf.array();
	}
}
