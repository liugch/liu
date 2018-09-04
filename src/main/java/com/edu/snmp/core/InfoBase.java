package com.edu.snmp.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class InfoBase {
	public final static String TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	protected Map<String, String> extAtt = new LinkedHashMap<String, String>();

	public Map<String, String> getExtAtt() {
		return extAtt;
	}

	public void setExtAtt(Map<String, String> extAtt) {
		this.extAtt = extAtt;
	}

	public void incExtAtt(String key, String value) {
		String vs = extAtt.get(key);
		if (StringUtils.isEmpty(vs)) {
			vs = value;
		} else {
			List<String> ls = new ArrayList<String>(Arrays.asList(vs
					.split("\\|")));
			if (!ls.contains(value))
				vs += "|" + value;
		}
		extAtt.put(key, vs);
	}

	public void decExtAtt(String key, String value) {
		String vs = extAtt.get(key);
		if (vs == null)
			return;
		List<String> ls = new ArrayList<String>(Arrays.asList(vs.split("\\|")));
		if (ls.contains(value)) {
			ls.remove(value);
			if (ls.size() == 0) {
				extAtt.remove(key);
			} else {
				String s = "";
				for (String l : ls) {
					s += l + "|";
				}
				s = s.substring(0, s.length() - 1);
				extAtt.put(key, s);
			}
		}
	}

	public String takeExtAtt(String key) {
		String s = extAtt.get(key);
		if (s == null) {
			s = "";
		}
		return s;
	}

	public void offerExtAtt(String key, String value) {
		extAtt.put(key, value);
	}

	public String takeAtt(String key) {
		String ret = null;
		if (key != null) {
			Method m;
			try {
				m = this.getClass().getMethod("get" + capitalize(key));
				ret = (String) m.invoke(this);
			} catch (NoSuchMethodException e) {
				ret = takeExtAtt(key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	public void offerAtt(String key, String value) {
		try {
			if (key != null) {
				Method m = this.getClass().getMethod("set" + capitalize(key),
						String.class);
				m.invoke(this, value);
			} else {
				offerExtAtt(key, value);
			}
		} catch (NoSuchMethodException e) {
			offerExtAtt(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String capitalize(String val) {
		char[] charArray = val.toCharArray();
		charArray[0] = Character.toUpperCase(charArray[0]);
		return new String(charArray);
	}
}
