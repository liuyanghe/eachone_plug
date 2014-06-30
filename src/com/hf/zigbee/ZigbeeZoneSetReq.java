/**
 * 0x11
 */
package com.hf.zigbee;

import java.nio.ByteBuffer;

import com.hf.module.ModuleException;
import com.hf.zgbee.util.Payload;
import com.hf.zgbee.util.Tool;
import com.hf.zigbee.attr.LevelAttr;
import com.hf.zigbee.attr.OnOffAttr;

public class ZigbeeZoneSetReq extends Request{
	private byte flag;
	private Payload pl = new Payload();
	
	public ZigbeeZoneSetReq(){
		this.cmd = 0x11;
		this.flag = 0x01;
	}
//	public void setFlag(byte flag){
//		this.flag = flag;
//	}
	
	public void setPayload(Payload pl){
		this.pl = pl;
	}
	@Override
	public byte[] pack() throws ModuleException {
		// TODO Auto-generated method stub
		
		setLen((short) (2+pl.pack().length));
		ByteBuffer bf = ByteBuffer.allocate(2+getLen());
		bf.putShort(getLen());
		bf.put(getCmd());
		bf.put(flag);
		bf.put(pl.pack());
		return bf.array();
	}
	
//	public static class Payload{
//		private byte[] nw_addr;
//		private byte attr;
//		private byte[] attrdata;
//		public byte[] getNw_addr() {
//			return nw_addr;
//		}
//		public void setNw_addr(byte[] nw_addr) {
//			this.nw_addr = nw_addr;
//		}
//		public byte getAttr() {
//			return attr;
//		}
//		public void setAttr(byte attr) {
//			this.attr = attr;
//		}
//		public byte[] getAttrdata() {
//			return attrdata;
//		}
//		public void setAttrdata(byte[] attrdata) {
//			this.attrdata = attrdata;
//		}
//		
//		public void setAttrOnOff(OnOffAttr onoff){
//			this.attrdata = onoff.pack();
//		}
//		
//		public void setAttrLevel(LevelAttr level){
//			this.attrdata = level.pack();
//		}
//		
//		public byte[] pack() throws ModuleException{
//			try{
//			ByteBuffer bf = ByteBuffer.allocate(4);
//			if(attr == 0){
//				bf = ByteBuffer.allocate(4);
//			}else if(attr == 1){
//				bf = ByteBuffer.allocate(4);
//			}else if(attr ==2){
//				bf = ByteBuffer.allocate(5);
//			}else if(attr == 3){
//				bf = ByteBuffer.allocate(7);
//			}
//			bf.put(nw_addr);
//			bf.put(attr);
//			bf.put(getAttrdata());
//			return bf.array();
//			}catch(Exception e){
//				throw new ModuleException();
//			}
//		}
//		
//		public void unpack(){
//			
//		}
//		
//	}
	public static void main(String[] args) {
		ZigbeeZoneSetReq z = new ZigbeeZoneSetReq();
		z.pl.setNw_addr(new byte[]{0x01,0x02});
		z.pl.setAttr((byte) 0x01);
		//z.pl.setAttrdata(new byte[]{(byte) 0x8F});
		try {
			Tool.printHex(z.pack());
		} catch (ModuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
