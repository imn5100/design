package com.shaw.vo;

import java.util.Map;

public class ExerciseStatisticsDataVo {
	private Integer type1 = 0;
	private Integer type2 = 0;
	private Map<String, Integer> subjectNameCount;

	public Integer getType1() {
		return type1;
	}

	public void setType1(Integer type1) {
		this.type1 = type1;
	}

	public Integer getType2() {
		return type2;
	}

	public void setType2(Integer type2) {
		this.type2 = type2;
	}

	public Map<String, Integer> getSubjectNameCount() {
		return subjectNameCount;
	}

	public void setSubjectNameCount(Map<String, Integer> subjectNameCount) {
		this.subjectNameCount = subjectNameCount;
	}

}
