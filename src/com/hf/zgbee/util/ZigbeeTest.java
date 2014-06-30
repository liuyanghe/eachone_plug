package com.hf.zgbee.util;

import com.hf.lib.util.HexBin;
import com.hf.module.ModuleException;
import com.hf.zigbee.ZigbeeNodeAddReq;

public class ZigbeeTest {
	public static void main(String[] args) {
		
		ZigbeeNodeAddReq z = new ZigbeeNodeAddReq();
		z.setFlag((byte) 0x01);
		z.setTime((byte) 0x30);
		try {
			System.out.println(HexBin.bytesToString(ZigbeeT2CmdPacker.pack(z)));
		} catch (ModuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}	
