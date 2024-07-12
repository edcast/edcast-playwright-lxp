package com.qa.pages;

import java.util.Properties;

import com.microsoft.playwright.Page;

public class LoginPage extends BaseTest {
	Page page;
	Properties config;
	private String emailInput = "//input[@placeholder='Email']";
	private String passwordInput = "//input[@placeholder='Password']";
	private String logInButton = "//span[@class='button-loader']/parent::button[@class='login-page-button'] | //button[contains(text(),'Login')]";
	private String loginCheckBox = "id=tandc";

	LoginPage(Page page, Properties config) {
		this.page = page;
		this.config = config;
	}

	public HomePage adminLogin() {
		page.getByLabel("Email").fill(config.getProperty("adminEmail"));
		page.getByLabel("Password").fill(config.getProperty("adminPassword"));
		page.click(loginCheckBox);
		page.click(logInButton);
		return new HomePage(page);
	}

	public HomePage memberLogin() {
		page.locator(emailInput).fill(config.getProperty("adminEmail1"));
		page.locator(passwordInput).fill(config.getProperty("adminPassword1"));
		page.click(loginCheckBox);
		page.click(logInButton);
		return new HomePage(page);
	}
}
