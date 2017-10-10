package com.travelbross.main;

import com.travelbross.reqhandler.RequestHandler;

public class Main {
	
	public static void main(String args[]) {
		try {
			//starting the inbuilt webserver
			RequestHandler reqHandler = new RequestHandler();
			reqHandler.handleRequest();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
