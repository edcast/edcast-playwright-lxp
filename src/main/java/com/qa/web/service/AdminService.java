package com.qa.web.service;

import com.qa.pages.AdminPage;

import com.qa.pages.BaseTest;
import com.qa.pages.HomePage;
import com.qa.pages.SmartCardPage;
import com.qa.utils.EmailUtils;

import dev.brachtendorf.jimagehash.hash.Hash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;
import dev.brachtendorf.jimagehash.hashAlgorithms.PerceptiveHash;
import io.qameta.allure.testng.AllureTestNg;
import net.coobird.thumbnailator.Thumbnails;

import static com.qa.utils.Constants.Test_DATA.ADMIN_PAGE_TEST_DATA;
import static com.qa.utils.Constants.Test_DATA.HOME_PAGE_TEST_DATA;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import com.google.gson.Gson;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.qa.base.PlaywrightFactory;
import com.qa.enums.*;
@Listeners({AllureTestNg.class})

public class AdminService extends BaseTest {
	Page page;

	public AdminService(Page page) {
		this.page = page;
		homePage = new HomePage(page);
		adminPage = new AdminPage(page);

	}

	public AdminService openAdminAccountTab() {		
		homePage.clickOnMoreButton();
		adminPage = homePage.clickOnAdminButton();
		adminPage.clickOnAccountTab();
		adminPage.clickOnUsersTab();
		return this;
	}

	public <T extends BaseTest> AdminService downloadSampleFileAndUploadUserDetails1(JSONArray val) {
		adminPage.clickOnUsersTab();
		adminPage.deleteExistingUser(val);
		adminPage.clickOnAddUsersButton();
		adminPage.downloadUsersSampleFile();
		adminPage.uploadSampleFileWithUsersDetails(val);
		return this;
	}
	public <T extends BaseTest> AdminService deleteExistingUser(JSONArray val) {
		adminPage.clickOnUsersTab();
		//adminPage.deleteExistingUser(val);
		return this;
	}
	public AdminService getEmailConfirmationAndUserOnBoarding(JSONObject testData) {
		JSONArray val = testData.getJSONArray("new_user_data");
		homePageService = new HomePageService(page);
		for (int i = 0; i <= val.length() - 1; i++) {
			JSONArray newRow11 = val.getJSONArray(i);
			String[] stringArray = new Gson().fromJson(newRow11.toString(), String[].class);
			String inviteURL = new EmailUtils().getEmailInfo(stringArray[2], testData.getString(stringArray[2]),
					testData.getString("emailSubjectLine").toString());
			page = PlaywrightFactory.openNewTab(inviteURL);
			homePageService.fillDefaultOnboardingInfo(page);
			Assert.assertTrue(homePageService.getUserNameAfterFirstLoginOnScreen(page)
					.contains(stringArray[0] + " " + stringArray[1]),"User Name on the dashboard is not correct");

		}

		return this;
	}
}
