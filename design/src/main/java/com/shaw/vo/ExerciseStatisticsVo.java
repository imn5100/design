package com.shaw.vo;

public class ExerciseStatisticsVo {
	private String subjectName;
	private Integer subjectId;
	private Integer type;
	private Integer count;
	private Integer countSubject;
	private Integer countType;

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getCountSubject() {
		return countSubject;
	}

	public void setCountSubject(Integer countSubject) {
		this.countSubject = countSubject;
	}

	public Integer getCountType() {
		return countType;
	}

	public void setCountType(Integer countType) {
		this.countType = countType;
	}

}
