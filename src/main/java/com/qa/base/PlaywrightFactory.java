package com.qa.base;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Properties;

import com.qa.pages.LoginPage;
import org.testng.annotations.Listeners;
import com.microsoft.playwright.*;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.qameta.allure.testng.AllureTestNg;

@Listeners({AllureTestNg.class})
public class PlaywrightFactory {

    protected static final ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
    protected static final ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();
    protected static final ThreadLocal<LoginPage> loginPageThreadLocal = new ThreadLocal<>();
    protected static final ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    protected static final ThreadLocal<BrowserContext> browserContextThreadLocal = new ThreadLocal<>();
    protected static Path path;

    // @Parameters({"appURL", "browserType"})
    public static synchronized Page getPage(String appURL, String browserType, Properties config) {
        Playwright playwright = Playwright.create();
        Boolean headlessState = Boolean.parseBoolean(config.getProperty("headlessState"));
        Browser browser = null;
        switch (browserType) {
            case "chrome":
            case "firefox":
            case "safari":
                browser = playwright.chromium()
                        .launch(new BrowserType.LaunchOptions().setChannel(browserType).setHeadless(headlessState));
                break;
            case "chromium":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headlessState));
                break;
            default:
                throw new RuntimeException("Browser name is incorrect.");
        }
        BrowserContext browserContext;
        if (Boolean.parseBoolean(config.getProperty("video-recording"))) {
            path = Paths.get(
                    config.getProperty("video-recording-files") + LocalDateTime.now().toString().replace(":", "_").replace(".", "_"));
            browserContext = browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(path).setRecordVideoSize(1280, 720));
        } else {
            browserContext = browser.newContext();
        }
        Page page = browserContext.newPage();
        browserContextThreadLocal.set(browserContext);
        page.setDefaultTimeout(Integer.parseInt(config.getProperty("setDefaultTime")) * 1000);
        page.navigate(appURL);
        pageThreadLocal.set(page);
        playwrightThreadLocal.set(playwright);
        browserThreadLocal.set(browser);
        return page;
    }

    public static Page openNewTab(String url) {
        Page secondPage = browserContextThreadLocal.get().newPage();
        secondPage.navigate(url);
        secondPage.bringToFront();
        return secondPage;
    }

    public static Page openNewBrowser(String url) {
        BrowserContext browserContext = browserThreadLocal.get().newContext();
        Page page = browserContext.newPage();
        page.navigate(url);
        page.bringToFront();
        return page;
    }
}
