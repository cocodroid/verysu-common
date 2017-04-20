package com.verysu.basic.model;

import java.util.List;

/**
 * easyui分页
 * @author Sjg
 * 2014-10-7
 * 下午04:21:58
 */
public class EasyUIPager <T> {
	/**
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(long total) {
		this.total = total;
	}
	/**
	 * 总的记录数
	 */
	private long total;
	/**
	 * 分页数据
	 */
	private List<T> rows;

	
	/**
	 * @return the rows
	 */
	public List<T> getRows() {
		return rows;
	}
	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public EasyUIPager() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EasyUIPager(long total, List<T> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
}
