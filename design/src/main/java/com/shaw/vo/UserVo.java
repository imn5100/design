package com.shaw.vo;

import com.shaw.bo.UserInfo;

public class UserVo extends UserInfo {
	private static final long serialVersionUID = 2104390380806667208L;
	private String username;
	private String role;
	private String nick;
	private Integer infoId;

	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
