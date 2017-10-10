package com.travelbross.reqhandler;

import org.json.JSONObject;

import com.travelbross.config.Config;
import com.travelbross.service.UserService;
import com.travelbross.dbhandler.UserDBHandler;
import com.travelbross.model.UserModel;
import com.travelbross.util.DateUtil;

import spark.Request;
import spark.Response;

public class UserRequestHandler {
	
	private String action = "";
	private Request req;
	private Response res;
	
	public UserRequestHandler(Request request, Response response) {
		this.action = request.queryParams("action");
		this.req = request;
		this.res = response;
		System.out.println("Action:::::"+this.action);
	}
	
	public JSONObject processResquest() {
		JSONObject response = new JSONObject();
		if(this.action.equals("DoSignup")) {
			response = this.doSignup();
		} else {
			res.status(404);
			res.type("application/json");
		}
		return response;
	}

	public JSONObject doSignup() {
		
		JSONObject response = new JSONObject();
		boolean status = false;
		String message = "Unable to setup the account at this time";
		
		//get user information
		String firstName = req.queryParams("first_name");
		String lastName = req.queryParams("last_name");
		String emailID = req.queryParams("email");
		String password = req.queryParams("password");
		String timezoneName = req.queryParams("timezone_name");
		String timezoneOffset = req.queryParams("timezone_offset");
		String timezone = DateUtil.identifyUserTimezone(timezoneOffset, timezoneName);
		long currentTime = DateUtil.getCurrentUTCTime();
		long createdBy = 0;
		long modifiedBy = 0;
		long userId = 0;
		
		// check whether the email is already registered
		UserDBHandler userDBH = new UserDBHandler(req);
		boolean isEmailExists = userDBH.isEmailIDExists(emailID);
		if(!isEmailExists) {
			UserModel userModel = new UserModel();
			userModel.setFirstName(firstName);
			userModel.setLastName(lastName);
			userModel.setEmailId(emailID);
			userModel.setPassword(password);
			userModel.setTimezone(timezone);
			userModel.setCreatedDate(currentTime);
			userModel.setModifiedDate(currentTime);
			userModel.setCreatedBy(createdBy);
			userModel.setModifiedBy(modifiedBy);
			
			userId = userDBH.createUser(userModel);
			if(userId > 0) {
				status = true;
				message = "Your account has been created successfully! Welcome Mail sent to the registered email ID";
				//send account activation email
//				UserService userService = new UserService(req);
//				userService.sendAccountActivationEmail(firstName, emailID);
				
				/* User Acitvity Log */
				int activityType = Config.USER_ACTIVITY_ERROR;
				if (status) {
					activityType = Config.USER_ACTIVITY_SUCCESS;
				}

				userDBH.addUserActivity(userId, this.action, activityType, message, DateUtil.getCurrentUTCTime());
			}
		} else {
			status = false;
			message = "It looks like you already have an account with travelbross using this email ID."
					+ " Try using a different email ID";
		}
		response.put("status", (status ? 1 : 0));
		response.put("message", message);
		
		return response;
	}	
	
}
