package com.travelbross.dbhandler;

import org.sql2o.Sql2o;

import com.travelbross.config.PropertyConfiguration;

public class DBHandler {
	protected Sql2o sql2o;
	private String hostname = PropertyConfiguration.getPropertyValue("dbhostname");
	private String port = PropertyConfiguration.getPropertyValue("dbport");
	private String username = PropertyConfiguration.getPropertyValue("dbusername");
	private String password = PropertyConfiguration.getPropertyValue("dbpassword");
	private String database = PropertyConfiguration.getPropertyValue("database");
	
	public DBHandler() {
		String conStr = "jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database;
		this.sql2o = new Sql2o(conStr, this.username, this.password);
	}
}
