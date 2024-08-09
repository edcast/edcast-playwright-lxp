package com.qa.base;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Properties;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import com.microsoft.playwright.*;
import com.qa.pages.LoginPage;

import io.qameta.allure.testng.AllureTestNg;

@Listeners({ AllureTestNg.class })
public class PlaywrightFactory {
	private static ThreadLocal<Playwright> threadLocalPlaywright = new ThreadLocal<>();
	private static ThreadLocal<Browser> threadLocalBrowser = new ThreadLocal<>();
	private static ThreadLocal<BrowserContext> threadLocalContext = new ThreadLocal<>();
	protected static ThreadLocal<Page> webNode = new ThreadLocal<>();
	protected static ThreadLocal<JSONObject> threadTestData = new ThreadLocal<>();
	protected static Path path;
	protected static ThreadLocal<LoginPage> loginPage = new ThreadLocal<>();
	protected Properties config = loadProperties("config.properties");

	// @Parameters({"appURL", "browserType"})
	@BeforeMethod
	public void getPage222() {
		Playwright playwright = Playwright.create();
		Browser browser = null;
		BrowserContext context;
		Page page;
		Boolean headlessState = Boolean.parseBoolean(config.getProperty("headlessState"));
		switch (config.getProperty("browserType")) {
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
			path = Paths.get(config.getProperty("video-recording-files")
					+ LocalDateTime.now().toString().replace(":", "_").replace(".", "_"));
			context = browser
					.newContext(new Browser.NewContextOptions().setRecordVideoDir(path).setRecordVideoSize(1280, 720));
		} else
			context = browser.newContext();
		page = context.newPage();
		page.setDefaultTimeout(Integer.parseInt(config.getProperty("setDefaultTime")) * 1000);
		System.out.println(config.getProperty(config.getProperty("env") + ".baseUrl"));
		page.navigate(config.getProperty(config.getProperty("env") + ".baseUrl"));
		loginPage.set(new LoginPage(page, config));
		threadLocalPlaywright.set(playwright);
		threadLocalBrowser.set(browser);
		threadLocalContext.set(context);
		webNode.set(page);
		
	}

	public JSONObject getTestData(String path) {

		try {
			FileReader reader = new FileReader(path);
			threadTestData.set(new JSONObject(new JSONTokener(reader)));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load test data");
		}
		return threadTestData.get();
	}

	@AfterMethod
	public void tearDown() {
		Page page = webNode.get();
		BrowserContext context = threadLocalContext.get();
		Browser browser = threadLocalBrowser.get();
		Playwright playwright = threadLocalPlaywright.get();

		if (page != null)
			page.close();
		if (context != null)
			context.close();
		if (browser != null)
			browser.close();
		if (playwright != null)
			playwright.close();

		threadLocalPlaywright.remove();
		threadLocalBrowser.remove();
		threadLocalContext.remove();
		webNode.remove();
	}

	protected Playwright getPlaywright() {
		return threadLocalPlaywright.get();
	}

	protected Browser getBrowser() {
		return threadLocalBrowser.get();
	}

	protected BrowserContext getContext() {
		return threadLocalContext.get();
	}

	protected Page getPage() {
		return webNode.get();
	}

	private Properties loadProperties(String fileName) {
		Properties properties = new Properties();
		try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
			if (input == null) {
				return null;
			}
			properties.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return properties;
	}

	public static Page openNewTab(String url) {
		Page secondPage = threadLocalContext.get().newPage();
		secondPage.navigate(url);
		secondPage.bringToFront();
		return secondPage;
	}

	public static Page openNewBrowser(String url) {
		BrowserContext browserContext = threadLocalBrowser.get().newContext();
		Page page = browserContext.newPage();
		page.navigate(url);
		page.bringToFront();
		return page;
	}
}
