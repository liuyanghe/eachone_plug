package com.hf.zgbee.util;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

import com.hf.lib.util.HexBin;
import com.hf.module.IModuleManager;
import com.hf.module.ManagerFactory;
import com.hf.module.ModuleException;
import com.hf.zigbee.GetAllScenesReq;
import com.hf.zigbee.GetAllScenesRsp;
import com.hf.zigbee.GetAllZonesReq;
import com.hf.zigbee.GetAllZonesRsp;
import com.hf.zigbee.SceneAddNodeReq;
import com.hf.zigbee.SceneAddNodeRsp;
import com.hf.zigbee.SceneGetAllNodesReq;
import com.hf.zigbee.SceneGetAllNodesRsp;
import com.hf.zigbee.SceneRemoveNodeReq;
import com.hf.zigbee.SceneRemoveNodeRsp;
import com.hf.zigbee.SceneRemoveReq;
import com.hf.zigbee.SceneRemoveRsp;
import com.hf.zigbee.ZigbeeGetNodesInfoReq;
import com.hf.zigbee.ZigbeeGetNodesInfoRsp;
import com.hf.zigbee.ZigbeeNodeAddReq;
import com.hf.zigbee.ZigbeeNodeAddRsp;
import com.hf.zigbee.ZigbeeNodeRemoveReq;
import com.hf.zigbee.ZigbeeNodeRemoveRsp;
import com.hf.zigbee.ZigbeeNodeSetReq;
import com.hf.zigbee.ZigbeeNodeSetRsp;
import com.hf.zigbee.ZigbeeSceneSetReq;
import com.hf.zigbee.ZigbeeSceneSetRsp;
import com.hf.zigbee.ZigbeeZoneSetReq;
import com.hf.zigbee.ZigbeeZoneSetRsp;
import com.hf.zigbee.ZoneAddNodeReq;
import com.hf.zigbee.ZoneAddNodeRsp;
import com.hf.zigbee.ZoneGetAllNodesReq;
import com.hf.zigbee.ZoneGetAllNodesRsp;
import com.hf.zigbee.ZoneRemoveNodeReq;
import com.hf.zigbee.ZoneRemoveNodeRsp;
import com.hf.zigbee.ZoneRemoveReq;
import com.hf.zigbee.ZoneRemoveRsp;
import com.hf.zigbee.Info.IeeeAddrInfo;
import com.hf.zigbee.Info.NetWorkAddr;
import com.hf.zigbee.Info.NodeStateInfo;
import com.hf.zigbee.Info.RspStatus;
import com.hf.zigbee.Info.SceneIdInfo;
import com.hf.zigbee.Info.ZigbeeNodeInfo;
import com.hf.zigbee.Info.ZoneIdInfo;
import com.hf.zgbee.util.Payload;

public class zigbeeModuleHelper {
	private  IModuleManager mm = ManagerFactory.getInstance().getManager();
	private  String mac = null;
	
	public zigbeeModuleHelper(String modueleMac){
		this.mac = modueleMac;
	}
	
	public ArrayList<SceneIdInfo>  getAllScenes() throws ModuleException{
		HashMap<String, byte[]> t2Req = new HashMap<String, byte[]>();
		GetAllScenesReq req = new GetAllScenesReq();
		byte[] t2reqcmd = ZigbeeT2CmdPacker.pack(req);
		t2Req.put(mac, t2reqcmd);
		HashMap<String, byte[]> t2rsp = mm.moduleCommGet(t2Req);
		byte[] t2rspcmd = t2rsp.get(mac);
		GetAllScenesRsp rsp = new GetAllScenesRsp();
		if(t2rspcmd == null){
			throw new ModuleException();
		}
		try {
			rsp.unpack(ZigbeeT2CmdPacker.unpackT2(t2rspcmd));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ModuleException();
		}
		return rsp.getscInfo();
	}
	
	public ArrayList<ZoneIdInfo> getAllZones() throws ModuleException{
		HashMap<String, byte[]> t2Req = new HashMap<String, byte[]>();
		GetAllZonesReq req = new GetAllZonesReq();
		byte[] t2reqcmd = ZigbeeT2CmdPacker.pack(req);
		t2Req.put(mac, t2reqcmd);
		HashMap<String, byte[]> t2rsp = mm.moduleCommGet(t2Req);
		byte[] t2rspcmd = t2rsp.get(mac);
		if(t2rspcmd == null){
			throw new ModuleException();
		}
		GetAllZonesRsp rsp =  new GetAllZonesRsp();
		try {
			rsp.unpack(ZigbeeT2CmdPacker.unpackT2(t2rspcmd));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ModuleException();
		}
		return rsp.getZoneIds();
	}
	
	public RspStatus addNodeToScene(SceneIdInfo scid,NetWorkAddr nw_addr) throws ModuleException{
		HashMap<String, byte[]> t2Req = new HashMap<String, byte[]>();
		SceneAddNodeReq req = new SceneAddNodeReq();
		req.setSceneId(scid.getSceneId());
		req.setNw_addr(nw_addr.getNw_addr());
		byte[] t2reqcmd = ZigbeeT2CmdPacker.pack(req);
		t2Req.put(mac, t2reqcmd);
		HashMap<String, byte[]> t2rsp = mm.moduleCommGet(t2Req);
		byte[] t2rspcmd = t2rsp.get(mac);
		if(t2rspcmd == null){
			throw new ModuleException();
		}
		SceneAddNodeRsp rsp = new SceneAddNodeRsp();
		rsp.unpack(ZigbeeT2CmdPacker.unpackT2(t2rspcmd));
		return new RspStatus(rsp.getStat());
	}
	
	public RspStatus createScene(NetWorkAddr nw_addr,String sceneName) throws ModuleException{
		HashMap<String, byte[]> t2Req = new HashMap<String, byte[]>();
		SceneAddNodeReq req = new SceneAddNodeReq();
		req.setSceneId((byte) 0);
		req.setNw_addr(nw_addr.getNw_addr());
		req.setSceneName(sceneName.getBytes());
		byte[] t2reqcmd = ZigbeeT2CmdPacker.pack(req);
		t2Req.put(mac, t2reqcmd);
		HashMap<String, byte[]> t2rsp = mm.moduleCommGet(t2Req);
		byte[] t2rspcmd = t2rsp.get(mac);
		if(t2rspcmd == null){
			throw new ModuleException();
		}
		SceneAddNodeRsp rsp = new SceneAddNodeRsp();
		rsp.unpack(ZigbeeT2CmdPacker.unpackT2(t2rspcmd));
		return new RspStatus(rsp.getStat());
	}
	
	public ArrayList<IeeeAddrInfo> getAllNodeFromScene(SceneIdInfo scid) throws ModuleException{
		HashMap<String, byte[]> t2Req = new HashMap<String, byte[]>();
		SceneGetAllNodesReq req = new SceneGetAllNodesReq();
		req.setsceneId(scid.getSceneId());
		byte[] t2reqcmd = ZigbeeT2CmdPacker.pack(req);
		t2Req.put(mac, t2reqcmd);
		HashMap<String, byte[]> t2rsp = mm.moduleCommGet(t2Req);
		byte[] t2rspcmd = t2rsp.get(mac);
		if(t2rspcmd == null){
			throw new ModuleException();
		}
		SceneGetAllNodesRsp rsp = new SceneGetAllNodesRsp();
		rsp.unpack(ZigbeeT2CmdPacker.unpackT2(t2rspcmd));
		return rsp.getIeeeadds();
	}
	
	public RspStatus removeNodeFormScene(SceneIdInfo scid,NetWorkAddr nw_addr) throws ModuleException{
		HashMap<String, byte[]> t2Req = new HashMap<String, byte[]>();
		SceneRemoveNodeReq req = new SceneRemoveNodeReq();
		req.setNw_addr(nw_addr.getNw_addr());
		req.setSceneId(scid.getSceneId());
		byte[] t2reqcmd = ZigbeeT2CmdPacker.pack(req);
		t2Req.put(mac, t2reqcmd);
		HashMap<String, byte[]> t2rsp = mm.moduleCommGet(t2Req);
		byte[] t2rspcmd = t2rsp.get(mac);
		if(t2rspcmd == null){
			throw new ModuleException();
		}
		SceneRemoveNodeRsp rsp = new SceneRemoveNodeRsp();
		rsp.unpack(ZigbeeT2CmdPacker.unpackT2(t2rspcmd));
		return new RspStatus(rsp.getStat());
	}
	
	public RspStatus deleteAllScene(SceneIdInfo scid) throws ModuleException{
		HashMap<String, byte[]> t2Req = new HashMap<String, byte[]>();
		SceneRemoveReq req = new SceneRemoveReq();
		byte[] t2reqcmd = ZigbeeT2CmdPacker.pack(req);
		t2Req.put(mac, t2reqcmd);
		HashMap<String, byte[]> t2rsp = mm.moduleCommGet(t2Req);
		byte[] t2rspcmd = t2rsp.get(mac);
		if(t2rspcmd == null){
			throw new ModuleException();
		}
		SceneRemoveRsp rsp = new SceneRemoveRsp();
		rsp.unpack(ZigbeeT2CmdPacker.unpackT2(t2rspcmd));
		return new RspStatus(rsp.getStat());
	}
	
	public ArrayList<ZigbeeNodeInfo> getAllNodes() throws ModuleException{
		HashMap<String, byte[]> t2Req = new HashMap<String, byte[]>();
		ZigbeeGetNodesInfoReq req = new ZigbeeGetNodesInfoReq();
		byte[] t2reqcmd = ZigbeeT2CmdPacker.pack(req);
		t2Req.put(mac, t2reqcmd);
		HashMap<String, byte[]> t2rsp = mm.moduleCommGet(t2Req);
		byte[] t2rspcmd = t2rsp.get(mac);
		if(t2rspcmd == null){
			throw new ModuleException();
		}
		ZigbeeGetNodesInfoRsp rsp = new ZigbeeGetNodesInfoRsp();
		Log.i("getAllNodes", HexBin.bytesToString(t2rspcmd));
		rsp.unpack(ZigbeeT2CmdPacker.unpackT2(t2rspcmd));
		return rsp.getInfos();
	}
	
	public ZigbeeNodeInfo getNodeInfo(IeeeAddrInfo ieeeaddr) throws ModuleException{
		HashMap<String, byte[]> t2Req = new HashMap<String, byte[]>();
		ZigbeeGetNodesInfoReq req = new ZigbeeGetNodesInfoReq();
		req.addIeeeAddr(ieeeaddr);
		byte[] t2reqcmd = ZigbeeT2CmdPacker.pack(req);
		t2Req.put(mac, t2reqcmd);
		HashMap<String, byte[]> t2rsp = mm.moduleCommGet(t2Req);
		byte[] t2rspcmd = t2rsp.get(mac);
		if(t2rspcmd == null){
			throw new ModuleException();
		}
		ZigbeeGetNodesInfoRsp rsp = new ZigbeeGetNodesInfoRsp();
		rsp.unpack(ZigbeeT2CmdPacker.unpackT2(t2rspcmd));
		return rsp.getInfos().get(0);
	}
	
	
	public ArrayList<ZigbeeNodeInfo> getSomeNodes(ArrayList<IeeeAddrInfo> ieeeadds) throws ModuleException{
		HashMap<String, byte[]> t2Req = new HashMap<String, byte[]>();
		ZigbeeGetNodesInfoReq req = new ZigbeeGetNodesInfoReq();
		for (int i = 0; i < ieeeadds.size(); i++) {
			req.addIeeeAddr(ieeeadds.get(i));
		}
		byte[] t2reqcmd = ZigbeeT2CmdPacker.pack(req);
		t2Req.put(mac, t2reqcmd);
		HashMap<String, byte[]> t2rsp = mm.moduleCommGet(t2Req);
		byte[] t2rspcmd = t2rsp.get(mac);
		if(t2rspcmd == null){
			throw new ModuleException();
		}
		ZigbeeGetNodesInfoRsp rsp = new ZigbeeGetNodesInfoRsp();
		rsp.unpack(ZigbeeT2CmdPacker.unpackT2(t2rspcmd));
		return rsp.getInfos();
	}
	
	public RspStatus startAddNodes(int timeout) throws ModuleException{
		HashMap<String, byte[]> t2Req = new HashMap<String, byte[]>();
		ZigbeeNodeAddReq req = new ZigbeeNodeAddReq();
		req.setFlag((byte) 0x00);
		req.setTime((byte) timeout);
		byte[] t2reqcmd = ZigbeeT2CmdPacker.pack(req);
		t2Req.put(mac, t2reqcmd);
		HashMap<String, byte[]> t2rsp = mm.moduleCommGet(t2Req);
		byte[] t2rspcmd = t2rsp.get(mac);
		if(t2rspcmd == null){
			throw new ModuleException();
		}
		ZigbeeNodeAddRsp rsp = new ZigbeeNodeAddRsp();
		rsp.unpack(ZigbeeT2CmdPacker.unpackT2(t2rspcmd));
		return new RspStatus(rsp.getStat());
	}
	
	public RspStatus removeNodes(NetWorkAddr nw_addr) throws ModuleException{
		HashMap<String, byte[]> t2Req = new HashMap<String, byte[]>();
		ZigbeeNodeRemoveReq req = new ZigbeeNodeRemoveReq();
		req.setNwAddr(nw_addr);
		byte[] t2reqcmd = ZigbeeT2CmdPacker.pack(req);
		t2Req.put(mac, t2reqcmd);
		HashMap<String, byte[]> t2rsp = mm.moduleCommGet(t2Req);
		byte[] t2rspcmd = t2rsp.get(mac);
		if(t2rspcmd == null){
			throw new ModuleException();
		}
		ZigbeeNodeRemoveRsp rsp = new ZigbeeNodeRemoveRsp();
		rsp.unpack(ZigbeeT2CmdPacker.unpackT2(t2rspcmd));
		return rsp.getStat();
	}
	
	
	/**
	 * 
	 * @param payload �Ժ� payload ҲҪ��װ ��������
	 * @return
	 * @throws ModuleException 
	 */
	public NodeStateInfo setNode(Payload payload) throws ModuleException{
		HashMap<String, byte[]> t2Req = new HashMap<String, byte[]>();
		ZigbeeNodeSetReq req = new ZigbeeNodeSetReq();
		req.setPayload(payload);
		byte[] t2reqcmd = ZigbeeT2CmdPacker.pack(req);
		t2Req.put(mac, t2reqcmd);
		HashMap<String, byte[]> t2rsp = mm.moduleCommGet(t2Req);
		byte[] t2rspcmd = t2rsp.get(mac);
		if(t2rspcmd == null){
			throw new ModuleException();
		}
		ZigbeeNodeSetRsp rsp = new ZigbeeNodeSetRsp();
		rsp.unpack(ZigbeeT2CmdPacker.unpackT2(t2rspcmd));
		return rsp.getNodeStat();
	}
	public RspStatus setScene(SceneIdInfo scid) throws ModuleException{
		HashMap<String, byte[]> t2Req = new HashMap<String, byte[]>();
		ZigbeeSceneSetReq req = new ZigbeeSceneSetReq();
		req.setSceneId(scid.getSceneId());
		byte[] t2reqcmd = ZigbeeT2CmdPacker.pack(req);
		t2Req.put(mac, t2reqcmd);
		HashMap<String, byte[]> t2rsp = mm.moduleCommGet(t2Req);
		byte[] t2rspcmd = t2rsp.get(mac);
		if(t2rspcmd == null){
			throw new ModuleException();
		}
		ZigbeeSceneSetRsp rsp = new ZigbeeSceneSetRsp();
		rsp.unpack(ZigbeeT2CmdPacker.unpackT2(t2rspcmd));
		return new RspStatus(rsp.getStat());
	}
	
	
	public  RspStatus setZone(Payload payload) throws ModuleException{
		HashMap<String, byte[]> t2Req = new HashMap<String, byte[]>();
		ZigbeeZoneSetReq req = new ZigbeeZoneSetReq();
		req.setPayload(payload);
		byte[] t2reqcmd = ZigbeeT2CmdPacker.pack(req);
		t2Req.put(mac, t2reqcmd);
		HashMap<String, byte[]> t2rsp = mm.moduleCommGet(t2Req);
		byte[] t2rspcmd = t2rsp.get(mac);
		if(t2rspcmd == null){
			throw new ModuleException();
		}
		ZigbeeZoneSetRsp rsp = new ZigbeeZoneSetRsp();
		rsp.unpack(ZigbeeT2CmdPacker.unpackT2(t2rspcmd));
		return new RspStatus(rsp.getStat());
	}
	
	public RspStatus addNodeToZone(ZoneIdInfo zoneid,NetWorkAddr nw_addr) throws ModuleException{
		HashMap<String, byte[]> t2Req = new HashMap<String, byte[]>();
		ZoneAddNodeReq req = new ZoneAddNodeReq();
		req.setNw_addr(nw_addr.getNw_addr());
		req.setZoneId(zoneid.getZoneId());
		byte[] t2reqcmd = ZigbeeT2CmdPacker.pack(req);
		t2Req.put(mac, t2reqcmd);
		HashMap<String, byte[]> t2rsp = mm.moduleCommGet(t2Req);
		byte[] t2rspcmd = t2rsp.get(mac);
		if(t2rspcmd == null){
			throw new ModuleException();
		}
		ZoneAddNodeRsp rsp = new ZoneAddNodeRsp();
		rsp.unpack(ZigbeeT2CmdPacker.unpackT2(t2rspcmd));
		return new RspStatus(rsp.getStat());
	}
	public RspStatus creatZone(NetWorkAddr nw_addr,String zoneName) throws ModuleException{
		HashMap<String, byte[]> t2Req = new HashMap<String, byte[]>();
		ZoneAddNodeReq req = new ZoneAddNodeReq();
		req.setNw_addr(nw_addr.getNw_addr());
		req.setZoneId(new byte[]{0,0});
		req.setZoneName(zoneName.getBytes());
		byte[] t2reqcmd = ZigbeeT2CmdPacker.pack(req);
		t2Req.put(mac, t2reqcmd);
		HashMap<String, byte[]> t2rsp = mm.moduleCommGet(t2Req);
		byte[] t2rspcmd = t2rsp.get(mac);
		if(t2rspcmd == null){
			throw new ModuleException();
		}
		ZoneAddNodeRsp rsp = new ZoneAddNodeRsp();
		rsp.unpack(ZigbeeT2CmdPacker.unpackT2(t2rspcmd));
		return new RspStatus(rsp.getStat());
	}
	
	
	public ArrayList<IeeeAddrInfo> getAllNodesFromZone(ZoneIdInfo zoneid) throws ModuleException{
		HashMap<String, byte[]> t2Req = new HashMap<String, byte[]>();
		ZoneGetAllNodesReq req = new ZoneGetAllNodesReq();
		req.setZoneId(zoneid.getZoneId());
		byte[] t2reqcmd = ZigbeeT2CmdPacker.pack(req);
		t2Req.put(mac, t2reqcmd);
		HashMap<String, byte[]> t2rsp = mm.moduleCommGet(t2Req);
		byte[] t2rspcmd = t2rsp.get(mac);
		if(t2rspcmd == null){
			throw new ModuleException();
		}
		ZoneGetAllNodesRsp rsp = new ZoneGetAllNodesRsp();
		rsp.unpack(ZigbeeT2CmdPacker.unpackT2(t2rspcmd));
		return rsp.getIeeeaddList();
	}
	
	public RspStatus removeNodeForZone(ZoneIdInfo zoneid,NetWorkAddr nw_addr) throws ModuleException{
		HashMap<String, byte[]> t2Req = new HashMap<String, byte[]>();
		ZoneRemoveNodeReq req = new ZoneRemoveNodeReq();
		req.setZoneId(zoneid.getZoneId());
		req.setNw_addr(nw_addr.getNw_addr());
		byte[] t2reqcmd = ZigbeeT2CmdPacker.pack(req);
		t2Req.put(mac, t2reqcmd);
		HashMap<String, byte[]> t2rsp = mm.moduleCommGet(t2Req);
		byte[] t2rspcmd = t2rsp.get(mac);
		if(t2rspcmd == null){
			throw new ModuleException();
		}
		ZoneRemoveNodeRsp rsp = new ZoneRemoveNodeRsp();
		rsp.unpack(ZigbeeT2CmdPacker.unpackT2(t2rspcmd));
		return new RspStatus(rsp.getStat());
	}
	
	public RspStatus deleteAllZone() throws ModuleException{
		HashMap<String, byte[]> t2Req = new HashMap<String, byte[]>();
		ZoneRemoveReq req = new ZoneRemoveReq();
		byte[] t2reqcmd = ZigbeeT2CmdPacker.pack(req);
		t2Req.put(mac, t2reqcmd);
		HashMap<String, byte[]> t2rsp = mm.moduleCommGet(t2Req);
		byte[] t2rspcmd = t2rsp.get(mac);
		if(t2rspcmd == null){
			throw new ModuleException();
		}
		ZoneRemoveRsp rsp = new ZoneRemoveRsp();
		rsp.unpack(ZigbeeT2CmdPacker.unpackT2(t2rspcmd));
		return new RspStatus(rsp.getStat());
	}
}
