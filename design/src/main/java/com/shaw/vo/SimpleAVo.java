package com.shaw.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleAVo {
	private Integer id;
	private String an;

	public SimpleAVo() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAn() {
		return an;
	}

	public void setAn(String an) {
		this.an = an;
	}

	public static Map<Integer, String> toMap(List<SimpleAVo> list) {
		Map<Integer, String> svoMap = new HashMap<Integer, String>();
		for (SimpleAVo svo : list) {
			svoMap.put(svo.getId(), svo.getAn());
		}
		return svoMap;
	}
}
