package com.mywinestore.bo;

public class User {
	
	private String userName;
	private String password;
	private String userRule;
	
	public User(String userName, String password, String userRule) {
		this.userName = userName;
		this.password = password;
		this.userRule = userRule;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserRule() {
		return userRule;
	}

	public void setUserRule(String userRule) {
		this.userRule = userRule;
	}
}
