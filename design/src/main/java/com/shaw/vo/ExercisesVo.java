package com.shaw.vo;

import com.shaw.bo.Exercises;

public class ExercisesVo extends Exercises {
	private static final long serialVersionUID = -4857618718205098846L;
	private OptionVo optionVo;

	public OptionVo getOptionVo() {
		return optionVo;
	}

	public void setOptionVo(OptionVo optionVo) {
		this.optionVo = optionVo;
	}

	private String userAnswer;
	private Integer toTeacherId;
	private String toTeacherName;
	private String aUsername;
	private Integer aUid;

	public String getToTeacherName() {
		return toTeacherName;
	}

	public void setToTeacherName(String toTeacherName) {
		this.toTeacherName = toTeacherName;
	}

	public String getaUsername() {
		return aUsername;
	}

	public void setaUsername(String aUsername) {
		this.aUsername = aUsername;
	}

	public Integer getaUid() {
		return aUid;
	}

	public void setaUid(Integer aUid) {
		this.aUid = aUid;
	}

	public String getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	public Integer getToTeacherId() {
		return toTeacherId;
	}

	public void setToTeacherId(Integer toTeacherId) {
		this.toTeacherId = toTeacherId;
	}

}
