package com.qa.base;

import org.testng.annotations.Parameters;
import com.microsoft.playwright.*;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightFactory  {

	protected static Playwright playwright;
	protected static Page page;
	protected static Browser browser;
	static BrowserContext browserContext;

	// @Parameters({"appURL", "browserType"})
	public static Page getPage(String appURL, String browserType) {
		playwright = Playwright.create();
		switch (browserType) {
		case "chrome":
			browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
			break;
		case "chromium":
			browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			break;
		case "firefox":
			browser = playwright.firefox()
					.launch(new BrowserType.LaunchOptions().setChannel("firefox").setHeadless(false));
			break;
		case "safari":
			browser = playwright.webkit()
					.launch(new BrowserType.LaunchOptions().setChannel("safari").setHeadless(false));
			break;
		default:
			break;
		}

		browserContext = browser.newContext();
		page = browserContext.newPage();
		page.navigate(appURL);
		return page;
	}

	public static Page openNewTab(String url) {
		Page secondPage = browserContext.newPage();
		secondPage.navigate(url);
		secondPage.bringToFront();
		return secondPage;
	}
	
	public static Page openNewBrowser(String url) {
		BrowserContext browserContext= browser.newContext();;
		Page page = browserContext.newPage();
		page.navigate(url);
		page.bringToFront();
		return page;
	}
}
