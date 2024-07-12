package com.qa.utils;

import java.util.Properties;

public class PropertiesEmail {
	String host = "imap.gmail.com";
	String user;
	String password;
	int port = 993;
	
	public PropertiesEmail(String email, String password) {
		this.user = email;
		this.password = password;
	}

	public Properties setServerProperties(){
	    Properties properties = new Properties();
	    properties.put("mail.imap.host", host);
	    properties.put("mail.imap.port", port);
	    properties.put("mail.imap.starttls.enable", "true");
	    properties.put("mail.store.protocol", "imaps");
	    return properties;
	}	
}
