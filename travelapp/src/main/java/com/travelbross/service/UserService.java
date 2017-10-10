package com.travelbross.service;

import spark.Request;

public class UserService {
	private Request req;
	
	public UserService(Request request) {
		this.req = request;
	}

	public void sendAccountActivationEmail(String firstName, String emailID) {
		
		
	}
}
