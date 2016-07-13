package com.shaw.vo;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.shaw.bo.Exercises;
import com.shaw.utils.TimeUtils;

public class ExercisesShowVo extends Exercises {
	private static final long serialVersionUID = -8002600380738500122L;
	private String showCreateTime;
	private String showType;
	private String answerA;
	private String answerB;
	private String answerC;
	private String answerD;

	public ExercisesShowVo(Exercises exercises) {
		super();
		BeanUtils.copyProperties(exercises, this);
		if (exercises.getType() == 1) {
			OptionVo opvo = JSONObject.parseObject(exercises.getOptions(), OptionVo.class);
			this.answerA = opvo.getAnswerA();
			this.answerB = opvo.getAnswerB();
			this.answerC = opvo.getAnswerC();
			this.answerD = opvo.getAnswerD();
			this.showType = "选择题";
		} else {
			this.showType = "简答题";
		}
	}

	public String getShowCreateTime() {
		showCreateTime = TimeUtils.getFormatTimeYMDHM(super.getCreatetime());
		return showCreateTime;
	}

	public String getAnswerA() {
		return answerA;
	}

	public void setAnswerA(String answerA) {
		this.answerA = answerA;
	}

	public String getAnswerB() {
		return answerB;
	}

	public void setAnswerB(String answerB) {
		this.answerB = answerB;
	}

	public String getAnswerC() {
		return answerC;
	}

	public void setAnswerC(String answerC) {
		this.answerC = answerC;
	}

	public String getAnswerD() {
		return answerD;
	}

	public void setAnswerD(String answerD) {
		this.answerD = answerD;
	}

	public void setShowCreateTime(String showCreateTime) {
		this.showCreateTime = showCreateTime;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

}
