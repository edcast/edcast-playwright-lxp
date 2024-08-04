package com.qa.web.service;

import com.qa.pages.BaseTest;
import com.qa.pages.HomePage;
import com.qa.pages.PathwayPage;
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

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Listeners;

import com.microsoft.playwright.Page;
import com.qa.enums.*;
@Listeners({AllureTestNg.class})
public class PathwayService extends BaseTest {
	Page page;	
	public PathwayService(Page page)

	{
		this.page = page;
		pathwayPage = new PathwayPage(page);
		homePage = new HomePage(page);

	}
	public PathwayService createPathway(String pathwayName,JSONArray cardsArray) {
		homePage.clickOnCreateButton().clickPathway();
		pathwayPage.enterPathwayTitle(pathwayName);
		pathwayPage.clickOnContinueButton();
		pathwayPage.enterSmartCardSearchAndCheck(cardsArray);
		pathwayPage.clickOnAddContentButton();
		return this;
	}
	public String getCardNameByCardNumber(int positionNumber) {
		return pathwayPage.getCardTitle(Integer.toString(positionNumber));
	}

	public PathwayService moverCardUnderPathway(String cardName, int positionNumber) {
	pathwayPage.moveCardUnderPathway(cardName,Integer.toString(positionNumber));
	
	return this;
	
	
}
}
