package com.qa.testcases;

import com.microsoft.playwright.Page;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.qa.pages.BaseTest;
import io.qameta.allure.testng.AllureTestNg;
import io.qameta.allure.Description;

@Listeners({ AllureTestNg.class })
public class Trautomationv extends BaseTest {
	@Test()
	@Description("This test case checks that only one user session is maintained.")
	public void testConcurrentUserTest() throws InterruptedException {
		String loginUrl = "https://trautomationv11.cmnetwork.co/user/login";
		String expectedUrlAfterLogin = "https://trautomationv11.cmnetwork.co1/";
		loginPageThreadLocal.get().trautomationvLogin(pageThreadLocal.get());
		verifyPageUrl(expectedUrlAfterLogin, pageThreadLocal.get());
		Page pageOnNewBrowser = openNewBrowser(loginUrl);
		loginPageThreadLocal.get().trautomationvLogin(pageOnNewBrowser);
		verifyPageUrl(loginUrl, pageThreadLocal.get());

	}
	
	@Test
	public void getFileNames() throws IOException {
		abcd();


	}
	public void abcd() throws IOException {
		String val = null;
		Path path1= path;
		System.out.println(path1);
		try (Stream<Path> paths = Files.list(Paths.get("allure-report\\data"))) {
			paths.filter(Files::isRegularFile)
					.forEach(path -> System.out.println(path.getFileName().toString()));}
			//System.out.println(PlaywrightFactory.path.toString() + "\\" + path.getFileName().toString());
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
