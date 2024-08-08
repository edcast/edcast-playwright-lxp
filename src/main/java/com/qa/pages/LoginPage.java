package com.qa.pages;

import java.util.Properties;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;

public class LoginPage extends PageBase {

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

    private Page getPage() {
        return page;
    }

    public HomePage adminLogin() {
        getPage().getByLabel("Email").fill(config.getProperty("picasso.adminEmail"));
        getPage().getByLabel("Password").fill(config.getProperty("picasso.adminPassword"));
        getPage().click(loginCheckBoxId);
        getPage().click(logInButton);
        return new HomePage(getPage());
    }

    public HomePage memberLogin() {
        getPage().locator(emailInput).fill(config.getProperty("picasso.adminEmail1"));
        getPage().locator(passwordInput).fill(config.getProperty("picasso.adminPassword1"));
        getPage().click(loginCheckBoxId);
        getPage().click(logInButton);
        return new HomePage(getPage());
    }

    public HomePage trautomationvLogin(Page page) {
        page.locator(emailInput).fill(config.getProperty("trautomationv.adminEmail"));
        page.locator(passwordInput).fill(config.getProperty("trautomationv.adminPassword"));
        ElementHandle loginCheckBox = page.querySelector("id=tandc");
        if (loginCheckBox != null) {
            page.click(loginCheckBoxId);
        }
        page.click(logInButton);
        return new HomePage(page);
    }
}
