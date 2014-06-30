package com.hf.zigbee.attr;

import java.nio.ByteBuffer;

public class OnOffAttr extends BaseAttr{
	private byte onoff;

	public byte getOnoff() {
		return onoff;
	}

	public void setOnoff(byte onoff) {
		this.onoff = onoff;
	}
	
	public byte[] pack(){
		ByteBuffer bf = ByteBuffer.allocate(1);
		bf.put(onoff);
		return bf.array();
	}
}
