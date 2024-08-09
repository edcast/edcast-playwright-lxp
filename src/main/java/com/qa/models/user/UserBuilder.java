package com.qa.models.user;



import static com.qa.utils.Constants.USER_DATA.*;







//TODO[anazarenko]: Make it as a singleton or if it's a builder it should be stateless
public class UserBuilder {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String fullName;
	private String handle;
	private String externalId;

	private UserBuilder() {
	}

	public static UserBuilder newInstance() {
		return new UserBuilder();
	}

	

	

	public UserBuilder withPicassoAdmin() {
		this.email = PICASSO_ADMIN_USER_EMAIL;
		this.password = PICASSO_ADMIN_USER_PASSWORD;	
		return this;
	}
	public UserBuilder withTrautmationVAdmin() {
		this.email = TRAUTMATIONV_ADMIN_USER_EMAIL;
		this.password = TRAUTMATIONV_ADMIN_USER_PASSWORD;	
		return this;
	}
	

	public User build() {
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		user.setUserFullName(fullName);
		user.setHandle(handle);
		user.setExternalId(externalId);
		return user;
	}
	

}
