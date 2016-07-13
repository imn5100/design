package com.shaw.bo;

import java.io.Serializable;

public class Subject implements  Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -334069327880172782L;

	private Integer id;

    private String name;

    private Float score;

    private String detail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }
}