package com.travelbross.main;

import spark.Request;

public class Authentication {
	private Request req;
	public Authentication(Request request) {
		this.req = request;
	}
	
	public boolean isLoginExists() {
		
		return false;
	}

}
