package com.hf.zgbee.util;

import java.nio.ByteBuffer;

public class Payload{
	private byte[] nw_addr;
	private byte attr;
	private byte onoff;
	private byte level;
	private byte[] ct;
	private short colorx;
	private short colory;
	
	public byte[] getNw_addr() {
		return nw_addr;
	}
	public void setNw_addr(byte[] nw_addr) {
		this.nw_addr = nw_addr;
	}
	public byte getAttr() {
		return attr;
	}
	public void setAttr(byte attr) {
		this.attr = attr;
	}
	
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
	public byte[] getCt() {
		return ct;
	}
	public void setCt(byte[] ct) {
		this.ct = ct;
	}
	
	
	

	public short getColorx() {
		return colorx;
	}
	public void setColorx(short colorx) {
		this.colorx = colorx;
	}
	public short getColory() {
		return colory;
	}
	public void setColory(short colory) {
		this.colory = colory;
	}
	public byte[] pack(){
		ByteBuffer bf = ByteBuffer.allocate(7);
		bf.put(nw_addr);
		bf.put(attr);
		if(attr == 0){
			bf.put(onoff);
		}else if(attr == 1){
			bf.put(level);
		}else if(attr ==2){
			bf.put(ct);
		}else if(attr == 3){
			bf.putShort(colorx);
			bf.putShort(colory);
		}
		
		return bf.array();
	}
	
}
