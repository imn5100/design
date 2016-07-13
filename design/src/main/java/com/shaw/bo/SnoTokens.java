package com.shaw.bo;

import java.io.Serializable;

public class SnoTokens implements  Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 499711454146683115L;

	private Integer id;

	private String code;

	private Integer status;

	private Integer sid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}
}