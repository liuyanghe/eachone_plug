package com.hf.zigbee;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import com.hf.lib.util.HexBin;
import com.hf.module.ModuleException;
import com.hf.zgbee.util.Tool;
import com.hf.zigbee.Info.IeeeAddrInfo;

public class ZoneGetAllNodesRsp extends Response{
	private byte[] zoneId;
	private byte num;
	private ArrayList<IeeeAddrInfo> iadds = new ArrayList<IeeeAddrInfo>();
	
	public byte getNum(){
		return this.num;
	}
	
	public byte[] getZoneId(){
		return this.zoneId;
	}
	public ArrayList<IeeeAddrInfo> getIeeeaddList(){
		return this.iadds;
	}
	@Override
	public void unpack(byte[] cmd) throws ModuleException {
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(cmd.length);
		bf.put(cmd);
		setLen(bf.getShort(0));
		setCmd(bf.get(2));
		this.zoneId = Arrays.copyOfRange(cmd, 3, 5);
		this.num = bf.get(5);
		for (int i = 0; i < num; i++) {
			byte[] ieeeaddr = Arrays.copyOfRange(cmd, 6+i*8, 6+8+i*8);
			IeeeAddrInfo ieeaddrinfo = new IeeeAddrInfo();
			ieeaddrinfo.setIeee_addr(ieeeaddr);
			iadds.add(ieeaddrinfo);
		}
		}catch(Exception e){
			throw new ModuleException();
		}
	}
	public static void main(String[] args) {
		ZoneGetAllNodesRsp z = new ZoneGetAllNodesRsp();
		try {
			z.unpack(HexBin.stringToBytes("000c070002010102030405060708"));
		} catch (ModuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Tool.printHex(z.getZoneId());
		Tool.printHex(z.getIeeeaddList().get(0).getIeee_addr());
	}
}
