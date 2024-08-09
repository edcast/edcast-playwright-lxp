package com.qa.pages;

import org.testng.annotations.Listeners;

import com.qa.base.SetUpUtils;
import com.qa.web.service.AdminService;
import com.qa.web.service.CardService;
import com.qa.web.service.HomePageService;
import com.qa.web.service.IAuthService;

import io.qameta.allure.testng.AllureTestNg;

@Listeners({ AllureTestNg.class })
public class BaseTest extends SetUpUtils implements IAuthService {
	public HomePage homePage;
	protected SmartCardPage smartCard;
	protected CardService cardService;
	protected AdminService adminService;
	protected AdminPage adminPage;
	protected HomePageService homePageService;

}
