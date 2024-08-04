package com.qa.web.service;

import com.qa.pages.BaseTest;
import com.qa.pages.HomePage;
import com.qa.pages.SmartCardPage;
import dev.brachtendorf.jimagehash.hash.Hash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;
import dev.brachtendorf.jimagehash.hashAlgorithms.PerceptiveHash;
import io.qameta.allure.testng.AllureTestNg;
import net.coobird.thumbnailator.Thumbnails;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.json.JSONObject;
import org.testng.annotations.Listeners;

import com.microsoft.playwright.Page;
import com.qa.enums.*;
@Listeners({AllureTestNg.class})
public class CardService extends BaseTest {
	Page page;

	public CardService(Page page)

	{
		this.page = page;
		smartCard = new SmartCardPage(page);
		homePage = new HomePage(page);

	}

	public CardService createCard(CardTypes val, String cardName) {
		switch (val) {
		case UPLOAD:	
			homePage.clickOnCreateButton().clickSmartCard().clickOnUploadContent().setCardDetails(cardName);
		}

		return this;
	}

	public String getToastMessageAfterSavingCard() {
		return smartCard.getToastMessage();

	}

	public String getToastMessageAfterSavingCard1() {
		return smartCard.getToastMessage(page);

	}

	public int getCardImageHammingDistance(String cardName, Page page, JSONObject val) {
		return smartCard.captureCardImageAndGetHammingDistance(cardName, page, val);

	}

	public HomePageService clickOnAssignCard(Page page, String cardName, String startDate, String endDate) {
		homePage.cardSearchOnHomePage(cardName);
		homePage.clickOnCardAssign(page, startDate, endDate);
		return new HomePageService(page);

	}

	public CardService clickOnSelfAssignCardWithDate(Page page, String cardName, String startDate, String endDate) {
		homePage.cardSearchOnHomePage(cardName);
		homePage.clickOnCardAssign(page, startDate, endDate);
		return this;
	}
public Boolean isAssignToMeOptionPresent(String cardName) {
	homePage.cardSearchOnHomePage(cardName);
	return homePage.IsAssignToMePresent();
	
}
}
