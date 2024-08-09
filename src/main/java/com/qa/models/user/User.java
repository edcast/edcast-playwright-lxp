package com.qa.models.user;

import java.util.List;


public class User {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String userId;
	private String fullName;
	private String handle;
	private String token;
	private String devApiKey;
	private String devApiSharedSecret;
	private String devApiAuthToken;
	private String devApiToken;
	private String csrfToken;
	private String externalId;


	public String getDevApiKey() {
		return devApiKey;
	}

	public void setDevApiKey(String devApiKey) {
		this.devApiKey = devApiKey;
	}

	public String getDevApiSharedSecret() {
		return devApiSharedSecret;
	}

	public void setDevApiSharedSecret(String devApiSharedSecret) {
		this.devApiSharedSecret = devApiSharedSecret;
	}

	public String getDevApiToken()
	{
		return devApiToken;
	}

	public void setDevApiToken(String devApiToken)
	{
		this.devApiToken = devApiToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId(){
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserFullName() {
		return fullName;
	}

	public void setUserFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDevAuthToken() {
		return devApiAuthToken;
	}

	public void setDevAuthToken(String devAuthToken) {
		this.devApiAuthToken = devAuthToken;
	}

	public String getCsrfToken(){
		return csrfToken;
	}

	public void setCsrfToken(String csrfToken){
		this.csrfToken = csrfToken;
	}

	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle){
		this.handle = handle;
	}
	
	

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

    @Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password=" + password + ", userId=" + userId
				+ ", fullName=" + fullName + ", handle=" + handle +  ", token=" + token + ", devApiKey=" + devApiKey
				+ ", devApiSharedSecret=" + devApiSharedSecret + ", devApiAuthToken=" + devApiAuthToken + ", devApiToken=" + devApiToken
				+ ", csrfToken=" + csrfToken +  ", externalId=" + externalId + "]";
    }

}
