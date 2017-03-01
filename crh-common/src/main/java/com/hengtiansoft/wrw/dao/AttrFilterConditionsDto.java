package com.hengtiansoft.wrw.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class AttrFilterConditionsDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7562972193804634386L;
	private Map<Long, String> attrMap;
	private Map<Long, List<String>> attrValuesMap;
	public Map<Long, String> getAttrMap() {
		return attrMap;
	}
	public void setAttrMap(Map<Long, String> attrMap) {
		this.attrMap = attrMap;
	}
	public Map<Long, List<String>> getAttrValuesMap() {
		return attrValuesMap;
	}
	public void setAttrValuesMap(Map<Long, List<String>> attrValuesMap) {
		this.attrValuesMap = attrValuesMap;
	}
}
