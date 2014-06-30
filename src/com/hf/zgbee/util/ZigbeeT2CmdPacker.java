package com.hf.zgbee.util;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.hf.module.ModuleException;
import com.hf.zigbee.Request;

public class ZigbeeT2CmdPacker {
	public static byte[] pack(Request req) throws ModuleException{
		byte[] bytereq = req.pack();
		ByteBuffer bf = ByteBuffer.allocate(4+bytereq.length);
		switch (req.getCmd()) {
		//get
		case 0x10:
		case 0x07:
		case 0x08:
		case 0x0c:
		case 0x0d:
			bf.put((byte) 0xfd);
			
			break;
		//set
		case 0x11:
		case 0x12:
		case 0x01:
		case 0x05:
		case 0x06:
		case 0x09:
		case 0x0a:
		case 0x0b:
		case 0x0e:
			bf.put((byte) 0xfe);
			
			
			break;
		default:
			break;
		}
		
		bf.put((byte) 0x09);
		bf.putShort((short) (req.getLen()+2));
		bf.put(bytereq);
		return bf.array();
	}
	
	public static  byte[] unpackT2(byte[] t2cmd){
		if(t2cmd == null||t2cmd.length<4)
			return null;
		return Arrays.copyOfRange(t2cmd, 4, t2cmd.length);
	}
}
