package com.ureca.member.dto;

public class Member {
	private String id;
	private String pwd;
	private String name;
	private String refreshToken;
	
	public Member() {
	}

	public Member(String id, String pwd, String name, String refreshToken) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.refreshToken = refreshToken;
	}



	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", pwd=" + pwd + ", name=" + name + ", refreshToken=" + refreshToken + "]";
	}


}
