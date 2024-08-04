package com.qa.pages;

import java.util.Properties;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;

public class LoginPage extends BaseTest {
	Page page;
	Properties config;
	private String emailInput = "//input[@placeholder='Email']";
	private String passwordInput = "//input[@placeholder='Password']";
	private String logInButton = "//span[@class='button-loader']/parent::button[@class='login-page-button'] | //button[contains(text(),'Login')]";
	private String loginCheckBoxId = "id=tandc";

	public LoginPage(Page page, Properties config) {
		this.page = page;
		this.config = config;
	}

	public HomePage adminLogin() {
		page.getByLabel("Email").fill(config.getProperty("picasso.adminEmail"));
		page.getByLabel("Password").fill(config.getProperty("picasso.adminPassword"));
		page.click(loginCheckBoxId);
		page.click(logInButton);
		return new HomePage(page);
	}

	public HomePage memberLogin() {
		page.locator(emailInput).fill(config.getProperty("picasso.adminEmail1"));
		page.locator(passwordInput).fill(config.getProperty("picasso.adminPassword1"));
		page.click(loginCheckBoxId);
		page.click(logInButton);
		return new HomePage(page);
	}
	public HomePage trautomationvLogin(Page page) {
	    ElementHandle loginCheckBox = page.querySelector("id=tandc");
		page.locator(emailInput).fill(config.getProperty("trautomationv.adminEmail"));
		page.locator(passwordInput).fill(config.getProperty("trautomationv.adminPassword"));
		if (loginCheckBox != null)
		page.click(loginCheckBoxId);
		page.click(logInButton);
		return new HomePage(page);
	}
}
