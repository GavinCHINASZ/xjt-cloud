package com.xjt.cloud.admin.manage.common.utils;

import java.util.ArrayList;
import java.util.List;
public class ScriptPage<T> {
	private List<T> rows = new ArrayList<T>();
	private Integer total = 0;

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
		if (rows.isEmpty() || rows.get(0) == null){
			return;
		}

		this.rows = rows;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}
