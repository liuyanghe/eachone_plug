package com.hf.zigbee.Info;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class NodeStateInfo {
	private byte type;
	private byte[] nw_addr;
	private byte online_state;
	private byte[] states;
	public byte getType() {
		return this.type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public byte[] getNw_addr() {
		return nw_addr;
	}
	public void setNw_addr(byte[] nw_addr) {
		this.nw_addr = nw_addr;
	}
	
	public void setNw_addr(NetWorkAddr nw_addr){
		this.nw_addr = nw_addr.getNw_addr();
	}
	
	public NetWorkAddr getNw_addrAsClass(){
		return  new NetWorkAddr(this.nw_addr);
	}
	public byte getOnline_state() {
		return online_state;
	}
	public void setOnline_state(byte online_state) {
		this.online_state = online_state;
	}
	public byte[] getStates() {
		return states;
	}
	public void setStates(byte[] states) {
		this.states = states;
	}
	
	public short getcolorX(){
		if(getType()==0x08){
			ByteBuffer bf = ByteBuffer.allocate(6);
			bf.put(states);
			return bf.getShort(2);
		}else{
			return 0;
		}
	}
	public short getcolorY(){
		if(getType()==0x08){
			ByteBuffer bf = ByteBuffer.allocate(6);
			bf.put(states);
			return bf.getShort(4);
		}else{
			return 0;
		}
	}
	public byte getLevle(){
		if(getType()>2){
			return states[1];
		}else{
			return 0;
		}
	}
	
	public boolean getOnoffstat(){
		if(states[0] == 0x01){
			return true;
		}else{
			return false;
		}
	}
	
	public short getTemp(){
		if(getType() == 0x02){
			ByteBuffer bf = ByteBuffer.allocate(6);
			bf.put(states);
			return bf.getShort(2);
		}else{
			return 0;
		}
	}
	public void unpack(byte[] cmd){
		setType(cmd[0]);
		setNw_addr(Arrays.copyOfRange(cmd, 1, 3));
		setOnline_state(cmd[3]);
		if(type == 1){
			setStates(Arrays.copyOfRange(cmd, 4, 5));
		}else if(type == 2){
			setStates(Arrays.copyOfRange(cmd, 4, 8));
		}else if(type == 4){
			setStates(Arrays.copyOfRange(cmd, 4, 6));
		}else if(type ==8){
			setStates(Arrays.copyOfRange(cmd, 4, 10));
		}
	}
}
