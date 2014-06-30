package com.hf.zigbee;

public abstract class Event {
	public short len;
	public byte cmd;
	public short getLen() {
		return len;
	}
	public void setLen(short s) {
		this.len = s;
	}
	public byte getCmd() {
		return cmd;
	}
	public void setCmd(byte cmd) {
		this.cmd = cmd;
	}
	
	public abstract void unpack(byte[] cmd) throws Exception;
}
