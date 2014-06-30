/**
 * 0x10
 */
package com.hf.zigbee;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;

import com.hf.module.ModuleException;
import com.hf.zgbee.util.Tool;
import com.hf.zigbee.Info.IeeeAddrInfo;

public class ZigbeeGetNodesInfoReq extends Request{
	public ZigbeeGetNodesInfoReq() {
		setCmd((byte) 0x10);
	}
	private byte num = 0;
	private ArrayList<IeeeAddrInfo> ieeeaddrs = new ArrayList<IeeeAddrInfo>();
	
	public void addIeeeAddr(IeeeAddrInfo ia){
		ieeeaddrs.add(ia);
		setNum(ieeeaddrs.size());
	}
	
	private  void setNum(int num){
		this.num = (byte) num;
	}
	@Override
	public byte[] pack() throws ModuleException {
		// TODO Auto-generated method stub
		try{
		setLen((short) (2+8*num));
		ByteBuffer bf = ByteBuffer.allocate(getLen()+2);
		bf.putShort(len);
		bf.put(cmd);
		bf.put(num);
		Iterator<IeeeAddrInfo> it = ieeeaddrs.iterator();
		while(it.hasNext()){
			bf.put(it.next().getIeee_addr());
		}
		return bf.array();
		}catch(Exception e){
			throw new ModuleException();
		}
	}
	
	public static void main(String[] args) {
		ZigbeeGetNodesInfoReq zz = new ZigbeeGetNodesInfoReq();
		IeeeAddrInfo ie = new IeeeAddrInfo();
		ie.setIeee_addr(new byte[]{0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08});
		zz.addIeeeAddr(ie);
		zz.addIeeeAddr(ie);
		try {
			Tool.printHex(zz.pack());
		} catch (ModuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
