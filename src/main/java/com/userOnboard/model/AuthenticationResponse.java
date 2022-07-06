package com.userOnboard.model;

public class AuthenticationResponse {
	private final String jwttoken;

	public String getJwttoken() {
		return jwttoken;
	}

	public AuthenticationResponse(String jwttoken) {
		super();
		this.jwttoken = jwttoken;
	}

	public AuthenticationResponse() {
		this.jwttoken = "";
	}
}
