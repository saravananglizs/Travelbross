package com.travelbross.reqhandler;

import org.json.JSONObject;

import com.travelbross.config.PropertyConfiguration;
import com.travelbross.main.Authentication;

import spark.Request;
import static spark.Spark.*;


public class RequestHandler {
	
	private static String ALLOWED_ORIGIN = PropertyConfiguration.getPropertyValue("allowedorigin");
    private static int IS_PRODUCTION_SETUP = Integer.parseInt(PropertyConfiguration.getPropertyValue("isproductionsetup"));

	public void handleRequest() {
		if(IS_PRODUCTION_SETUP == 1) {
			//SSL Connection
			port(4567);
			
			String keyStoreLocation = "/etc/letsencrypt/live/travelbross.com/keystore.jks";
			String keyStorePassword = "marvel";
			secure(keyStoreLocation, keyStorePassword, null, null);
		}
		
		options("/*", (request,response) -> {
			String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
			if(accessControlRequestHeaders != null) {
				response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
			}
			String accessControlRequestMethods = request.headers("Access-Control-Request-Methods");
			if(accessControlRequestMethods != null) {
				response.header("Access-Control-Request-Methods", accessControlRequestMethods);
			}
			return "OK";
		});
		
		before("/*", (request,response) -> {
			//setting up the response header
			response.header("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
			response.header("Access-Control-Allow-Credentials", "true");
			response.header("Pragma", "no-cache");
			response.header("Cache-Control", "no-cache");
			response.header("Expires", "0");
			
			//Handling Application Request
            //pre-flight request shouldn't be authenticated
			if(request.requestMethod().equals("POST")) {
				if(this.isLoginRequired(request)) {
					Authentication auth = new Authentication(request);
					boolean isLoginExists = auth.isLoginExists();
					if(!isLoginExists) {
						halt(401, "Unauthorized access. Access is restricted");
					}
				}
			}
		});
		
		post("/signin", (request,response) -> {
			
			return null;
		});
		
		post("/signup", (request,response) -> {
			UserRequestHandler userReqHandler =  new UserRequestHandler(request, response);
			JSONObject apiResponse =  userReqHandler.doSignup();
			return apiResponse;
		});
		
		post("/signout", (request,response) -> {
			
			return null;
		});
			
	}

	private boolean isLoginRequired(Request request) {
		String URI = request.uri();
		if(URI.equals("/signin") || URI.equals("/signup") || URI.equals("/signout")) {
			return false;
		}
		return true;
	}

}
