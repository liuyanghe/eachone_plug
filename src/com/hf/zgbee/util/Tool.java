package com.hf.zgbee.util;

public class Tool {
	public static void printHex(byte[] cmd){
		for(int i = 0;i<cmd.length;i++){
			System.out.print(Integer.toHexString(cmd[i]&0xff));
			System.out.print(" ");
		}
		System.out.println();
	}
}
