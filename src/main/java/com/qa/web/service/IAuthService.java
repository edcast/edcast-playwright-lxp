package com.qa.web.service;

import com.qa.models.user.User;
import com.qa.models.user.UserBuilder;

public interface IAuthService {
	public default User preparePicassoAdminAuth() {
		User user = UserBuilder.newInstance().withPicassoAdmin().build();
		return user;
	}


}
