package com.travelbross.model;

import lombok.Data;

@Data
public class UserModel {
	private String userId;
	private String firstName;
	private String lastName;
	private String emailId;
	private String password;
	private int status;
	private String timezone;
	private long createdDate;
    private long modifiedDate;
    private long createdBy;
    private long modifiedBy;
	
}
