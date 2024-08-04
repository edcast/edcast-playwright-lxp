package com.qa.base;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Properties;

import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import com.microsoft.playwright.*;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.qameta.allure.testng.AllureTestNg;

@Listeners({ AllureTestNg.class })
public class PlaywrightFactory  {

	protected static Playwright playwright;
	protected static Page page;
	protected static Browser browser;
	static BrowserContext browserContext;
	protected static Path path;

	// @Parameters({"appURL", "browserType"})
	public static Page getPage(String appURL, String browserType, Properties config) {
		playwright = Playwright.create();
		Boolean headlessState = Boolean.parseBoolean(config.getProperty("headlessState"));
		switch (browserType) {
		case "chrome":
			browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(headlessState));
			break;
		case "chromium":
			browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headlessState));
			break;
		case "firefox":
			browser = playwright.firefox()
					.launch(new BrowserType.LaunchOptions().setChannel("firefox").setHeadless(headlessState));
			break;
		case "safari":
			browser = playwright.webkit()
					.launch(new BrowserType.LaunchOptions().setChannel("safari").setHeadless(headlessState));
			break;
		default:
			break;
		}

		if (Boolean.parseBoolean(config.getProperty("video-recording"))) {
			path = Paths.get(
					config.getProperty("video-recording-files")+ LocalDateTime.now().toString() .replace(":", "_").replace(".", "_"));
			browserContext = browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(path).setRecordVideoSize(1280, 720) );
		} else
			browserContext = browser.newContext();
		page = browserContext.newPage();
		page.setDefaultTimeout(Integer.parseInt(config.getProperty("setDefaultTime"))*1000);
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
		BrowserContext browserContext = browser.newContext();
		Page page = browserContext.newPage();
		page.navigate(url);
		page.bringToFront();
		return page;
	}
}
