package com.shaw.utils;

import java.util.HashMap;

public class ResponseMap<K> extends HashMap<String, Object> {
	private static final long serialVersionUID = 299062793015637371L;
	private K data = null;
	private Integer code = ErrorCode.SUCCESS;
	private String msg = "";
	public static final String RETURN_CODE = "code";
	public static final String DATA = "data";
	public static final String RETURN_MSG = "msg";

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.put(RETURN_MSG, msg);
		this.msg = msg;
	}

	public ResponseMap() {
		this.put(RETURN_CODE, code);
	}

	public K getData() {
		return data;
	}

	public void setData(K data) {
		this.data = data;
		this.put(DATA, data);
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
		this.put(RETURN_CODE, code);
	}
	
	public void setBizErrorCode(){
		this.code = ErrorCode.BIZERROR;
		this.put(RETURN_CODE, code);
	}

}
