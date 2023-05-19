package com.bezkoder.springjwt.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//只有用户，运输员不登陆了
public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;
    @NotNull
	private int type=0;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
