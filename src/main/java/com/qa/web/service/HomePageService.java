package com.qa.web.service;

import java.util.Properties;

import org.json.JSONObject;

import com.microsoft.playwright.Page;
import com.qa.pages.BaseTest;
import com.qa.pages.HomePage;
import com.qa.utils.CommonUtils;
import com.qa.utils.EmailUtils;
public class HomePageService extends BaseTest {
	
	Page page;

	public HomePageService(Page page) {
		this.page = page;
		homePage = new HomePage(page);

	}

	public HomePageService cardSearch(String cardName,Page page) {

		return homePage.cardSearchOnHomePage(cardName,page);

	}

	public HomePageService fillDefaultOnboardingInfo(Page page) {

		return homePage.fillDefaultOnboardingInfo(page);

	}

	public String getUserNameAfterFirstLoginOnScreen(Page page) {

		return homePage.getUserNameAfterFirstLogin(page);

	}

	public HomePageService clickSkillsPassport() {
		homePage.clickSkillsPassport();
		return this;

	}
	public HomePageService clickGetTranscript() {
		homePage.clickSkillsPassport();
		return this;

	}
	
	
	public String downloadPDFContent(JSONObject testData) {

		new EmailUtils().getEmailInfo(config.getProperty("picasso.adminEmail1"), config.getProperty("picasso.adminGmailKeyAccess"),
				testData.getString("emailSubjectLine"));
		String pdfContent = new CommonUtils().readContentFromPDF(testData.getString("file-name"));
		return pdfContent;
	}

	public void matchPDFContent(String pdfContent,JSONObject testData) {
		for (Object key : testData.keySet()) {
			String keyStr = (String) key;
			Object value = testData.get(keyStr);
			System.out.println("Key: " + keyStr + ", Value: " + value);
			pdfContent.contains(value.toString());

		}

	}
}
