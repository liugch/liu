package com.edu.snmp.template;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.edu.snmp.core.InfoBase;
import org.apache.commons.lang3.StringUtils;



public class Template extends InfoBase {

	public static final String SIMPLE = "simple";
	public static final String CRONTAB = "crontab";
	public static final String DEVICE = "device";
	public static final String BUSINESS = "business";
	public static final String STATE_UP = "up";
	public static final String STATE_DOWN = "down";

	private String uuid;
	private String name;
	private String alias;
	private String ctime;
	private String tpid;
	private String moid;
	private String type = DEVICE; // "device" or "business"
	private boolean clone;
	private String status = STATE_UP;

	private List<String> holidays = new ArrayList<String>();
	private Map<String, String> scripts = new LinkedHashMap<String, String>();
	private Map<String, String> appInfos = new LinkedHashMap<String, String>();
	private Map<String, List<String>> scheExps = new LinkedHashMap<String, List<String>>();

	public Template baseClone(Template template) {
		Template ct = this.clone();
		ct.setUuid(template.getUuid());
		ct.setMoid(template.getMoid());
		ct.setClone(template.isClone());
		ct.setAppInfos(template.getAppInfos());
		return ct;
	}

	public Template clone() {
		Template template = new Template();
		template.uuid = this.uuid;
		template.name = this.name;
		template.alias = this.alias;
		template.moid = this.moid;
		template.type = this.type;
		template.setTpid(this.tpid);
		template.setCtime(this.ctime);
		template.setClone(this.clone);
		template.setStatus(this.status);

		Map<String, String> uris = new LinkedHashMap<String, String>();
		Iterator<Entry<String, String>> it2 = this.appInfos.entrySet()
				.iterator();
		while (it2.hasNext()) {
			Entry<String, String> entry = it2.next();
			uris.put(entry.getKey(), entry.getValue());
		}
		template.setAppInfos(uris);

		List<String> hs = new ArrayList<String>();
		for (String h : this.holidays) {
			hs.add(h);
		}
		template.setHolidays(hs);

		Map<String, List<String>> sps = new LinkedHashMap<String, List<String>>();
		Iterator<Entry<String, List<String>>> it3 = this.scheExps.entrySet()
				.iterator();
		while (it3.hasNext()) {
			Entry<String, List<String>> entry = it3.next();
			List<String> ls = new ArrayList<String>();
			for (String s : entry.getValue()) {
				ls.add(s);
			}
			sps.put(entry.getKey(), ls);
		}
		template.setScheExps(sps);

		Map<String, String> sts = new LinkedHashMap<String, String>();
		Iterator<Entry<String, String>> it4 = this.scripts.entrySet()
				.iterator();
		while (it4.hasNext()) {
			Entry<String, String> entry = it4.next();
			sts.put(entry.getKey(), entry.getValue());
		}
		template.setScripts(sts);

		Map<String, String> ext = new LinkedHashMap<String, String>();
		Iterator<Entry<String, String>> it5 = this.extAtt.entrySet().iterator();
		while (it5.hasNext()) {
			Entry<String, String> entry = it5.next();
			ext.put(entry.getKey(), entry.getValue());
		}
		template.setExtAtt(ext);

		return template;
	}

	@Override
	public String toString() {
		return "Template [uuid=" + uuid + ", name=" + name + ", alias=" + alias
				+ ", ctime=" + ctime + ", tpid=" + tpid + ", moid=" + moid
				+ ", type=" + type + ", clone=" + clone + ", status=" + status
				+ ", holidays=" + holidays + ", appInfos=" + appInfos
				+ ", scheExps=" + scheExps + ", extAtt=" + extAtt
				+ ", scripts=" + scripts + "]";
	}

	public int hcode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		result = prime * result
				+ ((holidays == null) ? 0 : holidays.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((scheExps == null) ? 0 : scheExps.hashCode());
		result = prime * result + ((scripts == null) ? 0 : scripts.hashCode());
		return result;
	}

	public List<String> getScheExps(String type) {
		synchronized (this.getClass()) {
			List<String> exps = scheExps.get(type);
			if (exps == null) {
				exps = new ArrayList<String>();
			}
			return exps;
		}
	}

	public void addScheExp(String type, String exp) {
		if (!scheExps.containsKey(type)) {
			scheExps.put(type, new ArrayList<String>());
		}
		scheExps.get(type).add(exp);
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getTpid() {
		return tpid;
	}

	public void setTpid(String tpid) {
		this.tpid = tpid;
	}

	public String getMoid() {
		return moid;
	}

	public void setMoid(String moid) {
		this.moid = moid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if (StringUtils.isEmpty(type)) {
			type = DEVICE;
		}
		this.type = type;
	}

	public boolean isClone() {
		return clone;
	}

	public void setClone(boolean clone) {
		this.clone = clone;
	}

	public List<String> getHolidays() {
		return holidays;
	}

	public void setHolidays(List<String> holidays) {
		this.holidays = holidays;
	}

	public Map<String, String> getScripts() {
		return scripts;
	}

	public void setScripts(Map<String, String> scripts) {
		this.scripts = scripts;
	}

	public Map<String, String> getAppInfos() {
		return appInfos;
	}

	public void setAppInfos(Map<String, String> appInfos) {
		this.appInfos = appInfos;
	}

	public Map<String, List<String>> getScheExps() {
		return scheExps;
	}

	public void setScheExps(Map<String, List<String>> scheExps) {
		this.scheExps = scheExps;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
