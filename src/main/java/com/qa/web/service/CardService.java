package com.qa.web.service;

import com.qa.pages.BaseTest;
import com.qa.pages.HomePage;
import com.qa.pages.SmartCardPage;
import io.qameta.allure.testng.AllureTestNg;

import org.json.JSONObject;
import org.testng.annotations.Listeners;

import com.microsoft.playwright.Page;
import com.qa.enums.*;

@Listeners({AllureTestNg.class})
public class CardService extends BaseTest {

    public CardService createCard(Page page, CardTypes val, String cardName) {
        switch (val) {
            case UPLOAD:
                new HomePage(page).clickOnCreateButton().clickSmartCard().clickOnUploadContent().setCardDetails(cardName);
        }
        return this;
    }

    public String getToastMessageAfterSavingCard(Page page) {
        return new SmartCardPage(page).getToastMessage();
    }

    public String getToastMessageAfterSavingCard1(Page page) {
        return new SmartCardPage(page).getToastMessage(page);

    }

    public int getCardImageHammingDistance(String cardName, Page page, JSONObject val) {
        return new SmartCardPage(page).captureCardImageAndGetHammingDistance(cardName, page, val);

    }

    public HomePageService clickOnAssignCard(Page page, String cardName, String startDate, String endDate) {
        HomePage homePage = new HomePage(page);
        homePage.cardSearchOnHomePage(cardName);
        homePage.clickOnCardAssign(page, startDate, endDate);
        return new HomePageService();

    }

    public CardService clickOnSelfAssignCardWithDate(Page page, String cardName, String startDate, String endDate) {
        HomePage homePage = new HomePage(page);
        homePage.cardSearchOnHomePage(cardName);
        homePage.clickOnCardAssign(page, startDate, endDate);
        return this;
    }

    public Boolean isAssignToMeOptionPresent(Page page, String cardName) {
        HomePage homePage = new HomePage(page);
        homePage.cardSearchOnHomePage(cardName);
        return homePage.IsAssignToMePresent();

    }
}
