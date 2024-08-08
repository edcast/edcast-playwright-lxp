package com.qa.web.service;

import com.qa.pages.BaseTest;
import com.qa.pages.HomePage;
import com.qa.pages.PathwayPage;
import io.qameta.allure.testng.AllureTestNg;
import org.json.JSONArray;
import org.testng.annotations.Listeners;

import com.microsoft.playwright.Page;

@Listeners({AllureTestNg.class})
public class PathwayService extends BaseTest {
    Page page;

    public PathwayService(Page page) {
        this.page = page;
    }

    public PathwayService createPathway(String pathwayName, JSONArray cardsArray) {
        HomePage homePage = new HomePage(page);
        PathwayPage pathwayPage = homePage.clickOnCreateButton().clickPathway();
        pathwayPage.enterPathwayTitle(pathwayName);
        pathwayPage.clickOnContinueButton();
        pathwayPage.enterSmartCardSearchAndCheck(cardsArray);
        pathwayPage.clickOnAddContentButton();
        return this;
    }

    public String getCardNameByCardNumber(int positionNumber) {
        return new PathwayPage(page).getCardTitle(Integer.toString(positionNumber));
    }

    public PathwayService moverCardUnderPathway(String cardName, int positionNumber) {
        new PathwayPage(page).moveCardUnderPathway(cardName, Integer.toString(positionNumber));
        return this;
    }
}
