package com.hf.zigbee;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.hf.module.ModuleException;
import com.hf.zgbee.util.Tool;
import com.hf.zigbee.Info.NodeStateInfo;

public class ZigbeeNodeSetRsp extends Response{
	private byte state;
	private byte flag;
	private NodeStateInfo ndoeStat = new NodeStateInfo();
	
	public ZigbeeNodeSetRsp(){
		setCmd((byte) 0x11);
		this.flag = 0x00;
	}
	public byte getStat(){
		return this.state;
	}
	
	public  byte getFlag(){
		return this.flag;
	}
	
	public NodeStateInfo getNodeStat(){
		return this.ndoeStat;
	}
	@Override
	public void unpack(byte[] cmd) throws ModuleException {
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(cmd.length);
		bf.put(cmd);
		setLen(bf.getShort(0));
		this.state = bf.get(3);
		this.flag = bf.get(4);
		this.ndoeStat.unpack(Arrays.copyOfRange(cmd, 5, cmd.length));
		}catch(Exception e){
			throw new ModuleException();
		}
	}
	
	
	public static void main(String[] args) {
		ZigbeeNodeSetRsp z = new ZigbeeNodeSetRsp();
		
		// 00 0a 11 00 00 02 11 22 01 fe ff
		try {
			z.unpack(new byte[]{0x00 ,0x09 ,0x11 ,0x00 ,0x00 ,0x02 ,0x11 ,0x22 ,0x01 ,(byte) 0xfe ,(byte) 0xff});
		} catch (ModuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Tool.printHex(z.getNodeStat().getNw_addr());
		Tool.printHex(z.getNodeStat().getStates());
		System.out.println(z.getNodeStat().getType());
	}
}
