package com.hf.zigbee.Info;

public class RspStatus {
	private byte status;
	
	
	public RspStatus(byte status){
		this.status = status;
	}
	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
	
}
