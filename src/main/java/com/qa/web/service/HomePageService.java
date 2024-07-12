package com.qa.web.service;

import com.qa.pages.AdminPage;
import com.qa.pages.BaseTest;
import com.qa.pages.HomePage;
import com.qa.pages.SmartCardPage;
import com.qa.utils.CommonUtils;
import com.qa.utils.EmailUtils;

import dev.brachtendorf.jimagehash.hash.Hash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;
import dev.brachtendorf.jimagehash.hashAlgorithms.PerceptiveHash;
import net.coobird.thumbnailator.Thumbnails;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.google.gson.Gson;
import com.microsoft.playwright.Page;
import com.qa.base.PlaywrightFactory;
import com.qa.enums.*;

public class HomePageService extends BaseTest {
	
	Page page;

	public HomePageService(Page page) {
		this.page = page;
		homePage = new HomePage(page);

	}

	public HomePageService cardSearch(String cardName) {

		return homePage.cardSearchOnHomePage(cardName);

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

	public String downloadPDFContent() {

		new EmailUtils().getEmailInfo(config.getProperty("adminEmail1"), config.getProperty("adminGmailKeyAccess"),
				testData.getString("emailSubjectLine"));
		System.out.println(testData.getString("file-name"));
		String pdfContent = new CommonUtils().readContentFromPDF(testData.getString("file-name"));
		return pdfContent;
	}

	public void matchPDFContent(String pdfContent) {
		for (Object key : testData.keySet()) {
			String keyStr = (String) key;
			Object value = testData.get(keyStr);
			System.out.println("Key: " + keyStr + ", Value: " + value);
			pdfContent.contains(value.toString());

		}

	}
}
