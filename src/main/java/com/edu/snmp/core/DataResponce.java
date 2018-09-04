package com.edu.snmp.core;

import java.util.ArrayList;
import java.util.List;

public class DataResponce<T> {
	private Integer total = new Integer(0);
	private List<T> data=new ArrayList<T>();
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public void AddData(T data)
	{
		this.data.add(data);
	}
}
