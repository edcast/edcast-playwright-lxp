package com.qa.testcases;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.microsoft.playwright.Page;
import com.qa.base.PlaywrightFactory;
import com.qa.enums.CardTypes;
import static com.qa.utils.Constants.Test_DATA.*;
import io.qameta.allure.Step;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.stream.Stream;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.qa.pages.BaseTest;
import com.qa.utils.CommonUtils;
import com.qa.utils.DBUtils;
import com.qa.utils.OTPUtils;
import com.qa.web.service.AdminService;
import com.qa.web.service.CardService;
import com.qa.web.service.HomePageService;
import com.qa.web.service.PathwayService;

import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;

import io.qameta.allure.AllureId;
import io.qameta.allure.Description;

@Listeners({ AllureTestNg.class })
public class Trautomationv extends BaseTest {
	@Test()
	@Description("This test case checks that only one user session is maintained.")
	public void testConcurrentUserTest() throws InterruptedException {
		String loginUrl = "https://trautomationv11.cmnetwork.co/user/login";
		String expectedUrlAfterLogin = "https://trautomationv11.cmnetwork.co/";
		loginPage.trautomationvLogin(page);
		verifyPageUrl(expectedUrlAfterLogin, page);
		Page pageOnNewBrowser = openNewBrowser(loginUrl);
		loginPage.trautomationvLogin(pageOnNewBrowser);
		verifyPageUrl(loginUrl, page);

	}
	
	
	public void verifyPageUrl(String expectedUrl, Page page) {
		String url;
		int count = 5;
		do {
			page.reload();
			url = page.mainFrame().url();
			count--;
			page.waitForTimeout(1000);

		} while (url != expectedUrl && count != 0);
		logStep("Expected url is : " + expectedUrl);
		Assert.assertTrue(url.contains(expectedUrl), "Expected Url not found");

	}
}
