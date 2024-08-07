package com.qa.pages;

import java.io.ByteArrayInputStream;
import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.microsoft.playwright.Page;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class PageBase {
	public JSONObject testData;

	public JSONObject getTestData(String path) {

		try {
			FileReader reader = new FileReader(path);
			testData = new JSONObject(new JSONTokener(reader));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load test data");
		}
		return testData;
	}

	@Step("Log: {message}")
	public void logStep(String message) {
		Allure.addAttachment("Log", new ByteArrayInputStream(message.getBytes()));
	}

	public void pause(int waitInSeconds) {
		try {
			Thread.sleep(waitInSeconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void pause(Page page,int waitInSeconds) {		
			page.waitForTimeout(waitInSeconds*1000);		
	}
	
	
}
