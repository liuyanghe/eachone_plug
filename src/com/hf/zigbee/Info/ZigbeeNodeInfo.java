package com.hf.zigbee.Info;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.hf.zgbee.util.Tool;

public class ZigbeeNodeInfo {
	byte type;
	byte[] ieee_addr ;
	byte[] nw_addr;
	byte online_state;
	byte[] node_name;
	byte[] deviceStates;
	
	
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public byte[] getIeee_addr() {
		return ieee_addr;
	}
	public void setIeee_addr(byte[] ieee_addr) {
		this.ieee_addr = ieee_addr;
	}
	public byte[] getNw_addr() {
		return nw_addr;
	}
	
	public NetWorkAddr getnw_addr(){
		return new NetWorkAddr(this.nw_addr);
	}
	public void setNw_addr(byte[] nw_addr) {
		this.nw_addr = nw_addr;
	}
	public byte getOnline_state() {
		return online_state;
	}
	public void setOnline_state(byte online_state) {
		this.online_state = online_state;
	}
	public byte[] getNode_name() {
		
		return node_name;
	}
	public void setNode_name(byte[] node_name) {
		this.node_name = node_name;
	}
	public byte[] getDeviceStates() {
		return deviceStates;
	}
	
	public void setDeviceStates(byte[] deviceStates) {
		this.deviceStates = deviceStates;
	}
	
	public short getcolorX(){
		if(getType()==0x08){
			ByteBuffer bf = ByteBuffer.allocate(6);
			bf.put(deviceStates);
			return bf.getShort(2);
		}else{
			return 0;
		}
	}
	
	
	public void setcolorX(short x){
		if(getType()==0x08){
			ByteBuffer bf = ByteBuffer.allocate(deviceStates.length);
			bf.put(deviceStates);
			bf.putShort(2, x);
			deviceStates = bf.array();
		}
	}
	public void setcolorY(short y){
		if(getType()==0x08){
			ByteBuffer bf = ByteBuffer.allocate(deviceStates.length);
			bf.put(deviceStates);
			bf.putShort(4, y);
			deviceStates = bf.array();
		}
	}
	public short getcolorY(){
		if(getType()==0x08){
			ByteBuffer bf = ByteBuffer.allocate(6);
			bf.put(deviceStates);
			return bf.getShort(4);
		}else{
			return 0;
		}
	}
	public byte getLevle(){
		if(getType()>2){
			return deviceStates[1];
		}else{
			return 0;
		}
	}
	
	public void setLevel(int level){
		if(getType()>2){
			deviceStates[1] = (byte) level;
		}
	}
	
	public boolean getOnoffstat(){
		if(deviceStates[0] == 0x01){
			return true;
		}else{
			return false;
		}
	}
	
	public void unpack(byte[] cmd){
		setType(cmd[0]);
		setIeee_addr(Arrays.copyOfRange(cmd,1,9));
		setNw_addr(Arrays.copyOfRange(cmd, 9, 11));
		setOnline_state(cmd[11]);
		setNode_name(Arrays.copyOfRange(cmd, 12, 44));
		if(type == 1){
			setDeviceStates(Arrays.copyOfRange(cmd, 44, 45 ));
		}else if(type == 4){
			setDeviceStates(Arrays.copyOfRange(cmd, 44, 46 ));
		}else if(type == 2){
			setDeviceStates(Arrays.copyOfRange(cmd, 44, 48 ));
		}else if(type == 8){
			setDeviceStates(Arrays.copyOfRange(cmd, 44, 50 ));
		}
	}
	public byte[] pack(){
		ByteBuffer bf = ByteBuffer.allocate(deviceStates.length+node_name.length+12);
		bf.put(type);
		bf.put(ieee_addr);
		bf.put(nw_addr);
		bf.put(online_state);
		bf.put(node_name);
		bf.put(deviceStates);
		return bf.array();
	}
	
	
	public static void main(String[] args) {
		ZigbeeNodeInfo z = new ZigbeeNodeInfo();
		z.setType((byte) 1);
		z.setDeviceStates(new byte[]{0x01,0x02,0x03,0x04,0x05,0x06});
		z.setNode_name(new byte[32]);
		z.setNw_addr(new byte[]{0x11,0x22});
		z.setIeee_addr(new byte[]{0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a,0x0a});
		z.setOnline_state((byte) 0x0b);
		Tool.printHex(z.pack());
		z.unpack(z.pack());
		Tool.printHex(z.pack());
	}
	
	
}
