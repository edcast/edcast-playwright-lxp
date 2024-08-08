package com.qa.testcases;

import com.microsoft.playwright.Page;
import com.qa.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.qa.pages.BaseTest;
import io.qameta.allure.testng.AllureTestNg;
import io.qameta.allure.Description;

@Listeners({AllureTestNg.class})
public class Trautomationv extends BaseTest {

    @Test()
    @Description("This test case checks that only one user session is maintained.")
    public void testConcurrentUserTest() {
        String loginUrl = "https://trautomationv11.cmnetwork.co/user/login";
        String expectedUrlAfterLogin = "https://trautomationv11.cmnetwork.co/";
        new LoginPage(playwrightSessionThreadLocal.get().getPage(), config).trautomationvLogin(playwrightSessionThreadLocal.get().getPage());
        verifyPageUrl(expectedUrlAfterLogin, playwrightSessionThreadLocal.get().getPage());
        Page pageOnNewBrowser = openNewBrowser(loginUrl);
        new LoginPage(playwrightSessionThreadLocal.get().getPage(), config).trautomationvLogin(pageOnNewBrowser);
        verifyPageUrl(loginUrl, playwrightSessionThreadLocal.get().getPage());
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
