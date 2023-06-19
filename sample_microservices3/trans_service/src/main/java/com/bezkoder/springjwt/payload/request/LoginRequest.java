package com.bezkoder.springjwt.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//运输员和用户都可以登录
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
