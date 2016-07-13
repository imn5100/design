package com.shaw.vo;

import java.util.List;

public class SinaNewsResponse {
	Integer is_intro;
	List<SineNews> list;

	public Integer getIs_intro() {
		return is_intro;
	}

	public void setIs_intro(Integer is_intro) {
		this.is_intro = is_intro;
	}

	public List<SineNews> getList() {
		return list;
	}

	public void setList(List<SineNews> list) {
		this.list = list;
	}
}