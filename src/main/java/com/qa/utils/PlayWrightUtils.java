package com.qa.utils;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;

import io.qameta.allure.Allure;
public class PlayWrightUtils {
	public static void captureScreenshot(Page page, String screenshotPath) {
        try {
        	 byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));
            System.out.println("Screenshot captured: " + screenshotPath);
           // byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));

            Allure.addAttachment("Failure Screenshot", new ByteArrayInputStream(screenshot));
        } catch (PlaywrightException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
	
	

}
