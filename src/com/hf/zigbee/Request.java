package com.hf.zigbee;

import com.hf.module.ModuleException;

public abstract class Request {
	public short len;
	public byte cmd;
	public short getLen() {
		return len;
	}
	public void setLen(short len) {
		this.len = len;
	}
	public byte getCmd() {
		return cmd;
	}
	public void setCmd(byte cmd) {
		this.cmd = cmd;
	}
	
	public abstract byte[] pack() throws ModuleException;
}
