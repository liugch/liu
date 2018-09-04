package com.edu.snmp.groovy.util;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InterfaceVO {

	public InterfaceVO(String index, String descr, String type, String mtu, String speed, String physAddress,
			String adminStatus, String operStatus, String lastChange, String inOctets, String inUcastPkts,
			String inNUcastPkts, String inDiscards, String inErrors, String inUnknownProtos, String outOctets,
			String outUcastPkts, String outNUcastPkts, String outDiscards, String outErrors, String outQLen,
			String specific) {
		this.index = Integer.parseInt(index);
		this.Descr = DecodingUtil.decoding(descr, "gbk");
		this.Type = getType(type);
		this.Mtu = Integer.parseInt(mtu);
		this.Speed = Long.parseLong(speed);
		this.PhysAddress = physAddress;
		this.AdminStatus = getAdminStatus(adminStatus);
		this.OperStatus = getOperStatus(operStatus);
		this.LastChange = lastChange;
		this.InOctets = Long.parseLong(inOctets);
		this.InUcastPkts = Long.parseLong(inUcastPkts);
		this.InNUcastPkts = Long.parseLong(inNUcastPkts);
		this.InDiscards = Long.parseLong(inDiscards);
		this.InErrors = Long.parseLong(inErrors);
		this.InUnknownProtos = Long.parseLong(inUnknownProtos);
		this.OutOctets = Long.parseLong(outOctets);
		this.OutUcastPkts = Long.parseLong(outUcastPkts);
		this.OutNUcastPkts = Long.parseLong(outNUcastPkts);
		this.OutDiscards = Long.parseLong(outDiscards);
		this.OutErrors = Long.parseLong(outErrors);
		this.OutQLen = Long.parseLong(outQLen);
		this.Specific = specific;
	}

	private Integer index;
	private String Descr;
	private String Type;
	private Integer Mtu;
	private Long Speed;
	private String PhysAddress;
	private String AdminStatus;
	private String OperStatus;
	private String LastChange;
	private Long InOctets;
	private Long InUcastPkts;
	private Long InNUcastPkts;
	private Long InDiscards;
	private Long InErrors;
	private Long InUnknownProtos;
	private Long OutOctets;
	private Long OutUcastPkts;
	private Long OutNUcastPkts;
	private Long OutDiscards;
	private Long OutErrors;
	private Long OutQLen;
	private String Specific;

	private Date measureTime;

	static {
		HashMap<String, String> type_map = new HashMap<String, String>();
		type_map.put("1", "other");
		type_map.put("2", "regular1822");
		type_map.put("3", "hdh1822");
		type_map.put("4", "ddnX25");
		type_map.put("5", "rfc877x25");
		type_map.put("6", "ethernetCsmacd");
		type_map.put("7", "iso88023Csmacd");
		type_map.put("8", "iso88024TokenBus");
		type_map.put("9", "iso88025TokenRing");
		type_map.put("10", "iso88026Man");
		type_map.put("11", "starLan");
		type_map.put("12", "proteon10Mbit");
		type_map.put("13", "proteon80Mbit");
		type_map.put("14", "hyperchannel");
		type_map.put("15", "fddi");
		type_map.put("16", "lapb");
		type_map.put("17", "sdlc");
		type_map.put("18", "ds1");
		type_map.put("19", "e1");
		type_map.put("20", "basicISDN");
		type_map.put("21", "primaryISDN");
		type_map.put("22", "propPointToPointSerial");
		type_map.put("23", "ppp");
		type_map.put("24", "softwareLoopback");
		type_map.put("25", "eon");
		type_map.put("26", "ethernet3Mbit");
		type_map.put("27", "nsip");
		type_map.put("28", "slip");
		type_map.put("29", "ultra");
		type_map.put("30", "ds3");
		type_map.put("31", "sip");
		type_map.put("32", "frameRelay");
		type_map.put("33", "rs232");
		type_map.put("34", "para");
		type_map.put("35", "arcnet");
		type_map.put("36", "arcnetPlus");
		type_map.put("37", "atm");
		type_map.put("38", "miox25");
		type_map.put("39", "sonet");
		type_map.put("40", "x25ple");
		type_map.put("41", "iso88022llc");
		type_map.put("42", "localTalk");
		type_map.put("43", "smdsDxi");
		type_map.put("44", "frameRelayService");
		type_map.put("45", "v35");
		type_map.put("46", "hssi");
		type_map.put("47", "hippi");
		type_map.put("48", "modem");
		type_map.put("49", "aal5");
		type_map.put("50", "sonetPath");
		type_map.put("51", "sonetVT");
		type_map.put("52", "smdsIcip");
		type_map.put("53", "propVirtual");
		type_map.put("54", "propMultiplexor");
		type_map.put("55", "ieee80212");
		type_map.put("56", "fibreChannel");
		type_map.put("57", "hippiInterface");
		type_map.put("58", "frameRelayInterconnect");
		type_map.put("59", "aflane8023");
		type_map.put("60", "aflane8025");
		type_map.put("61", "cctEmul");
		type_map.put("62", "fastEther");
		type_map.put("63", "isdn");
		type_map.put("64", "v11");
		type_map.put("65", "v36");
		type_map.put("66", "g703at64k");
		type_map.put("67", "g703at2mb");
		type_map.put("68", "qllc");
		type_map.put("69", "fastEtherFX");
		type_map.put("70", "channel");
		type_map.put("71", "ieee80211");
		type_map.put("72", "ibm370parChan");
		type_map.put("73", "escon");
		type_map.put("74", "dlsw");
		type_map.put("75", "isdns");
		type_map.put("76", "isdnu");
		type_map.put("77", "lapd");
		type_map.put("78", "ipSwitch");
		type_map.put("79", "rsrb");
		type_map.put("80", "atmLogical");
		type_map.put("81", "ds0");
		type_map.put("82", "ds0Bundle");
		type_map.put("83", "bsc");
		type_map.put("84", "async");
		type_map.put("85", "cnr");
		type_map.put("86", "iso88025Dtr");
		type_map.put("87", "eplrs");
		type_map.put("88", "arap");
		type_map.put("89", "propCnls");
		type_map.put("90", "hostPad");
		type_map.put("91", "termPad");
		type_map.put("92", "frameRelayMPI");
		type_map.put("93", "x213");
		type_map.put("94", "adsl");
		type_map.put("95", "radsl");
		type_map.put("96", "sdsl");
		type_map.put("97", "vdsl");
		type_map.put("98", "iso88025CRFPInt");
		type_map.put("99", "myrinet");
		type_map.put("100", "voiceEM");
		type_map.put("101", "voiceFXO");
		type_map.put("102", "voiceFXS");
		type_map.put("103", "voiceEncap");
		type_map.put("104", "voiceOverIp");
		type_map.put("105", "atmDxi");
		type_map.put("106", "atmFuni");
		type_map.put("107", "atmIma");
		type_map.put("108", "pppMultilinkBundle");
		type_map.put("109", "ipOverCdlc");
		type_map.put("110", "ipOverClaw");
		type_map.put("111", "stackToStack");
		type_map.put("112", "virtualIpAddress");
		type_map.put("113", "mpc");
		type_map.put("114", "ipOverAtm");
		type_map.put("115", "iso88025Fiber");
		type_map.put("116", "tdlc");
		type_map.put("117", "gigabitEthernet");
		type_map.put("118", "hdlc");
		type_map.put("119", "lapf");
		type_map.put("120", "v37");
		type_map.put("121", "x25mlp");
		type_map.put("122", "x25huntGroup");
		type_map.put("123", "transpHdlc");
		type_map.put("124", "interleave");
		type_map.put("125", "fast");
		type_map.put("126", "ip");
		type_map.put("127", "docsCableMaclayer");
		type_map.put("128", "docsCableDownstream");
		type_map.put("129", "docsCableUpstream");
		type_map.put("130", "a12MppSwitch");
		type_map.put("131", "tunnel");
		type_map.put("132", "coffee");
		type_map.put("133", "ces");
		type_map.put("134", "atmSubInterface");
		type_map.put("135", "l2vlan");
		type_map.put("136", "l3ipvlan");
		type_map.put("137", "l3ipxvlan");
		type_map.put("138", "digitalPowerline");
		type_map.put("139", "mediaMailOverIp");
		type_map.put("140", "dtm");
		type_map.put("141", "dcn");
		type_map.put("142", "ipForward");
		type_map.put("143", "msdsl");
		type_map.put("144", "ieee1394");
		type_map.put("145", "if-gsn");
		type_map.put("146", "dvbRccMacLayer");
		type_map.put("147", "dvbRccDownstream");
		type_map.put("148", "dvbRccUpstream");
		type_map.put("149", "atmVirtual");
		type_map.put("150", "mplsTunnel");
		type_map.put("151", "srp");
		type_map.put("152", "voiceOverAtm");
		type_map.put("153", "voiceOverFrameRelay");
		type_map.put("154", "idsl");
		type_map.put("155", "compositeLink");
		type_map.put("156", "ss7SigLink");
		type_map.put("157", "propWirelessP2P");
		type_map.put("158", "frForward");
		type_map.put("159", "rfc1483");
		type_map.put("160", "usb");
		type_map.put("161", "ieee8023adLag");
		type_map.put("162", "bgppolicyaccounting");
		type_map.put("163", "frf16MfrBundle");
		type_map.put("164", "h323Gatekeeper");
		type_map.put("165", "h323Proxy");
		type_map.put("166", "mpls");
		type_map.put("167", "mfSigLink");
		type_map.put("168", "hdsl2");
		type_map.put("169", "shdsl");
		type_map.put("170", "ds1FDL");
		type_map.put("171", "pos");
		type_map.put("172", "dvbAsiIn");
		type_map.put("173", "dvbAsiOut");
		type_map.put("174", "plc");
		type_map.put("175", "nfas");
		type_map.put("176", "tr008");
		type_map.put("177", "gr303RDT");
		type_map.put("178", "gr303IDT");
		type_map.put("179", "isup");
		type_map.put("180", "propDocsWirelessMaclayer");
		type_map.put("181", "propDocsWirelessDownstream");
		type_map.put("182", "propDocsWirelessUpstream");
		type_map.put("183", "hiperlan2");
		type_map.put("184", "propBWAp2Mp");
		type_map.put("185", "sonetOverheadChannel");
		type_map.put("186", "digitalWrapperOverheadChannel");
		type_map.put("187", "aal2");
		type_map.put("188", "radioMAC");
		type_map.put("189", "atmRadio");
		type_map.put("190", "imt");
		type_map.put("191", "mvl");
		type_map.put("192", "reachDSL");
		type_map.put("193", "frDlciEndPt");
		type_map.put("194", "atmVciEndPt");
		type_map.put("195", "opticalChannel");
		type_map.put("196", "opticalTransport");
		type_map.put("197", "propAtm");
		type_map.put("198", "voiceOverCable");
		type_map.put("199", "infiniband");
		type_map.put("200", "teLink");
		type_map.put("201", "q2931");
		type_map.put("202", "virtualTg");
		type_map.put("203", "sipTg");
		type_map.put("204", "sipSig");
		type_map.put("205", "docsCableUpstreamChannel");
		type_map.put("206", "econet");
		type_map.put("207", "pon155");
		type_map.put("208", "pon622");
		type_map.put("209", "bridge");
		type_map.put("210", "linegroup");
		type_map.put("211", "voiceEMFGD");
		type_map.put("212", "voiceFGDEANA");
		type_map.put("213", "voiceDID");
		type_map.put("214", "mpegTransport");
		type_map.put("215", "sixToFour");
		type_map.put("216", "gtp");
		type_map.put("217", "pdnEtherLoop1");
		type_map.put("218", "pdnEtherLoop2");
		type_map.put("219", "opticalChannelGroup");
		type_map.put("220", "homepna");
		type_map.put("221", "gfp");
		type_map.put("222", "ciscoISLvlan");
		type_map.put("223", "actelisMetaLOOP");
		type_map.put("224", "fcipLink");
		type_map.put("225", "rpr");
		type_map.put("226", "qam");
		type_map.put("227", "lmp");
		type_map.put("228", "cblVectaStar");
		type_map.put("229", "docsCableMCmtsDownstream");
		type_map.put("230", "adsl2");
		type_map.put("231", "macSecControlledIF");
		type_map.put("232", "macSecUncontrolledIF");
		type_map.put("233", "aviciOpticalEther");
		type_map.put("234", "atmbond");
		type_map.put("235", "voiceFGDOS");
		type_map.put("236", "mocaVersion1");
		type_map.put("237", "ieee80216WMAN");
		type_map.put("238", "adsl2plus");
		type_map.put("239", "dvbRcsMacLayer");
		type_map.put("240", "dvbTdm");
		type_map.put("241", "dvbRcsTdma");
		type_map.put("242", "x86Laps");
		type_map.put("243", "wwanPP");
		type_map.put("244", "wwanPP2");
		type_map.put("245", "voiceEBS");
		type_map.put("246", "ifPwType");
		type_map.put("247", "ilan");
		type_map.put("248", "pip");
		type_map.put("249", "aluELP");
		type_map.put("250", "gpon");
		type_map.put("251", "vdsl2");

		TYPE_MAP = Collections.unmodifiableMap(type_map);
	}

	private static final Map<String, String> TYPE_MAP;

	private static String getType(final String typeNum) {
		String typeStr = TYPE_MAP.get(typeNum);
		return typeStr != null ? typeStr : "unknown";
	}

	private static String getAdminStatus(final String statusNum) {
		if ("1".equals(statusNum)) {
			return "up";
		}
		if ("2".equals(statusNum)) {
			return "down";
		}
		if ("3".equals(statusNum)) {
			return "testing";
		} else {
			return "unknown";
		}
	}

	private static String getOperStatus(final String statusNum) {
		if ("1".equals(statusNum)) {
			return "up";
		}
		if ("2".equals(statusNum)) {
			return "down";
		}
		if ("3".equals(statusNum)) {
			return "testing";
		}
		if ("4".equals(statusNum)) {
			return "unknown";
		}
		if ("5".equals(statusNum)) {
			return "dormant";
		}
		if ("6".equals(statusNum)) {
			return "notPresent";
		}
		if ("7".equals(statusNum)) {
			return "lowerLayerDown";
		} else {
			return "unknown";
		}
	}

	public Integer getIndex() {
		return index;
	}

	public String getDescr() {
		return Descr;
	}

	public String getType() {
		return Type;
	}

	public int getMtu() {
		return Mtu;
	}

	public Long getSpeed() {
		return Speed;
	}

	public String getPhysAddress() {
		return PhysAddress;
	}

	public String getAdminStatus() {
		return AdminStatus;
	}

	public String getOperStatus() {
		return OperStatus;
	}

	public String getLastChange() {
		return LastChange;
	}

	public Long getInOctets() {
		return InOctets;
	}

	public Long getInUcastPkts() {
		return InUcastPkts;
	}

	public Long getInNUcastPkts() {
		return InNUcastPkts;
	}

	public Long getInDiscards() {
		return InDiscards;
	}

	public Long getInErrors() {
		return InErrors;
	}

	public Long getInUnknownProtos() {
		return InUnknownProtos;
	}

	public Long getOutOctets() {
		return OutOctets;
	}

	public Long getOutUcastPkts() {
		return OutUcastPkts;
	}

	public Long getOutNUcastPkts() {
		return OutNUcastPkts;
	}

	public Long getOutDiscards() {
		return OutDiscards;
	}

	public Long getOutErrors() {
		return OutErrors;
	}

	public Long getOutQLen() {
		return OutQLen;
	}

	public String getSpecific() {
		return Specific;
	}

	public Date getMeasureTime() {
		return measureTime;
	}

	public void setMeasureTime(Date measureTime) {
		this.measureTime = measureTime;
	}

	@Override
	public String toString() {
		return "Interface [Descr=" + Descr + ", Type=" + Type + ", Mtu=" + Mtu + ", Speed=" + Speed + ", PhysAddress="
				+ PhysAddress + ", AdminStatus=" + AdminStatus + ", OperStatus=" + OperStatus + ", LastChange="
				+ LastChange + ", InOctets=" + InOctets + ", InUcastPkts=" + InUcastPkts + ", InNUcastPkts="
				+ InNUcastPkts + ", InDiscards=" + InDiscards + ", InErrors=" + InErrors + ", InUnknownProtos="
				+ InUnknownProtos + ", OutOctets=" + OutOctets + ", OutUcastPkts=" + OutUcastPkts + ", OutNUcastPkts="
				+ OutNUcastPkts + ", OutDiscards=" + OutDiscards + ", OutErrors=" + OutErrors + ", OutQLen=" + OutQLen
				+ ", Specific=" + Specific + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((Descr == null) ? 0 : Descr.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		InterfaceVO other = (InterfaceVO) obj;
		if (Descr == null) {
			if (other.Descr != null) {
				return false;
			}
		} else if (!Descr.equals(other.Descr)) {
			return false;
		}
		return true;
	}

}
