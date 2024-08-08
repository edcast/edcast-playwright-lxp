package com.qa.base;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Properties;

import org.testng.annotations.Listeners;
import com.microsoft.playwright.*;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.qameta.allure.testng.AllureTestNg;

@Listeners({AllureTestNg.class})
public class PlaywrightFactory {

    protected static final ThreadLocal<PlaywrightSession> playwrightSessionThreadLocal = new ThreadLocal<>();
    protected static Path path;

    // @Parameters({"appURL", "browserType"})
    public static Page createPage(String appURL, String browserType, Properties config) {
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
        page.setDefaultTimeout(Integer.parseInt(config.getProperty("setDefaultTime")) * 1000);
        page.navigate(appURL);
        playwrightSessionThreadLocal.set(new PlaywrightSession(playwright, browser, browserContext, page));
        return page;
    }

    public static Page openNewTab(String url) {
        PlaywrightSession playwrightSession = playwrightSessionThreadLocal.get();
        Page secondPage = playwrightSession.getBrowserContext().newPage();
        secondPage.navigate(url);
        secondPage.bringToFront();
        playwrightSession.setPage(secondPage);
        playwrightSessionThreadLocal.set(playwrightSession);
        return secondPage;
    }

    public static Page openNewBrowser(String url) {
        PlaywrightSession playwrightSession = playwrightSessionThreadLocal.get();
        Page page = playwrightSession.getBrowserContext().newPage();
        page.navigate(url);
        page.bringToFront();
        playwrightSession.setPage(page);
        playwrightSessionThreadLocal.set(playwrightSession);
        return page;
    }

}
