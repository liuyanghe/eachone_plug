package com.hf.zigbee;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import com.hf.lib.util.HexBin;
import com.hf.module.ModuleException;
import com.hf.zgbee.util.Tool;
import com.hf.zigbee.Info.ZigbeeNodeInfo;

public class ZigbeeGetNodesInfoRsp extends Response{
	private byte num;
	ArrayList<ZigbeeNodeInfo> zinfos = new ArrayList<ZigbeeNodeInfo>();
	public ZigbeeGetNodesInfoRsp(){
		this.cmd = 0x10;
	}
	public  byte getNum(){
		return this.num;
	}
	private void setNum(byte num){
		this.num = num;
	}
	
	public ArrayList<ZigbeeNodeInfo> getInfos(){
		return this.zinfos;
	}
	
	
	@Override
	public void unpack(byte[] cmd) throws ModuleException {
		// TODO Auto-generated method stub
		//len cmd N xxxx
		try{
		ByteBuffer bf = ByteBuffer.allocate(cmd.length);
		bf.put(cmd);
		setLen(bf.getShort(0));
		setCmd(bf.get(2));
		setNum(bf.get(3));
		int count = 4;
		System.out.println(HexBin.bytesToString(cmd));
		System.out.println(num);
		for(int i = 0;i<num;i++){
			ZigbeeNodeInfo z = new ZigbeeNodeInfo();
			byte[] abc = Arrays.copyOfRange(cmd, count, cmd.length);
			System.out.println(HexBin.bytesToString(abc));
			z.unpack(abc);
			count += z.pack().length;
			zinfos.add(z);
		}
		}catch(Exception e){
			throw new ModuleException();
		}
	}
	
	public static void main(String[] args) {
		ZigbeeGetNodesInfoRsp z = new ZigbeeGetNodesInfoRsp();
		ByteBuffer bf = ByteBuffer.allocate(1024);
		bf.putShort((short) 52);
		bf.put(z.cmd);
		bf.put((byte) 0x01);
		bf.put(new byte[]{0x01 ,0xa ,0xa ,0xa ,0xa ,0xa ,0xa ,0xa ,0xa ,0x11, 0x22, 0xb, 0x0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6 });
		Tool.printHex(bf.array());
		try {
			z.unpack(bf.array());
		} catch (ModuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Tool.printHex(z.getInfos().get(0).pack());
	}
}
