package com.travelbross.dbhandler;

import java.sql.SQLException;

import org.sql2o.Connection;
import org.sql2o.Sql2oException;
import org.sql2o.data.Table;

import com.travelbross.model.UserModel;

import spark.Request;

public class UserDBHandler extends DBHandler{
	
	public UserDBHandler(Request request) {
		super();
	}
	
	public boolean isEmailIDExists(String email) {
		boolean status = false;
		try(Connection con = sql2o.open()) {
			Table t = con.createQuery("select email from user where email=:email")
					.addParameter("email", email)
					.executeAndFetchTable();
			if(t.rows().size() > 0) {
				status = true;
			}
		} catch(Sql2oException e) {
			SQLException originalException = (SQLException) e.getCause();
			System.out.println("isEmailIDExists ::" + originalException.getLocalizedMessage());
		}
		return status;
	}

	public long createUser(UserModel userModel) {
		long userID = 0;
		try(Connection con = sql2o.open()) {
			userID = con.createQuery("insert into user(first_name, last_name, email, password, status, created_at, "
					+ "modified_at, created_by, modified_by) values(:first_name, :last_name, :email, :password, "
					+ ":status, :created_at, :modified_at, :created_by, :modified_by)", true)
					.addParameter("first_name", userModel.getFirstName())
					.addParameter("last_name", userModel.getLastName())
					.addParameter("email", userModel.getEmailId())
					.addParameter("password", userModel.getPassword())
					.addParameter("status", userModel.getStatus())
					.addParameter("created_at", userModel.getCreatedDate())
					.addParameter("modified_at", userModel.getModifiedDate())
					.addParameter("created_by", userModel.getCreatedBy())
					.addParameter("modified_by", userModel.getModifiedBy())
					.executeUpdate()
					.getKey(Long.class);
		} catch(Sql2oException e) {
			SQLException originalException = (SQLException) e.getCause();
			System.out.println("CreateUser :: "+originalException.getLocalizedMessage());
		}
		return userID;
	}

	public long addUserActivity(long userId, String action, int activityType,
			String message, long currentUTCTime) {
		long userID = 0;
		try(Connection con = sql2o.open()) {
			userID = con.createQuery("insert into user_activity(user_id, action, type, message, created_at) "
					+ "values (:user_id, :action, :type, :message, :created_at)", true)
					.addParameter("user_id", userID)
					.addParameter("action", action)
					.addParameter("type", activityType)
					.addParameter("message", message)
					.addParameter("created_at", currentUTCTime)
					.executeUpdate()
					.getKey(Long.class);
		} catch(Sql2oException e) {
			SQLException originalException = (SQLException) e.getCause();
			System.out.println("AddUserActivity :: " + originalException.getLocalizedMessage());
		}
		return userID;
	}
}
