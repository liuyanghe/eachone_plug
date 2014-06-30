package com.hf.zigbee;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import com.hf.module.ModuleException;
import com.hf.zigbee.Info.ZoneIdInfo;

public class GetAllZonesRsp extends Response{
	private byte num;
	private ArrayList<ZoneIdInfo> zoneids = new ArrayList<ZoneIdInfo>();
	
	public byte getStat(){
		return this.num;
	}
	
	public ArrayList<ZoneIdInfo> getZoneIds(){
		return this.zoneids;
	}
	@Override
	public void unpack(byte[] cmd) throws Exception {
		// TODO Auto-generated method stub
		try{
		ByteBuffer bf = ByteBuffer.allocate(cmd.length);
		bf.put(cmd);
		setLen(bf.getShort(0));
		setCmd(bf.get(2));
		this.num = bf.get(3);
		
		for (int i = 0; i < num; i++) {
			byte[] zoneid = Arrays.copyOfRange(cmd, 4+i*2, 4+i*2+2);
			ZoneIdInfo zidInfo = new ZoneIdInfo();
			zidInfo.setZoneId(zoneid);
			zoneids.add(zidInfo);
		}
		}catch(Exception e){
			throw new ModuleException();
		}
	}

}
