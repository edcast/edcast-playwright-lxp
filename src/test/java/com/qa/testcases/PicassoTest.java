package com.qa.testcases;

import static com.qa.utils.Constants.Test_DATA.ADMIN_PAGE_TEST_DATA;
import static com.qa.utils.Constants.Test_DATA.PAATHWAY_TEST_DATA;
import static com.qa.utils.Constants.Test_DATA.SKILL_PASSPORT_TEST_DATA;
import static com.qa.utils.Constants.Test_DATA.SMART_CARD_TEST_DATA;
import java.sql.SQLException;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.qa.enums.CardTypes;
import com.qa.models.user.User;
import com.qa.pages.BaseTest;
import com.qa.utils.DBUtils;
import com.qa.utils.OTPUtils;
import com.qa.web.service.AdminService;
import com.qa.web.service.CardService;
import com.qa.web.service.HomePageService;
import com.qa.web.service.PathwayService;
import io.qameta.allure.Description;
import io.qameta.allure.testng.AllureTestNg;

@Listeners({ AllureTestNg.class })
public class PicassoTest extends BaseTest {

	@Test()

	@Description("Part -1 In this test case existing users are deleted")
	public void testDeleteExistingUser5() {
		loginPage.get().adminLogin(preparePicassoAdminAuth());
		// testData = getTestData(ADMIN_PAGE_TEST_DATA);
		getPage(AdminService.class, webNode.get()).openAdminAccountTab()
				.deleteExistingUser(getTestData(ADMIN_PAGE_TEST_DATA).getJSONArray("new_user_data"));

	}

	@Test()
	@Description("Part -2 Users file is downloaded and uploaded with user details. "
			+ "After uploading users details users are onboarded after fetchng the link provided in the mail box.")
	public void testDownloadUserFileAndUploadUsersDetailsAndUserOnboarding() {
		loginPage.get().adminLogin(preparePicassoAdminAuth());
		// testData = getTestData(ADMIN_PAGE_TEST_DATA);
		getPage(AdminService.class, webNode.get()).openAdminAccountTab()
				.deleteExistingUser(getTestData(ADMIN_PAGE_TEST_DATA).getJSONArray("new_user_data"))
				.downloadSampleFileAndUploadUserDetails1(
						getTestData(ADMIN_PAGE_TEST_DATA).getJSONArray("new_user_data"))
				.getEmailConfirmationAndUserOnBoarding(getTestData(ADMIN_PAGE_TEST_DATA));

	}

	@Test()
	@Description("Part -1 In this test case Toast message is verified after creating the card")
	public void testVerifyToastMessage() throws InterruptedException {
		loginPage.get().adminLogin(preparePicassoAdminAuth());
		String testCard = "Test_Card_ " + RandomStringUtils.randomAlphanumeric(15);
		// testData = getTestData(SMART_CARD_TEST_DATA);
		cardService = getPage(CardService.class, webNode.get()).createCard(CardTypes.UPLOAD, testCard,
				getTestData(SMART_CARD_TEST_DATA));
		Assert.assertEquals(cardService.getToastMessageAfterSavingCard1(webNode.get()),
				"Your card has been published publicly and will be accessible to everyone.", "Toast message not found");
	}

	@Test()

	@Description("Part -2 In this test case card is assigned to self with calendar")
	public void testSelfAssignCard() throws InterruptedException {
		loginPage.get().adminLogin(preparePicassoAdminAuth());
		String testCard = "Test_Card_ " + RandomStringUtils.randomAlphanumeric(15);
		// testData = getTestData(SMART_CARD_TEST_DATA);
		cardService = getPage(CardService.class, webNode.get()).createCard(CardTypes.UPLOAD, testCard,
				getTestData(SMART_CARD_TEST_DATA));
		
		cardService.clickOnSelfAssignCardWithDate(webNode.get(), testCard, "6-November-2024", "7-November-2024");
		Boolean value =cardService.isAssignToMeOptionPresent(webNode.get(),testCard);
		Assert.assertFalse(value,
				"After assigning to me, Assign to me option not available");

	}

	@Test()

	@Description("Part -3 In this test case card image is compared")
	public void testCardImageCompare() throws InterruptedException {
		loginPage.get().adminLogin(preparePicassoAdminAuth());
		String testCard = "Test_Card_ " + RandomStringUtils.randomAlphanumeric(15);
		JSONObject testData = getTestData(SMART_CARD_TEST_DATA);
		cardService = getPage(CardService.class, webNode.get()).createCard(CardTypes.UPLOAD, testCard,
				testData);
		getPage(HomePageService.class, webNode.get()).cardSearch(testCard,webNode.get());
		int hammingDistanceValue = cardService.getCardImageHammingDistance(testCard, webNode.get(),
				testData);
		Assert.assertTrue(hammingDistanceValue <= 3,
				"Images are not identical: Hamming value is " + hammingDistanceValue);
	}

	@Test()

	@Description("This test case checks PDF content after downloading content from the email.")
	public void pdfContentTest() {
		loginPage.get().adminLogin(preparePicassoAdminAuth());
		JSONObject testData = getTestData(SKILL_PASSPORT_TEST_DATA);
		// testData = getTestData(SKILL_PASSPORT_TEST_DATA);
		homePageService = getPage(HomePageService.class, webNode.get()).clickGetTranscript();
		homePageService.matchPDFContent(homePageService.downloadPDFContent(testData),
				testData);

	}

	/*
	 * Case is failing. In a professional setting, SMS APIs require a registered
	 * phone number to send messages, which must be purchased from the service
	 * provider. Once you register the phone number and add funds to your account,
	 * the code will work correctly.
	 */
	@Test()

	@Description("This test case sends OTP on the registered phone.")
	public void OTPTest() {
		logStep("Starting the test: OTPTest");
		String to = "+919717467417"; // recipient's phone number
		String otp = new OTPUtils().generateOTP(6);
		String message = "Your OTP code is: " + otp;
		new OTPUtils().sendSMS(to, message);

	}

	@Test()

	@Description("This test case checks drag and drop feature")
	public void testDragAndDropUnderPathway() throws InterruptedException {
		loginPage.get().adminLogin(preparePicassoAdminAuth());
		String pathwayName = "Test Card for Pathway";
		String CardNameToMove = "Test Card for Pathway A";
		int positionNumber = 2;
		// testData = getTestData(PAATHWAY_TEST_DATA);
		pathwayService = getPage(PathwayService.class, webNode.get())
				.createPathway(pathwayName, getTestData(PAATHWAY_TEST_DATA).getJSONArray("cards"))
				.moverCardUnderPathway(CardNameToMove, positionNumber);
		Assert.assertEquals(pathwayService.getCardNameByCardNumber(positionNumber), CardNameToMove,
				"Card position is not correct");
	}

	@Test()
	@Description("This test case fetch column name from the table"
			+ "In order to run this case ake sure that VPN is working properly")
	public void getColumnNameFromSourcesTable() throws ClassNotFoundException, SQLException {
		String url = "SELECT column_name FROM information_schema.columns WHERE table_name = 'sources' ORDER BY ordinal_position;";
		logStep("Executing the query: " + url);
		new DBUtils().printDBDetails(url);
	}

//	
///*	@Test()
//	@Description("Complete case - In this test case existing users are deleted"+
//			"Users file is downloaded and uploaded with user details. "
//			+ "After uploading users details users are onboarded after fetchng the provided in the mail box."
//			)
//	public void testUserOnboarding() {
//		loginPage.adminLogin();
//		testData = getTestData(ADMIN_PAGE_TEST_DATA);
//		getPage(AdminService.class, page).openAdminAccountTab()
//				.downloadSampleFileAndUploadUserDetails1(testData.getJSONArray("new_user_data"))
//				.getEmailConfirmationAndUserOnBoarding(testData);
//
//	}
//	@Test
//	@Description("Complete case : Part -1 In this test case Toast message is verified after creating the card"
//			+"Part -2 In this test case card is assigned to self with calendar"
//			+"Part -3 In this test case card image is compared"
//			)
//	public void testCreateImageCard() throws InterruptedException {
//		loginPage.adminLogin();
//		String testCard = "Test_Card_ " + RandomStringUtils.randomAlphanumeric(15);
//		testData = getTestData(SMART_CARD_TEST_DATA);
//		cardService = getPage(CardService.class, page).createCard(CardTypes.UPLOAD, testCard);
//
//		Assert.assertEquals(cardService.getToastMessageAfterSavingCard1(),
//				"Your card has been published publicly and will be accessible to everyone.", "Toast message not found");
//		cardService.clickOnSelfAssignCardWithDate(page, testCard, "6-November-2024", "7-November-2024");
//		getPage(HomePageService.class, page).cardSearch(testCard);
//		System.out.println(cardService.getCardImageHammingDistance(testCard, page, testData));
//		Assert.assertTrue(cardService.getCardImageHammingDistance(testCard, page, testData) <= 2,
//				"Images are not identical");
//
//	}
//	*/
}
