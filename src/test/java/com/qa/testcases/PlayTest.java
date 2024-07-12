package com.qa.testcases;

import com.microsoft.playwright.Page;
import com.qa.enums.CardTypes;
import static com.qa.utils.Constants.Test_DATA.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.qa.pages.BaseTest;
import com.qa.utils.CommonUtils;
import com.qa.utils.OTPUtils;
import com.qa.web.service.AdminService;
import com.qa.web.service.CardService;
import com.qa.web.service.HomePageService;
import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;

import io.qameta.allure.AllureId;
import io.qameta.allure.Description;

public class PlayTest extends BaseTest {

	@Test()
	public void testUserOnboarding() {
		loginPage.adminLogin();
		testData = getTestData(ADMIN_PAGE_TEST_DATA);
		getPage(AdminService.class, page).openAdminAccountTab()
				.downloadSampleFileAndUploadUserDetails1(testData.getJSONArray("new_user_data"))
				.getEmailConfirmationAndUserOnBoarding(testData);

	}

	@Test
	public void testCreateImageCard() throws InterruptedException {

		loginPage.adminLogin();
		String testCard = "Test_Card_ " + RandomStringUtils.randomAlphanumeric(15);
		testData = getTestData(SMART_CARD_TEST_DATA);
		cardService = getPage(CardService.class, page).createCard1(CardTypes.UPLOAD, testCard);

		Assert.assertEquals(cardService.getToastMessageAfterSavingCard1(),
				"Your card has been published publicly and will be accessible to everyone.", "Toast message not found");
		cardService.clickOnSelfAssignCardWithDate1(page, testCard, "6-November-2024", "7-November-2024");
		getPage(HomePageService.class, page).cardSearch(testCard);

		Assert.assertTrue(cardService.getCardImageHammingDistance(testCard, page, testData) <= 1,
				"Images are not identical");

	}

	@Test()
	public void pdfContentTest() {
		loginPage.memberLogin();
		testData = getTestData(SKILL_PASSPORT_TEST_DATA);
		homePageService = getPage(HomePageService.class, page).clickSkillsPassport();
		homePageService.matchPDFContent(homePageService.downloadPDFContent());

	}

	/*
	 * Case is failing.
	 * In a professional setting, SMS APIs require a registered
	 * phone number to send messages, which must be purchased from the service
	 * provider. Once you register the phone number and add funds to your account,
	 * the code will work correctly.
	 */
	@Test
	public void OTPTest() {
		String to = "+919717467417"; // recipient's phone number
		String otp = new OTPUtils().generateOTP(6);
		String message = "Your OTP code is: " + otp;
		new OTPUtils().sendSMS(to, message);

	}
}
